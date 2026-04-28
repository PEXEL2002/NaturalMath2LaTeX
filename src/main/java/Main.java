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
    private static final String[] MATRIX_KEYWORDS = {
            "macierzRozszerzona", "augmentedMatrix", "augmented", "macierz", "matrix", "mat"
    };

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
        String normalizedInput = normalizeMatrixNotation(input);

        MathLexer lexer = new MathLexer(CharStreams.fromString(normalizedInput));
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

    private static String normalizeMatrixNotation(String input) {
        StringBuilder output = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            String keyword = matchedMatrixKeyword(input, i);
            if (keyword == null) {
                output.append(input.charAt(i));
                i++;
                continue;
            }

            int j = i + keyword.length();
            while (j < input.length() && Character.isWhitespace(input.charAt(j))) {
                j++;
            }

            if (j >= input.length() || input.charAt(j) != '(') {
                output.append(input.charAt(i));
                i++;
                continue;
            }

            int close = findClosingParen(input, j);
            if (close < 0) {
                output.append(input.substring(i));
                break;
            }

            output.append(input, i, j + 1);
            String inner = input.substring(j + 1, close);
            output.append(normalizeMatrixContent(inner));
            output.append(')');
            i = close + 1;
        }

        return output.toString();
    }

    private static String matchedMatrixKeyword(String input, int index) {
        for (String keyword : MATRIX_KEYWORDS) {
            if (!input.startsWith(keyword, index)) {
                continue;
            }

            boolean beforeOk = index == 0 || !Character.isLetterOrDigit(input.charAt(index - 1));
            int end = index + keyword.length();
            boolean afterOk = end >= input.length() || !Character.isLetterOrDigit(input.charAt(end));
            if (beforeOk && afterOk) {
                return keyword;
            }
        }
        return null;
    }

    private static int findClosingParen(String input, int openIndex) {
        int depth = 0;
        for (int i = openIndex; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String normalizeMatrixContent(String content) {
        StringBuilder out = new StringBuilder();
        int depth = 0;
        int i = 0;

        while (i < content.length()) {
            char c = content.charAt(i);

            if (Character.isWhitespace(c)) {
                int wsStart = i;
                while (i < content.length() && Character.isWhitespace(content.charAt(i))) {
                    i++;
                }

                char prev = previousNonWhitespace(content, wsStart - 1);
                char next = nextNonWhitespace(content, i);

                if (depth == 0 && isElementSeparator(prev, next)) {
                    out.append(", ");
                } else {
                    out.append(' ');
                }
                continue;
            }

            if (c == '(' || c == '{' || c == '[') {
                depth++;
            } else if ((c == ')' || c == '}' || c == ']') && depth > 0) {
                depth--;
            }

            out.append(c);
            i++;
        }

        return out.toString();
    }

    private static char previousNonWhitespace(String text, int start) {
        for (int i = start; i >= 0; i--) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return text.charAt(i);
            }
        }
        return '\0';
    }

    private static char nextNonWhitespace(String text, int start) {
        for (int i = start; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return text.charAt(i);
            }
        }
        return '\0';
    }

    private static boolean isElementSeparator(char prev, char next) {
        return isValueEnd(prev) && isValueStart(next);
    }

    private static boolean isValueEnd(char c) {
        return Character.isLetterOrDigit(c) || c == ')' || c == '}' || c == ']';
    }

    private static boolean isValueStart(char c) {
        return Character.isLetterOrDigit(c) || c == '(' || c == '{' || c == '[' || c == '-';
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
