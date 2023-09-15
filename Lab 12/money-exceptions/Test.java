/**
 * A class to test adding exceptions and errors to the Money class.
 * 
 * @author Lynn  Marshall and Damon Ricci
 * @version Solution
 */
public class Test
{
    /**
     * main program to test the Money class
     * 
     * @param args not used
     */
    public static void main(String[] args)
    {     
        Money m = new Money(5,0);
        Money m1 = new Money(-3,-1);
        Object o1 = new Money(-745);
        Object o2 = new Object();
        
        // Test 1
        try {
            // All arguments are valid.
            // This should output $2.30
            System.out.println(m.addMonies(5,95,o1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test 2
        try {
            // number is invalid (but calculation works)
            // After you complete part 1, Money's addMonies
            // method should throw an IllegalArgumentException.
            System.out.println(m.addMonies(0,95,o1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test 3
        try {
            // cents is invalid (but calculation works)
            // After you complete part 1, Money's addMonies
            // method should throw an IllegalArgumentException. 
            System.out.println(m.addMonies(10,105,o1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test 4
        try {
            // obj is null (null pointer runtime exception thrown
            // by Money's plus method)
            // After you complete part 1, Money's addMonies
            // method should throw a NullPointerException. 
            System.out.println(m.addMonies(5,95,null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test 5
        try {
            // obj is invalid (class cast runtime exception thrown
            // by the system)
            // After you complete part 1, Money's addMonies
            // method should throw a ClassCastException. 
            System.out.println(m.addMonies(5,95,o2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test 6
        try {
            // All arguments are valid.
            // This should output $1.99
            System.out.println(m.addMonies(10,0,m1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}