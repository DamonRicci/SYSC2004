import java.util.Random;

/**
 * Die models a 6-sided die.
 * 
 * @author D.L. Bailey, SCE, Carleton University
 * @version 1.01 January 22, 2006
 */
public class Die
{
    private Random generator;
 
    /**
     * Constructs a new Die object that uses the specified
     * random-number generator.
     * 
     * @param r the random number generator used by this Die.
     */
    public Die(Random r)
    {
        generator = r;
    }
    
    /**
     * Returns the result of rolling a die. The value returned
     * is a pseudorandom integer between 1 and 6, inclusive.
     */    
    public int roll()
    {
        /* The value returned by nextInt(6) will be a pseudorandom integer value 
         * between 0 and 5, inclusive. Map this to a value between 1 to 6, inclusive.
         */
        return generator.nextInt(6) + 1;
    }
}
