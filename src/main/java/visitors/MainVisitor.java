package visitors;

import grammar.MathParser;
import grammar.MathParserBaseVisitor;

public class MainVisitor extends MathParserBaseVisitor<String> {

    // Rejestracja specjalistów
    private final BasicVisitor basic = new BasicVisitor(this);
    private final AlgebraVisitor algebra = new AlgebraVisitor(this);
    private final AnalysisVisitor analysis = new AnalysisVisitor(this);
    private final MatrixVisitor matrix = new MatrixVisitor(this);

    @Override
    public String visitProgram(MathParser.ProgramContext ctx) {
        return visit(ctx.expression());
    }

    // Delegacja do BasicVisitor (podstawy: liczby, zmienne, grupy)
    @Override public String visitConstant(MathParser.ConstantContext ctx) { return basic.visitConstant(ctx); }
    @Override public String visitVariable(MathParser.VariableContext ctx) { return basic.visitVariable(ctx); }
    @Override public String visitGrouping(MathParser.GroupingContext ctx) { return basic.visitGrouping(ctx); }

    // Delegacja do AlgebraVisitor (działania, potęgi)
    @Override public String visitAddSub(MathParser.AddSubContext ctx) { return algebra.visitAddSub(ctx); }
    @Override public String visitMultDiv(MathParser.MultDivContext ctx) { return algebra.visitMultDiv(ctx); }
    @Override public String visitImplicitMul(MathParser.ImplicitMulContext ctx) { return algebra.visitImplicitMul(ctx); }
    @Override public String visitPower(MathParser.PowerContext ctx) { return algebra.visitPower(ctx); }

    // Tu w przyszłości dodasz metody dla Analysis (całki) i Matrix (macierze)
}