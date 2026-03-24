package visitors;
import grammar.MathParser;
import java.util.HashMap;
import java.util.Map;
public class LogicVisitor {
    private final MainVisitor main;

    public LogicVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitLogicNot(MathParser.LogicNotContext ctx) {
        String expr = main.visit(ctx.expression());
        return "\\neg " + expr;
    }

    public String visitLogicAndOr(MathParser.LogicAndOrContext ctx) {
        String left = main.visit(ctx.left);
        String right = main.visit(ctx.right);
        String operator = ctx.getChild(1).getText();

        String latexOp;
        if (operator.equals("i") || operator.equals("and") || operator.equals("oraz") || operator.equals("&&")) {
            latexOp = "\\land";
        } else {
            latexOp = "\\lor";
        }

        return left + " " + latexOp + " " + right;
    }

    public String visitLogicImplIff(MathParser.LogicImplIffContext ctx) {
        String left = main.visit(ctx.left);
        String right = main.visit(ctx.right);
        String operator = ctx.getChild(1).getText();

        String latexOp;
        if (operator.equals("<=>") || operator.equals("wtw") || operator.equals("wtedyitylkowtedy") || operator.equals("rownowaznie")) {
            latexOp = "\\iff";
        } else {
            latexOp = "\\implies";
        }

        return left + " " + latexOp + " " + right;
    }
}
