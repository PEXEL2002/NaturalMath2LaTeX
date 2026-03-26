package visitors;

import grammar.MathParser;
import grammar.MathParserBaseVisitor;

public class MainVisitor extends MathParserBaseVisitor<String> {

    // Rejestracja specjalistów
    private final BasicVisitor basic = new BasicVisitor(this);
    private final AlgebraVisitor algebra = new AlgebraVisitor(this);
    private final AnalysisVisitor analysis = new AnalysisVisitor(this);
    private final MatrixVisitor matrix = new MatrixVisitor(this);

    private final GreekVisitor greek = new GreekVisitor(this);

    private final TrigonometricVisitor trigonometric = new TrigonometricVisitor(this);

    private final LogicVisitor logic = new LogicVisitor(this);

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
    @Override public String visitUnderscore(MathParser.UnderscoreContext ctx) { return algebra.visitUnderscore(ctx); }
    @Override public String visitUnarySign(MathParser.UnarySignContext ctx) {return algebra.visitUnarySign(ctx);}

    // Tu w przyszłości dodasz metody dla Analysis (całki) i Matrix (macierze)
    @Override public String visitIntegral(MathParser.IntegralContext ctx) {return analysis.visitIntegral(ctx);}


    @Override public String visitGreek(MathParser.GreekContext ctx) { return greek.visitGreek(ctx); }
    @Override public String visitInfinity(MathParser.InfinityContext ctx) { return basic.visitInfinity(ctx);}
    @Override
    public String visitTrigonometricParen(MathParser.TrigonometricParenContext ctx) {
        return trigonometric.visitTrigonometricParen(ctx);
    }

    @Override
    public String visitTrigonometricNoParen(MathParser.TrigonometricNoParenContext ctx) {
        return trigonometric.visitTrigonometricNoParen(ctx);
    }


    @Override
    public String visitDegree(MathParser.DegreeContext ctx) { return basic.visitDegree(ctx);}

    @Override public String visitLogicNot(MathParser.LogicNotContext ctx) { return logic.visitLogicNot(ctx); }
    @Override public String visitLogicAndOr(MathParser.LogicAndOrContext ctx) { return logic.visitLogicAndOr(ctx); }
    @Override public String visitLogicImplIff(MathParser.LogicImplIffContext ctx) { return logic.visitLogicImplIff(ctx); }
}