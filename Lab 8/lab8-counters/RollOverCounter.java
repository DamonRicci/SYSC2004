public class RollOverCounter extends Counter
{
    /**
     * Constructs a new RollOverCounter whose current count is
     * initialized to DEFAULT_MINIMUM, and which counts between
     * DEFAULT_MINIMUM and DEFAULT_MAXIMUM, inclusive.
     */
    public RollOverCounter()
    {
        super();
    }

    /**
     * Constructs a new RollOverCounter whose current count is
     * initialized to minCount, and which counts between
     * minCount and maxCount, inclusive.
     * 
     * ex: RollOverCounter counter = new RollOverCounter(1, 5);
     */
    public RollOverCounter(int minCount, int maxCount)
    {
        super(minCount, maxCount);
    }
    
    /**
     * Increment this counter by 1.
     */
    public void countUp()
    {
        // If we've reached the maximum count, rolling over to the minimum value.
        if (isAtMaximum()) {
            reset();
        } else {
            incrementCount();
        }
    }
}
