package visitors;

import grammar.MathParser;
import grammar.MathParserBaseVisitor;

public class MainVisitor extends MathParserBaseVisitor<String> {

    @Override
    public String visitProgram(MathParser.ProgramContext ctx) {
        // Główny punkt wejścia - odwiedzamy pierwsze wyrażenie w programie
        return visit(ctx.expression());
    }

    @Override
    public String visitAddSub(MathParser.AddSubContext ctx) {
        String left = visit(ctx.left);
        String right = visit(ctx.right);
        String op = ctx.getChild(1).getText();
        return left + " " + op + " " + right;
    }

    @Override
    public String visitMultDiv(MathParser.MultDivContext ctx) {
        String left = visit(ctx.left);
        String right = visit(ctx.right);
        String op = ctx.getChild(1).getText();

        if (op.equals("//")) {
            // Ułamki zawsze dostają klamry w LaTeX (\frac{licznik}{mianownik}),
            // niezależnie od tego, czy użytkownik użył {} w kodzie źródłowym.
            return "\\frac{" + left + "}{" + right + "}";
        }

        // Zamiana gwiazdki na profesjonalną kropkę mnożenia
        String latexOp = op.equals("*") ? "\\cdot" : op;
        return left + " " + latexOp + " " + right;
    }

    @Override
    public String visitImplicitMul(MathParser.ImplicitMulContext ctx) {
        // Obsługa mnożenia domyślnego (np. 2x, 2 x, a{b+c})
        // Po prostu sklejamy wyniki spacją.
        return visit(ctx.left) + " " + visit(ctx.right);
    }

    @Override
    public String visitPower(MathParser.PowerContext ctx) {
        // Potęgi zawsze wymagają klamer w wykładniku dla bezpieczeństwa w LaTeX
        return visit(ctx.left) + "^{" + visit(ctx.right) + "}";
    }

    @Override
    public String visitGrouping(MathParser.GroupingContext ctx) {
        String content = visit(ctx.expression());
        String fullText = ctx.getText();

        if (fullText.startsWith("{")) {
            // KLAMRY: Traktujemy je jako cichy separator/grupator.
            // Zwracamy czysty środek. Dzięki temu {a+b}//c zadziała poprawnie
            // i nie wyprodukuje podwójnych klamer w ułamku.
            return content;
        } else {
            // NAWIASY: Przepisujemy je dosłownie do LaTeX-a.
            return "(" + content + ")";
        }
    }

    @Override
    public String visitConstant(MathParser.ConstantContext ctx) {
        // Pobieramy wartość liczbową (INT)
        return ctx.INT().getText();
    }

    @Override
    public String visitVariable(MathParser.VariableContext ctx) {
        // Pobieramy nazwę zmiennej (ID)
        return ctx.ID().getText();
    }
}