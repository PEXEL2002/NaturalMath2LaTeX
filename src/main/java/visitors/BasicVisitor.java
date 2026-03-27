package visitors;

import grammar.MathParser;

public class BasicVisitor {
    private final MainVisitor main;

    public BasicVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitConstant(MathParser.ConstantContext ctx) {
        // Normalize decimal separator so LaTeX always uses a dot.
        return ctx.NUMBER().getText().replace(',', '.');
    }

    public String visitVariable(MathParser.VariableContext ctx) {
        return ctx.ID().getText();
    }


    public String visitGrouping(MathParser.GroupingContext ctx) {
        String content = main.visit(ctx.expression());
        return ctx.getText().startsWith("{") ? content : "(" + content + ")";
    }
    public String visitInfinity(MathParser.InfinityContext ctx) {
        return "\\infty";
    }

    public String visitDegree(MathParser.DegreeContext ctx) {
        String value = main.visit(ctx.expression());
        return value + "^{\\circ}";
    }
    public String visitComparison(MathParser.ComparisonContext ctx) {
        String left = main.visit(ctx.left);
        String right = main.visit(ctx.right);
        String op = ctx.getChildCount() > 1 ? ctx.getChild(1).getText() : "";
        return switch (op) {
            case "<=" -> left + " \\le " + right;
            case ">=" -> left + " \\ge " + right;
            case "!=", "<>" -> left + " \\neq " + right;
            default -> left + " " + op + " " + right; // dla < i >
        };
    }
    public String visitEquality(MathParser.EqualityContext ctx) {
        String left = main.visit(ctx.left);
        if (ctx.right != null) {
            return left + " = " + main.visit(ctx.right);
        } else {
            return left + " = ";
        }
    }
}