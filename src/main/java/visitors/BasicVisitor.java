package visitors;

import main.antlr4.grammar.MathParser;

public class BasicVisitor {
    private final MainVisitor main;

    public BasicVisitor(MainVisitor main) {
        this.main = main;
    }

    public String visitConstant(MathParser.ConstantContext ctx) {
        return ctx.INT().getText();
    }

    public String visitVariable(MathParser.VariableContext ctx) {
        return ctx.ID().getText();
    }

    public String visitGrouping(MathParser.GroupingContext ctx) {
        String content = main.visit(ctx.expression());
        return ctx.getText().startsWith("{") ? content : "(" + content + ")";
    }
}