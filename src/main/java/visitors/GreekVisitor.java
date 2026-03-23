package visitors;

import grammar.MathParser;
import java.util.HashMap;
import java.util.Map;

public class GreekVisitor {
    private final MainVisitor main;
    private final Map<String, String> greekMap;

    public GreekVisitor(MainVisitor main) {
        this.main = main;
        this.greekMap = new HashMap<>();
        greekMap.put("alfa", "\\alpha");
        greekMap.put("alpha", "\\alpha");
        greekMap.put("beta", "\\beta");
        greekMap.put("gamma", "\\gamma");
        greekMap.put("delta", "\\delta");
        greekMap.put("epsilon", "\\epsilon");
        greekMap.put("varepsilon", "\\varepsilon");
        greekMap.put("zeta", "\\zeta");
        greekMap.put("eta", "\\eta");
        greekMap.put("theta", "\\theta");
        greekMap.put("vartheta", "\\vartheta");
        greekMap.put("iota", "\\iota");
        greekMap.put("kappa", "\\kappa");
        greekMap.put("lambda", "\\lambda");
        greekMap.put("mu", "\\mu");
        greekMap.put("nu", "\\nu");
        greekMap.put("xi", "\\xi");
        greekMap.put("omicron", "o");
        greekMap.put("pi", "\\pi");
        greekMap.put("varpi", "\\varpi");
        greekMap.put("rho", "\\rho");
        greekMap.put("varrho", "\\varrho");
        greekMap.put("sigma", "\\sigma");
        greekMap.put("varsigma", "\\varsigma");
        greekMap.put("tau", "\\tau");
        greekMap.put("upsilon", "\\upsilon");
        greekMap.put("psi", "\\phi");
        greekMap.put("phi", "\\phi");
        greekMap.put("varphi", "\\varphi");
        greekMap.put("chi", "\\chi");
        greekMap.put("psi", "\\psi");
        greekMap.put("omega", "\\omega");

        greekMap.put("Gamma", "\\Gamma");
        greekMap.put("Delta", "\\Delta");
        greekMap.put("Theta", "\\Theta");
        greekMap.put("Lambda", "\\Lambda");
        greekMap.put("Xi", "\\Xi");
        greekMap.put("Pi", "\\Pi");
        greekMap.put("Sigma", "\\Sigma");
        greekMap.put("Upsilon", "\\Upsilon");
        greekMap.put("Phi", "\\Phi");
        greekMap.put("Psi", "\\Psi");
        greekMap.put("Omega", "\\Omega");
    }

    public String visitGreek(MathParser.GreekContext ctx) {
        String text = ctx.GREEK().getText();
        return greekMap.getOrDefault(text, text);
    }
}