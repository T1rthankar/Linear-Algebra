import java.math.BigInteger; //minimizing dependencies to precisely what we need
/**
 * Defines a matrix data structure which uses Fractions (arbitrary-precision rational
 * numbers) as its native data type.
 * 
 * The class defines the row-reduction operations on itself that can be used to reduce
 * the matrix to an equivalent form.
 *
 * @author Tirthankar Mazumder
 * @version 2.71
 * @date June 4, 2020, major revisions done from 8th to 10th August, 2020
 */
public class Matrix {
    private Fraction[][] matrix;
    private final int row, column;

    public Matrix(int r, int c) {
        row = r;
        column = c;
        matrix = new Fraction[r][c];
    }
    
    private static String getFormatString(int numeratorLength, int denominatorLength) {
        return "%" + numeratorLength + "s/%-" + denominatorLength + "s";
    }
    
    private static String getFormatString(int spaces) {
        int leftSpaces;
        if (spaces % 2 == 0)
            leftSpaces = spaces / 2;
        else
            leftSpaces = (spaces + 1) / 2;
        //We also want the greater number of spaces (if spaces is odd) to be on the left.
        return " ".repeat(leftSpaces) + "%s" + " ".repeat(spaces - leftSpaces);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        String spaces = " ";
        
        //There are two parts to this, to ensure correct alignment. First, we have to extract all the
        //necessary metadata from the matrix, and then use that to make the output String.
        
        //Stores the length of the numerator and the denominator of the Fraction that has the maximum length in a
        //column
        int[][] max_len = new int[column][2]; //Terrible design decision, but I can't think of a better way to do it.
        //(Slightly better now. I had another array, which I managed to get rid of, thereby saving a little memory.)
        //(And then I had to bring it back because the case where a Fraction is an integer completely escaped my 
        //mind.)
        
        int[] maximum_length = new int[column]; //maximum_length stores the length of the longest Fraction in a column
        
        for (int c = 0; c < column; c++) {
            for (int r = 0; r < row; r++) {
                int len = matrix[r][c].toString().length(); //prevents us from having to calculate the same thing twice,
                //at the expense of a little memory
                
                //There are two cases: One for when the denominator is 1, and one for when it's not
                // System.out.println(matrix[r][c].getDenominator());
                // System.out.println(matrix[r][c].getDenominator().equals(Fraction.ONE));
                if (matrix[r][c].getDenominator().equals(BigInteger.ONE)) {
                    if (maximum_length[c] < len) {
                        max_len[c][0] = matrix[r][c].numeratorLength();
                        max_len[c][1] = 0;
                        //This actually makes sense because if the denominator is one, then we simply don't print it
                        spaces = " ".repeat(max_len[c][0] / 3 + 1);
                        
                        maximum_length[c] = len;
                        //The length of the Fraction is simply equal to its, well, actual length.
                    }
                } else {
                    if (maximum_length[c] < len) {
                        max_len[c][0] = matrix[r][c].numeratorLength();
                        max_len[c][1] = matrix[r][c].denominatorLength();
                        spaces = " ".repeat(Math.max(max_len[c][0], max_len[c][1]) / 3 + 1);
                        
                        maximum_length[c] = len;
                        //The length of the Fraction is simply equal to its, well, actual length.
                    }
                }
            }
        }
        
        for (int i = 0; i < row; i++) {
            result.append("[");
            for(int j = 0; j < column; j++) {
                if (matrix[i][j].getDenominator().equals(BigInteger.ONE)) {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(maximum_length[j] -
                            matrix[i][j].toString().length()), matrix[i][j].toString()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                } else {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(max_len[j][0], max_len[j][1]),
                            matrix[i][j].getNumerator(), matrix[i][j].getDenominator()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                }
                
                if (j != column - 1)
                    result.append(spaces);
            }
            result.append("]\n");
        }
        
        return result.toString();
    }
    
