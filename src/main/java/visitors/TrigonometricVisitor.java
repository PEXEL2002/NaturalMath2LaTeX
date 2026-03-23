package visitors;
import grammar.MathParser;

import java.util.HashMap;
import java.util.Map;

public class TrigonometricVisitor {
    private final MainVisitor main;
    private final Map<String, String> trigonometricMap;

    public TrigonometricVisitor(MainVisitor main) {
        this.main = main;
        this.trigonometricMap = new HashMap<>();
        trigonometricMap.put("sin", "\\sin");
        trigonometricMap.put("sinus", "\\sin");

        trigonometricMap.put("cos", "\\cos");
        trigonometricMap.put("cosinus", "\\cos");

        trigonometricMap.put("tan", "\\tan");
        trigonometricMap.put("tg", "\\tan");
        trigonometricMap.put("tangens", "\\tan");

        trigonometricMap.put("cot", "\\cot");
        trigonometricMap.put("ctg", "\\cot");
        trigonometricMap.put("cotangens", "\\cot");
        trigonometricMap.put("kotangens", "\\cot");

        trigonometricMap.put("sec", "\\sec");
        trigonometricMap.put("secans", "\\sec");
        trigonometricMap.put("sekans", "\\sec");

        trigonometricMap.put("csc", "\\csc");
        trigonometricMap.put("cosecans", "\\csc");
        trigonometricMap.put("kosekans", "\\csc");

        trigonometricMap.put("arcsin", "\\arcsin");
        trigonometricMap.put("arcussinus", "\\arcsin");

        trigonometricMap.put("arccos", "\\arccos");
        trigonometricMap.put("arcuscosinus", "\\arccos");

        trigonometricMap.put("arctan", "\\arctan");
        trigonometricMap.put("arctg", "\\arctan");
        trigonometricMap.put("arcustangens", "\\arctan");

        // LaTeX nie ma wbudowanego \arccot, używamy operatorname
        trigonometricMap.put("arccot", "\\operatorname{arccot}");
        trigonometricMap.put("arcctg", "\\operatorname{arccot}");
        trigonometricMap.put("arcuscotangens", "\\operatorname{arccot}");
        trigonometricMap.put("arcuskotangens", "\\operatorname{arccot}");

        trigonometricMap.put("arcsec", "\\operatorname{arcsec}");
        trigonometricMap.put("arcussecans", "\\operatorname{arcsec}");
        trigonometricMap.put("arcussekans", "\\operatorname{arcsec}");

        trigonometricMap.put("arccsc", "\\operatorname{arccsc}");
        trigonometricMap.put("arcuscosecans", "\\operatorname{arccsc}");
        trigonometricMap.put("arcuskosekans", "\\operatorname{arccsc}");

        // --- HIPERBOLICZNE ---
        trigonometricMap.put("sinh", "\\sinh");
        trigonometricMap.put("sinushiperboliczny", "\\sinh");

        trigonometricMap.put("cosh", "\\cosh");
        trigonometricMap.put("cosinushiperboliczny", "\\cosh");

        trigonometricMap.put("tanh", "\\tanh");
        trigonometricMap.put("tgh", "\\tanh");
        trigonometricMap.put("tangenshiperboliczny", "\\tanh");

        trigonometricMap.put("coth", "\\coth");
        trigonometricMap.put("ctgh", "\\coth");
        trigonometricMap.put("cotangenshiperboliczny", "\\coth");
        trigonometricMap.put("kotangenshiperboliczny", "\\coth");

        trigonometricMap.put("sech", "\\operatorname{sech}");
        trigonometricMap.put("secanshiperboliczny", "\\operatorname{sech}");

        trigonometricMap.put("csch", "\\operatorname{csch}");
        trigonometricMap.put("cosecanshiperboliczny", "\\operatorname{csch}");

        trigonometricMap.put("arcsinh", "\\operatorname{arcsinh}");
        trigonometricMap.put("arsinh", "\\operatorname{arcsinh}");
        trigonometricMap.put("areasinushiperboliczny", "\\operatorname{arcsinh}");

        trigonometricMap.put("arccosh", "\\operatorname{arccosh}");
        trigonometricMap.put("arcosh", "\\operatorname{arccosh}");
        trigonometricMap.put("areacosinushiperboliczny", "\\operatorname{arccosh}");

        trigonometricMap.put("arctanh", "\\operatorname{arctanh}");
        trigonometricMap.put("artanh", "\\operatorname{arctanh}");
        trigonometricMap.put("areatangenshiperboliczny", "\\operatorname{arctanh}");

        trigonometricMap.put("arccoth", "\\operatorname{arccoth}");
        trigonometricMap.put("arcoth", "\\operatorname{arccoth}");
        trigonometricMap.put("areacotangenshiperboliczny", "\\operatorname{arccoth}");

        trigonometricMap.put("arcsech", "\\operatorname{arcsech}");
        trigonometricMap.put("arsech", "\\operatorname{arcsech}");
        trigonometricMap.put("areasecanshiperboliczny", "\\operatorname{arcsech}");

        trigonometricMap.put("arccsch", "\\operatorname{arccsch}");
        trigonometricMap.put("arcsch", "\\operatorname{arccsch}");
        trigonometricMap.put("areacosecanshiperboliczny", "\\operatorname{arccsch}");

    }

    public String visitTrigonometricParen(MathParser.TrigonometricParenContext ctx) {
        String trigonometricName = ctx.TRIGONOMETRIC().getText();

        String latexTrigonometric = trigonometricMap.getOrDefault(trigonometricName, "\\" + trigonometricName);

        String argument = main.visit(ctx.expression());

        return latexTrigonometric + "(" + argument + ")";
    }

    public String visitTrigonometricNoParen(MathParser.TrigonometricNoParenContext ctx) {
        String trigonometricName = ctx.TRIGONOMETRIC().getText();
        String latexTrigonometric = trigonometricMap.getOrDefault(trigonometricName, "\\" + trigonometricName);

        String argument = main.visit(ctx.expression());


        return latexTrigonometric + "(" + argument + ")";
    }



}
