    package visitors;

    public class AnalysisVisitor {
        private final MainVisitor main;

        public AnalysisVisitor(MainVisitor main) { // lub AnalysisVisitor
            this.main = main;
        }
    }