    public String augmentedMatrixToString() {
        //This method is the exact same as the toString() method. The only difference is that we added a
        //special case for the last entry.
        
        StringBuilder result = new StringBuilder();
        
        String spaces = " ";
        
        //There are two parts to this, to ensure correct alignment. First, we have to extract all the
        //necessary metadata from the matrix, and then use that to make the output String.
        
        //Stores the length of the numerator and the denominator of the Fraction that has the maximum length in a
        //column
        int[][] max_len = new int[column][2]; //Terrible design decision, but I can't think of a better way to do it.
        //(Slightly better now. I had another array, which I managed to get rid of, thereby saving a little memory.)
        //(And then I had to bring it back because the case where a Fraction is an integer completely escaped my 
        //mind.)
        
        int[] maximum_length = new int[column]; //maximum_length stores the length of the longest Fraction in a column
        
        for (int c = 0; c < column; c++) {
            for (int r = 0; r < row; r++) {
                int len = matrix[r][c].toString().length(); //prevents us from having to calculate the same thing twice,
                //at the expense of a little memory
                
                //There are two cases: One for when the denominator is 1, and one for when it's not
                // System.out.println(matrix[r][c].getDenominator());
                // System.out.println(matrix[r][c].getDenominator().equals(Fraction.ONE));
                if (matrix[r][c].getDenominator().equals(BigInteger.ONE)) {
                    if (maximum_length[c] < len) {
                        max_len[c][0] = matrix[r][c].numeratorLength();
                        max_len[c][1] = 0;
                        //This actually makes sense because if the denominator is one, then we simply don't print it
                        spaces = " ".repeat(max_len[c][0] / 3 + 1);
                        
                        maximum_length[c] = len;
                        //The length of the Fraction is simply equal to its, well, actual length.
                    }
                } else {
                    if (maximum_length[c] < len) {
                        max_len[c][0] = matrix[r][c].numeratorLength();
                        max_len[c][1] = matrix[r][c].denominatorLength();
                        spaces = " ".repeat(Math.max(max_len[c][0], max_len[c][1]) / 3 + 1);
                        
                        maximum_length[c] = len;
                        //The length of the Fraction is simply equal to its, well, actual length.
                    }
                }
            }
        }
        
        for (int i = 0; i < row; i++) {
            result.append("[");
            int j = 0;
            for(; j < column - 1; j++) {
                if (matrix[i][j].getDenominator().equals(BigInteger.ONE)) {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(maximum_length[j] -
                            matrix[i][j].toString().length()), matrix[i][j].toString()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                } else {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(max_len[j][0], max_len[j][1]),
                            matrix[i][j].getNumerator(), matrix[i][j].getDenominator()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                }
                
                if (j != column - 1)
                    result.append(spaces);
            }
            result.append("|");
            result.append(spaces);
            
            //Just lifted the code from above and substituted j = column - 1 in.
            if (matrix[i][j].getDenominator().equals(BigInteger.ONE)) {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(maximum_length[j] -
                            matrix[i][j].toString().length()), matrix[i][j].toString()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                } else {
                    if (matrix[i][j].toString().length() != maximum_length[j]) {
                        result.append(String.format(getFormatString(max_len[j][0], max_len[j][1]),
                            matrix[i][j].getNumerator(), matrix[i][j].getDenominator()));
                    } else {
                        result.append(matrix[i][j].toString());
                    }
                }
            result.append("]\n");
        }
        
        return result.toString();
    }

    /**
     * Checks to see if any row is completely filled with zeroes.
     */
    public boolean zeroRow(int row) {
        boolean ret = true;
        for (int i = 0; i < column; i++) {
            if (!matrix[row][i].equals(Fraction.ZERO)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     * Swaps row i with row j.
     */
    public void rowSwap(int i, int j) {
        Fraction[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    public void mult(Fraction factor, int row) {
        for (int i = 0; i < column; i++) {
            matrix[row][i] = matrix[row][i].mul(factor);
        }
    }

    /**
     * Does the operation r1 <-- r1 + (factor * r2) on the matrix.
     */
    public void comb(int r1, int r2, Fraction factor) {
        for (int i = 0; i < column; i++) {
            matrix[r1][i] = matrix[r1][i].add(matrix[r2][i].mul(factor));
        }
    }
    
    public Fraction rowGCD(int r, int last_column) {
        Fraction g = new Fraction();
        
        int i = 0;
        //We only really want the gcd of the coefficient matrix. No one cares about the other guy.
        for (; i < last_column + 1; i++) {
            //Setting g to the first nonzero entry in the row
            if (!matrix[r][i].equals(Fraction.ZERO)) {
                g = matrix[r][i];
                break;
            }
        }
        
        boolean all_neg = true; //Stores true if every nonzero entry is negative, and false otherwise
        for (; i < last_column + 1; i++) {
            if (!matrix[r][i].equals(Fraction.ZERO)) {
                if (matrix[r][i].compareTo(Fraction.ZERO) > 0) //matrix[r][i] cannot be 0.
                    all_neg = false;
                g = Fraction.gcd(g, matrix[r][i]);
            } else if (g.equals(Fraction.ONE)) {
                break;
            }
        }
        
        //If every nonzero entry is negative, multiply the gcd (which is always nonnegative) by -1
        if (all_neg)
            g = g.mul(Fraction.NEG_ONE);
        
        return g;
    }

    /**
     * Getter methods
     */
    public int rows() {
        return this.row;
    }

    public int columns() {
        return this.column;
    }

    public Fraction get(int r, int c) {
        return matrix[r][c];
    }

    /**
     * Setter method
     */
    public void set(int r, int c, Fraction value) {
        matrix[r][c] = value;
    }
}