package visitors;

import grammar.MathParser;

public class AlgebraVisitor {
    private final MainVisitor main;

    public AlgebraVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitAddSub(MathParser.AddSubContext ctx) {
        String left = main.visit(ctx.left);
        String right = main.visit(ctx.right);
        String op = ctx.getChild(1).getText();
        return left + " " + op + " " + right;
    }

    public String visitMultDiv(MathParser.MultDivContext ctx) {
        String left = main.visit(ctx.left);
        String right = main.visit(ctx.right);
        String op = ctx.getChild(1).getText();

        if (op.equals("//")) {
            return "\\frac{" + left + "}{" + right + "}";
        }
        String latexOp = op.equals("*") ? "\\cdot" : op;
        return left + " " + latexOp + " " + right;
    }

    public String visitImplicitMul(MathParser.ImplicitMulContext ctx) {
        return main.visit(ctx.left) + " " + main.visit(ctx.right);
    }

    public String visitPower(MathParser.PowerContext ctx) {
        return main.visit(ctx.left) + "^{" + main.visit(ctx.right) + "}";
    }
    public String visitUnderscore(MathParser.UnderscoreContext ctx){
        return main.visit(ctx.left)+ "_{" + main.visit(ctx.right) + "}";
    }
    public String visitUnarySign(MathParser.UnarySignContext ctx) {
        String sign = ctx.getChild(0).getText();
        String expr = main.visit(ctx.expression());
        return sign + expr;
    }

}