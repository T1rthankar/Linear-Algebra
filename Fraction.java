import java.util.*;
import java.math.BigInteger;
/**
 * Reduced fractions returned by all operation methods
 * isEqual method checks equality
 * 
 * ————————————————————————————————————————————————————————————————————————————————————
 * 
 * Defines a rational number data type which uses arbitrary-precision numeric values
 * (BigIntegers) to store the numerator and denominator.
 * 
 * All four standard operations (addition, subtraction, multiplication, and division)
 * are defined.
 * 
 * In addition, the class features three predefined rational numbers: zero (called
 * ZERO), one (called ONE), and negative one (called NEG_ONE).
 * 
 * The class stores rational numbers in their reduced form internally. I.e., if a Fraction
 * is instantiated with 2 and 4 as the numerator and denominator, respectively, the class
 * reduces the number to 1/2 internally.
 * 
 * Note to self: Perhaps doing that is not such a great idea.
 * 
 * @author Tirthankar Mazumder (lead contributor)
 * @author Rohith Nibhanupudi (lead contributor)
 * @version 8.10.20
 */
public class Fraction implements Comparable<Fraction> {
    public static final Fraction ZERO = new Fraction("0", "1");
    public static final Fraction ONE = new Fraction("1", "1");
    public static final Fraction NEG_ONE = new Fraction("-1", "1");

    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction() {
        this.numerator = new BigInteger("1");
        this.denominator = new BigInteger("1");
    }

    public Fraction(String a) {
        this.numerator = new BigInteger(a);
        this.denominator = new BigInteger("1");
    }
    
    public Fraction(BigInteger b) {
        this.numerator = b;
        this.denominator = new BigInteger("1");
    }

    public Fraction(String a, String b) {
        this.numerator = new BigInteger(a);
        this.denominator = new BigInteger(b);
        reduce(this);
    }

    public Fraction(BigInteger a, BigInteger b) {
        this.numerator = a;
        this.denominator = b;
        reduce(this);
    }

    public Fraction add(Fraction f2) {
        Fraction sum = new Fraction();
        sum.numerator = this.numerator.multiply(f2.denominator).add(f2.numerator.multiply(this.denominator));
        sum.denominator = this.denominator.multiply(f2.denominator);
        return reduce(sum);
    }

    public Fraction sub(Fraction f2) {
        Fraction diff = new Fraction();
        diff.numerator = this.numerator.multiply(f2.denominator).subtract(f2.numerator.multiply(this.numerator));
        diff.denominator = this.denominator.multiply(f2.denominator);
        return reduce(diff);
    }

    public Fraction mul(Fraction f2) {
        Fraction product = new Fraction();
        product.numerator = this.numerator.multiply(f2.numerator);
        product.denominator = this.denominator.multiply(f2.denominator);
        return reduce(product);
    }

    public Fraction div(Fraction f2) {
        Fraction quotient = new Fraction();
        quotient.numerator = this.numerator.multiply(f2.denominator);
        quotient.denominator = this.denominator.multiply(f2.numerator);
        return reduce(quotient);
    }

    public static Fraction reduce(Fraction f) {
        BigInteger gcd = f.numerator.gcd(f.denominator);
        f.numerator = f.numerator.divide(gcd);
        f.denominator = f.denominator.divide(gcd);
        if (f.denominator.compareTo(BigInteger.ZERO) < 0) {
            BigInteger NEG_ONE = new BigInteger("-1");
            f.numerator = f.numerator.multiply(NEG_ONE);
            f.denominator = f.denominator.multiply(NEG_ONE);
        }
        return f;
    }
    
    public static Fraction inverse(Fraction f) {
        /* Returns the multiplicative inverse of f. */
        return reduce(new Fraction(f.denominator, f.numerator));
    }

    public static Fraction gcd(Fraction f1, Fraction f2) {
        return new Fraction(f1.numerator.gcd(f2.numerator), f1.denominator.gcd(f2.denominator));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Fraction) {
            Fraction cpyObj = (Fraction) o;
            return this.numerator.multiply(cpyObj.denominator).equals(cpyObj.numerator.multiply(this.denominator));
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.denominator.equals(BigInteger.ONE))
            return this.numerator.toString();
        else
            return this.numerator + "/" + this.denominator;
    }

    @Override
    public int compareTo(Fraction f) {
        if (this == f)
            return 0;
        else if (this.equals(f))
            return 0;

        //This logic works because we ensure that only the numerator is negative
        return (this.numerator.multiply(f.denominator)).compareTo(this.denominator.multiply(f.numerator));
    }
    
    public int numeratorLength() {
        /* Returns the length of the numerator, in terms of number of characters.
         * Used in the Matrix.toString() method for ensure correct alignment. */
        return this.numerator.toString().length();
    }
    
    public int denominatorLength() {
        /* Returns the length of the denominator, in terms of number of characters.
         * Used in the Matrix.toString() method for ensure correct alignment. */
        return this.denominator.toString().length();
    }
    
    public BigInteger getNumerator() {
        return this.numerator;
    }
    
    public BigInteger getDenominator() {
        return this.denominator;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the numerator of Fraction 1: ");
        String a = s.nextLine();
        System.out.println("Enter the denominator of Fraction 1: ");
        String b = s.nextLine();
        System.out.println("Enter the numerator of Fraction 2: ");
        String c = s.nextLine();
        System.out.println("Enter the denominator of Fraction 2: ");
        String d = s.nextLine();

        System.out.println("Enter a for addition, s for subtraction, m for multiplication, and d for division, and c for comparison: ");
        String str = s.nextLine();

        Fraction f1 = new Fraction(a, b);
        Fraction f2 = new Fraction(c, d);

        if (f1.equals(f2))
            System.out.println("Fractions 1 and 2 are equal.");
        else
            System.out.println("Fractions 1 and 2 are not equal.");

        if (str.equals("a"))
            System.out.println(f1.add(f2));
        else if(str.equals("s"))
            System.out.println(f1.sub(f2));
        else if(str.equals("m"))
            System.out.println(f1.mul(f2));
        else if(str.equals("d"))
            System.out.println(f1.div(f2));
        else
            System.out.println(f1.compareTo(f2));
    }
}