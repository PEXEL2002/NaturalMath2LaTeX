package visitors;

public class AlgebraVisitor {
    private final MainVisitor main;
    public AlgebraVisitor(MainVisitor main) {
        this.main = main;
    }
}
