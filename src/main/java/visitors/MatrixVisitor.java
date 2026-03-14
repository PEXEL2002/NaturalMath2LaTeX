package visitors;

public class MatrixVisitor {
    // W obu plikach dodaj to:
    private final MainVisitor main;
    public MatrixVisitor(MainVisitor main) { // lub AnalysisVisitor
        this.main = main;
    }
}
