import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
/**
 * A testing program that tests how well the Matrix.toString works. Uses random numbers for the numerator
 * and denominators of the Fractions.
 *
 * @author Tirthankar Mazumder
 * @version 1.7
 * @date 8th August, 2020 (minor updates on 9th and 10th August, 2020)
 */
public class test {
    public static void main(String[] args) {
        //I lifted the RNG code from StackOverflow. Don't ask how it works.
        int r = ThreadLocalRandom.current().nextInt(3, 8);
        int c = ThreadLocalRandom.current().nextInt(3, 8);

        Matrix m = new Matrix(r, c);

        long min = -1_000_000; //the underscores help read the number more clearly.
        long max = 1_000_000;

        System.out.println("Generating random numbers...");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (i == 0) {
                    BigInteger b = new BigInteger(Long.toString(random(min, max)));
                    m.set(i, j, new Fraction(b));
                } else {
                    BigInteger top = new BigInteger(Long.toString(random(min, max)));
                    //Using low class hacks because the BigInteger constructor that
                    //takes long inputs is apparently private
                    BigInteger bot = new BigInteger(Long.toString(random(min, max)));
                    m.set(i, j, new Fraction(top, bot));
                }
            }
        }

        System.out.println(m.toString());
        System.out.println(m.augmentedMatrixToString());
    }

    public static long random(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }
}