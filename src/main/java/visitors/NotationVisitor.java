package visitors;

import grammar.MathParser;

import java.util.StringJoiner;

public class NotationVisitor {
    private final MainVisitor main;

    public NotationVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitSum(MathParser.SumContext ctx) {
        String variable = ctx.var.getText();
        String lowerBound = main.visit(ctx.lower);
        String upperBound = main.visit(ctx.upper);
        String body = main.visit(ctx.body);

        return "\\sum_{" + variable + "=" + lowerBound + "}^{" + upperBound + "} \\left(" + body + "\\right)";
    }

    public String visitProduct(MathParser.ProductContext ctx) {
        String variable = ctx.var.getText();
        String lowerBound = main.visit(ctx.lower);
        String upperBound = main.visit(ctx.upper);
        String body = main.visit(ctx.body);

        // Dodane \left( i \right)
        return "\\prod_{" + variable + "=" + lowerBound + "}^{" + upperBound + "} \\left(" + body + "\\right)";
    }

    public String visitEqualityPiecewise(MathParser.EqualityPiecewiseContext ctx) {
        String left = main.visit(ctx.left);
        return left + " = " + renderPiecewise(ctx.right);
    }

    private String renderPiecewise(MathParser.PiecewiseExprContext ctx) {
        StringJoiner rows = new StringJoiner(" \\\\ ");
        String conditionKeyword = resolveConditionKeyword(ctx);

        for (MathParser.PiecewiseCaseContext pieceCtx : ctx.piecewiseCase()) {
            String value = main.visit(pieceCtx.value);
            String condition = main.visit(pieceCtx.condition);
            rows.add(value + " & \\text{" + conditionKeyword + " } " + condition);
        }

        return "\\begin{cases} " + rows + " \\end{cases}";
    }

    private String resolveConditionKeyword(MathParser.PiecewiseExprContext ctx) {
        String firstSeparator = ctx.piecewiseCase(0).sep.getText();
        if ("dla".equals(firstSeparator)) {
            return "dla";
        }
        return "for";
    }
}