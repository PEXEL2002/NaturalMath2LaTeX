package visitors;

import grammar.MathParser;

import java.util.ArrayList;
import java.util.List;

public class BasicVisitor {
    private final MainVisitor main;

    public BasicVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitConstant(MathParser.ConstantContext ctx) {
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
            default -> left + " " + op + " " + right;
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
    public String visitFunctionCall(MathParser.FunctionCallContext ctx) {
        String functionName = ctx.ID().getText();
        List<String> args = new ArrayList<>();
        for (MathParser.ExpressionContext exprCtx : ctx.argumentList().expression()) {
            args.add(main.visit(exprCtx));
        }
        String joinedArgs = String.join(", ", args);

        return functionName + "(" + joinedArgs + ")";
    }
}