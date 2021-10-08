import java.util.*;
import java.math.BigInteger;
/**
 * Takes input from the user and constructs a matrix from the input received.
 * 
 * —————————————————————————————————————————————————————————————————————————————
 * Provides input capabilities for so that an user can actually use the Gauss
 * and GaussJordan classes to solve systems of linear equations.
 * 
 * Assumes an adversarial user and does extensive input validation.
 *
 * @author Rohith Nibhanupudi
 * @author Tirthankar Mazumder (lead contributor)
 * @version 8.10.20
 */
public class Parser {
    private static void instructions() {
        System.out.println("Enter the coefficients from left to right for each row.");
        System.out.println("Press the Enter key twice when you're done inputting values.\n");
        
        System.out.println("Or, if you're not used to this type of input, that's okay too because the");
        System.out.println("program is smart enough to deal with that as well. :)\n");
        
        System.out.println("Separate each coefficient with a space and enter fractions as a/b:");
    }

    public static boolean takeInput(Matrix m) {
        Scanner sc = new Scanner(System.in);

        int r = m.rows();
        int c = m.columns();

        instructions();

        String str = sc.nextLine();
        ArrayList<String> split = new ArrayList<>();

        for (String s: str.split("\\s+")) {
            split.add(s);
        }

        /**
         * This little gem was written because it is needed. 'Nuff said.
         * 
         * The idea is that we'll use an ArrayList to temporarily hold all the raw input.
         * We do that so that we can deal with the case where the user breaks the input
         * over multiplie lines. When the user is done inputting numbers, we convert the
         * ArrayList into a String array and run our input validation and then finally
         * convert it into Fractions and fill up the Matrix.
         */

        if (split.size() < r * c) {
            str = sc.nextLine();

            while (str != null && !str.isEmpty()) {
                //while the string is not empty, do this:
                for (String s: str.split("\\s+")) {
                    split.add(s);
                }
                str = sc.nextLine();
            }
            sc.close(); //Since we are done with the Scanner, it's a good idea to close it.
            
            if (split.size() == r * c) {
                split.trimToSize();
                return setMatrix((String[]) split.toArray(new String[0]), m);
                //see https://stackoverflow.com/a/9572820/12591388
            } else if (split.size() < r * c) {
                System.out.println("You input too few numbers! Exiting...");
            } else {
                System.out.println("You input too many numbers! Exiting...");
            }
        } else if (split.size() == r * c) {
            sc.nextLine(); //The dirtiest hack ever. To make it so that the instructions are the same for both cases,
            //we are consuming the second Enter key press
            //It's dirty because the other case actually requires it for the program to work correctly, but this is
            //just fluff
            sc.close(); // Since we are done with the Scanner, it's a good idea to close it.
            split.trimToSize();
            return setMatrix((String[]) split.toArray(new String[0]), m);
        } else {
            System.out.println("You input too many numbers! Exiting...");
        }
        return false;
    }

    private static boolean validate(String s) {
        int ind = s.indexOf("/");
        if (ind != s.lastIndexOf("/") || ind == s.length() - 1) {
            return false;
        } else {
            //Then there is only one forward slash in the String. This means it might be a correctly input fraction
            //This also includes Strings with 0 slashes in them, which correspond to integers.
            return true; //then the String passed the preliminary test. The rest of the tests will be run in
            //setMatrix() (which can't be run here since it involves a try-catch loop.
        }
    }

    private static boolean setMatrix(String[] s, Matrix m) {
        //At this point, we have all the raw user input. Now, we just need to validate it and then throw it into the
        //Matrix so that the other programs can do their thing.
        boolean cont = true;

        for (String str: s) {
            if (!validate(str)) {
                System.out.println("Invalid input detected. Exiting...");
                cont = false;
                break;
            }
        }
        //cont stores whether we should continue with our checks and the program or not.

        if (cont) {
            //Preliminary checks passed, continue with more expensive checks and start inputting stuff into the matrix.
            int c = m.columns();
            
            int curr_r = 0; //short for current row
            int curr_c = 0; //short for current column
            
            outer: //Label for the for loop so that we can break out of it if necessary
            for (int i = 0; i < s.length; i++) {
                int ind = s[i].indexOf("/");
                
                if (ind == -1) {
                    try {
                        BigInteger b = new BigInteger(s[i]);
                        m.set(curr_r, curr_c, new Fraction(b));
                        
                        //Properly incrementing curr_r and curr_c
                        curr_c++;
                        if (curr_c % c == 0) {
                            curr_c = 0;
                            curr_r++;
                        }
                    } catch (NumberFormatException ne) {
                        System.out.println("Invalid input detected. Exiting...");
                        cont = false;
                        break outer;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        cont = false;
                        break outer;
                    }
                } else {
                    String num = s[i].substring(0, ind);
                    String denom = s[i].substring(ind + 1);
                    try {
                        BigInteger big_n = new BigInteger(num); //short for BigInteger_numerator
                        BigInteger big_d = new BigInteger(denom); //short for BigInteger_denominator
                        m.set(curr_r, curr_c, new Fraction(big_n, big_d));
                        
                        //Properly incrementing curr_r and curr_c
                        curr_c++;
                        if (curr_c % c == 0) {
                            curr_c = 0;
                            curr_r++;
                        }
                    } catch (NumberFormatException ne) {
                        System.out.println("Invalid input detected. Exiting...");
                        cont = false;
                        break outer;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        cont = false;
                        break outer;
                    }
                }
            }
        }
        return cont; //If cont is true, then it means that it's safe to try and perform Gaussian or Gauss-Jordan elimination
        //because we are certain that the Matrix contains legitimate values.
    }
}
