    package visitors;
    import grammar.MathParser;
    public class AnalysisVisitor {
        private final MainVisitor main;

        public AnalysisVisitor(MainVisitor main) {
            this.main = main;
        }
        public String visitIntegral(MathParser.IntegralContext ctx) {
            StringBuilder sb = new StringBuilder("\\int");
            if (ctx.lower != null && ctx.upper != null) {
                sb.append("_{").append(main.visit(ctx.lower)).append("}");
                sb.append("^{").append(main.visit(ctx.upper)).append("}");
            }
            sb.append(" ").append(main.visit(ctx.body));
            String v = (ctx.var != null) ? ctx.var.getText() : "x";
            sb.append(" \\, \\mathrm{d}").append(v);
            return sb.toString();
        }

        public String visitLimit(MathParser.LimitContext ctx) {
            String variable = ctx.var.getText();
            String target = renderLimitTarget(ctx.target);
            String body = main.visit(ctx.body);
            if (ctx.L_BRACKET() != null) {
                return "\\lim_{" + variable + " \\to " + target + "} \\left(" + body + "\\right)";
            } else {
                return "\\lim_{" + variable + " \\to " + target + "} " + body;
            }
        }

        private String renderLimitTarget(MathParser.LimitTargetContext ctx) {
            if (ctx.NUMBER() != null) {
                String n = ctx.NUMBER().getText().replace(',', '.');
                return (ctx.MINUS() != null ? "-" : "") + n;
            }
            if (ctx.INFINITY() != null) {
                return (ctx.MINUS() != null ? "-" : "") + "\\infty";
            }
            if (ctx.ID() != null) {
                return ctx.ID().getText();
            }
            if (ctx.GREEK() != null) {
                return ctx.GREEK().getText();
            }
            if (ctx.expression() != null) {
                return main.visit(ctx.expression());
            }
            return ctx.getText();
        }

    }