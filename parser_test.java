import java.util.*;
/**
 * A test class to see whether Parser works as expected. (Please do!)
 *
 * @author Tirthankar Mazumder
 * @version 1.0
 * @date 9th August, 2020
 */
public class parser_test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("r = ");
        int r = sc.nextInt();
        System.out.print("c = ");
        int c = sc.nextInt();
        
        System.out.println();
        
        Matrix m = new Matrix(r, c + 1);
        
        Parser.takeInput(m);
        System.out.println(m.toString());
    }
}