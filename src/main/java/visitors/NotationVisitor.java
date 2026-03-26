package visitors;

import grammar.MathParser;

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
}