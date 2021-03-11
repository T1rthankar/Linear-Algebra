import java.util.*;
/**
 * Performs Gaussian Elimination on the matrix input by the user. Returns the
 * ref (row echelon form) of the matrix.
 *
 * @author Tirthankar Mazumder
 * @date June 5, 2020 -- June 6, 2020 (updates done on 8th August, 2020)
 * @version 2.0
 */
public class Gauss {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the number of equations: ");
        int a = s.nextInt();
        
        s.nextLine();
        
        System.out.print("Enter the number of variables: ");
        int b = s.nextInt();

        Matrix mat = new Matrix(a, b + 1);
        
        System.out.println();
        
        Parser.takeInput(mat);
        
        System.out.println("Original matrix:");
        System.out.println(mat.toString());
        
        mat = gaussEliminate(mat, true);
        
        System.out.println("Row echelon form:");
        System.out.println(mat.toString());
        
        System.out.println("Row echelon form, represented as an augmented matrix:");
        System.out.println(mat.augmentedMatrixToString());
    }

    /**
     * Performs Gaussian Elimination on the matrix.
     */
    public static Matrix gaussEliminate(Matrix m, boolean reduce) {
        m = recursiveGEliminate(0, 0, m);
        
        //If reduce is true, then we clear the rows of any common factors.
        //We made it an option for performance reasons
        if (reduce) {
            for (int r = 0; r < m.rows(); r++) {
                m.mult(Fraction.inverse(m.rowGCD(r, m.columns() - 2)), r);
                //Keep in mind that the arrays are zero-indexed, so the index of the second-
                //last (heh) column is m.columns() - 2
            }
        }
        return m;
    }

    private static Matrix recursiveGEliminate(int row, int column, Matrix m) {
        if (row == m.rows()) {
            return m;
        }

        //First, we have to bring the first row with a non-zero entry at m[row][column] up to the current
        //position by doing a row swap.
        int swap = -1; //initializing with -1 because -1 will signify that no swap is required.
        for (int i = row; i < m.rows(); i++) {
            if (!m.get(i, column).equals(Fraction.ZERO)) {
                swap = i;
                break;
            }
        }
        if (swap != -1) //then we need to do a row swap
            m.rowSwap(row, swap);

        Fraction coeff = m.get(row, column);
        for (int i = row + 1; i < m.rows(); i++) {
            if (!m.get(i, column).equals(Fraction.ZERO)) {
                Fraction factor = m.get(i, column).div(coeff).mul(Fraction.NEG_ONE);
                m.comb(i, row, factor);
            }
        }
        return recursiveGEliminate(row + 1, column + 1, m);
    }
}