import grammar.MathLexer;
import grammar.MathParser;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import visitors.MainVisitor;

import java.util.Scanner;

public class Main {
    private static final int DEFAULT_API_PORT = 7070;

    public static void main(String[] args) {
        AppConfig config = AppConfig.fromEnv();

        if (config.mode() == AppMode.API) {
            runApiMode(config.port());
            return;
        }

        runConsoleMode();
    }

    private static void runConsoleMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== NaturalMath2LaTeX Tester (CONSOLE) ===");
        System.out.println("Wpisz formule (np. a // b + c) lub 'exit' by wyjsc:");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            if (input.trim().isEmpty()) {
                continue;
            }

            try {
                String result = convertToLatex(input);
                System.out.println("Wynik LaTeX: " + result);
            } catch (IllegalArgumentException ex) {
                System.err.println("Blad parsowania: " + ex.getMessage());
            }
        }
    }

    private static void runApiMode(int port) {
        Javalin app = Javalin.create(config -> config.showJavalinBanner = false)
                .start(port);

        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type");
        });

        app.options("/api/convert", ctx -> ctx.status(204));

        app.post("/api/convert", ctx -> {
            ConvertRequest request;
            try {
                request = ctx.bodyAsClass(ConvertRequest.class);
            } catch (Exception ex) {
                ctx.status(400).json(new ErrorResponse("Niepoprawny JSON. Oczekiwano pola: expression"));
                return;
            }

            if (request == null || request.expression() == null || request.expression().isBlank()) {
                ctx.status(400).json(new ErrorResponse("Pole 'expression' jest wymagane."));
                return;
            }

            try {
                String latex = convertToLatex(request.expression());
                ctx.status(200).json(new ConvertResponse(request.expression(), latex));
            } catch (IllegalArgumentException ex) {
                ctx.status(400).json(new ErrorResponse(ex.getMessage()));
            } catch (Exception ex) {
                ctx.status(500).json(new ErrorResponse("Wewnetrzny blad serwera."));
            }
        });

        System.out.println("=== NaturalMath2LaTeX API ===");
        System.out.println("POST http://localhost:" + port + "/api/convert");
    }

    private static String convertToLatex(String input) {
        MathLexer lexer = new MathLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathParser parser = new MathParser(tokens);

        SyntaxErrorCollector errors = new SyntaxErrorCollector();
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        lexer.addErrorListener(errors);
        parser.addErrorListener(errors);

        ParseTree tree = parser.program();
        if (errors.hasError() || parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException(errors.firstError());
        }

        MainVisitor visitor = new MainVisitor();
        return visitor.visit(tree);
    }

    private enum AppMode {
        CONSOLE,
        API;

        private static AppMode parse(String modeValue) {
            if (modeValue == null || modeValue.isBlank()) {
                return CONSOLE;
            }

            try {
                return AppMode.valueOf(modeValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Nieznany MODE='" + modeValue + "'. Dozwolone: CONSOLE albo API.");
            }
        }
    }

    private record AppConfig(AppMode mode, int port) {
        private static AppConfig fromEnv() {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

            AppMode mode = AppMode.parse(dotenv.get("MODE", "CONSOLE"));

            String portRaw = dotenv.get("PORT", String.valueOf(DEFAULT_API_PORT));
            int port;
            try {
                port = Integer.parseInt(portRaw);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Niepoprawny PORT='" + portRaw + "'. Podaj liczbe calkowita.");
            }

            return new AppConfig(mode, port);
        }
    }

    private record ConvertRequest(String expression) {
    }

    private record ConvertResponse(String expression, String latex) {
    }

    private record ErrorResponse(String error) {
    }

    private static final class SyntaxErrorCollector extends BaseErrorListener {
        private String firstError = "Niepoprawna skladnia wejscia.";

        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer,
                Object offendingSymbol,
                int line,
                int charPositionInLine,
                String msg,
                RecognitionException e
        ) {
            if (firstError == null || firstError.equals("Niepoprawna skladnia wejscia.")) {
                firstError = "Blad skladni (linia " + line + ", znak " + charPositionInLine + "): " + msg;
            }
        }

        private boolean hasError() {
            return firstError != null && !firstError.equals("Niepoprawna skladnia wejscia.");
        }

        private String firstError() {
            return firstError;
        }
    }
}
