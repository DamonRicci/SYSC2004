/**
 * A Heater models a simple space-heater. The operations provided by a Heater
 * object are:
 * 1. Increase and decrease the temperature setting by a set amount.
 * 2. Return the current temperature setting.
 * 3. Change the set amount by which the temperature is increased and lowered.
 * 
 * @author L.S. Marshall, SCE, Carleton University
 * (incomplete implementation for SYSC 2004 Lab 2)
 * @author Damon Ricci 101229913
 * @version 1.03 January 20th, 2023
 */
public class Heater
{
    /** The temperature setting that the heater should maintain. */
    private int temperature;
    
    /** The temperature setting for a newly created heater. */
    private static final int INITIAL_TEMPERATURE = 15;
    
    /** 
     * The amount by which the temperature setting is raised/lowered when
     * warmer() and cooler() are invoked.
     */
     private int increment;
    
    /** 
     * The default amount by which the temperature setting is 
     * increased when warmer() is invoked and decreased when cooler()
     * is invoked.
     */
    private static final int DEFAULT_INCREMENT = 5;
    
    private static final int DEFAULT_MIN = 0;
    private static final int DEFAULT_MAX = 100;
    
    private int min;
    private int max;
    
    /**
     * Constructs a new Heater with an initial temperature setting of 15
     * degrees, and which increments and decrements the temperature
     * setting in increments of 5 degrees.
     */
    public Heater()
    {
        temperature = INITIAL_TEMPERATURE;
        increment = DEFAULT_INCREMENT;
        min = DEFAULT_MIN;
        max = DEFAULT_MAX;
    }
 
    /**
     * Write an appropriate comment here.
     */    
    public Heater(int minTemp, int maxTemp)
    {
        temperature = INITIAL_TEMPERATURE;
        increment = DEFAULT_INCREMENT;
        min = minTemp;
        max = maxTemp;
    }

    /**
     * Returns the current temperature
     */    
    public int temperature()
    {
        return temperature;
    }
    
    /**
     * Increases the temperature by a set increment
     */
    public void warmer()
    {
        if (!(temperature + increment > max))
       {
            temperature = temperature + increment;
        }
    }

    /**
     * Decreases the temperature by a set increment
     */    
    public void cooler()
    { 
         if (!(temperature - increment < min))
       {
            temperature = temperature - increment;
        }
    }
    
    
    /**
     * Sets a new positive increment for the heater to use
     */    
    public void setIncrement(int newIncrement)
    { 
        if (newIncrement > 0) {
            increment = newIncrement;
        }
    }
}
