package visitors;

import grammar.MathParser;

import java.util.ArrayList;
import java.util.List;

public class MatrixVisitor {
    private final MainVisitor main;

    public MatrixVisitor(MainVisitor main) {
        this.main = main;
    }

    /**
     * Obsługuje zwykłe macierze
     * Przykład: matrix(1,2,3; 4,5,6; 7,8,9) -> \begin{pmatrix} 1 & 2 & 3 \\ 4 & 5 & 6 \\ 7 & 8 & 9 \end{pmatrix}
     */
    public String visitMatrix(MathParser.MatrixContext ctx) {
        StringBuilder latex = new StringBuilder("\\begin{pmatrix}\n");

        List<MathParser.MatrixRowContext> rows = ctx.matrixRows().matrixRow();
        for (int i = 0; i < rows.size(); i++) {
            MathParser.MatrixRowContext row = rows.get(i);
            List<MathParser.MatrixElementContext> elements = row.matrixElement();

            for (int j = 0; j < elements.size(); j++) {
                latex.append(main.visit(elements.get(j).expression()));
                if (j < elements.size() - 1) {
                    latex.append(" & ");
                }
            }

            if (i < rows.size() - 1) {
                latex.append(" \\\\\n");
            } else {
                latex.append("\n");
            }
        }

        latex.append("\\end{pmatrix}");
        return latex.toString();
    }

    /**
     * Obsługuje macierze rozszerzone używając dwóch osobnych macierzy
     * Przykład: augmented(1,2; 3,4; 5,6) -> \left(\begin{matrix} 1 & 2 \\ 3 & 4 \end{matrix}\right|\left.\begin{matrix} 5 \\ 6 \end{matrix}\right)
     */
    public String visitAugmentedMatrix(MathParser.AugmentedMatrixContext ctx) {
        List<MathParser.MatrixRowContext> leftRows = ctx.leftPart.matrixRow();
        List<MathParser.MatrixRowContext> rightRows = ctx.rightPart.matrixRow();

        if (leftRows.size() != rightRows.size()) {
            throw new RuntimeException("Augmented matrix: left and right parts must have the same number of rows");
        }

        StringBuilder latex = new StringBuilder("\\left(\\begin{matrix}\n");

        // Lewa część macierzy
        for (int i = 0; i < leftRows.size(); i++) {
            List<MathParser.MatrixElementContext> leftElements = leftRows.get(i).matrixElement();

            for (int j = 0; j < leftElements.size(); j++) {
                latex.append(main.visit(leftElements.get(j).expression()));
                if (j < leftElements.size() - 1) {
                    latex.append(" & ");
                }
            }

            if (i < leftRows.size() - 1) {
                latex.append(" \\\\\n");
            } else {
                latex.append("\n");
            }
        }

        latex.append("\\end{matrix}\\right|\\left.\\begin{matrix}\n");

        // Prawa część macierzy
        for (int i = 0; i < rightRows.size(); i++) {
            List<MathParser.MatrixElementContext> rightElements = rightRows.get(i).matrixElement();

            for (int j = 0; j < rightElements.size(); j++) {
                latex.append(main.visit(rightElements.get(j).expression()));
                if (j < rightElements.size() - 1) {
                    latex.append(" & ");
                }
            }

            if (i < rightRows.size() - 1) {
                latex.append(" \\\\\n");
            } else {
                latex.append("\n");
            }
        }

        latex.append("\\end{matrix}\\right)");
        return latex.toString();
    }

    /**
     * Obsługuje kropki (cdots, vdots, ddots)
     */
    public String visitDots(MathParser.DotsContext ctx) {
        String dotsType = ctx.DOTS().getText();

        switch (dotsType) {
            case "cdots":
            case "kropkiPoziome":
                return "\\cdots";
            case "vdots":
            case "kropkiPionowe":
                return "\\vdots";
            case "ddots":
            case "kropkiUkosne":
                return "\\ddots";
            default:
                return "\\cdots"; // domyślnie
        }
    }
}
