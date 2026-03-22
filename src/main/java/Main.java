import antlr4.grammar.MathLexer;
import antlr4.grammar.MathParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import visitors.MainVisitor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainVisitor visitor = new MainVisitor();

        System.out.println("=== NaturalMath2LaTeX Tester ===");
        System.out.println("Wpisz formule (np. a // b + c) lub 'exit' by wyjsc:");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;
            if (input.trim().isEmpty()) continue;

            try {
                // 1. Tworzymy strumień znaków z wejścia
                MathLexer lexer = new MathLexer(CharStreams.fromString(input));

                // 2. Zamieniamy znaki na tokeny
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                // 3. Budujemy parser
                MathParser parser = new MathParser(tokens);

                // 4. Tworzymy drzewo składniowe (zaczynamy od reguły 'program')
                ParseTree tree = parser.program();

                // 5. Odpalamy naszego Visitora
                String result = visitor.visit(tree);

                System.out.println("Wynik LaTeX: " + result);

            } catch (Exception e) {
                System.err.println("Blad parsowania: " + e.getMessage());
            }
        }
    }
}
