/**
 * The test class CounterTest.
 *
 * @author  Damon Ricci
 * @version Version 1.0
 */
public class CounterTest extends junit.framework.TestCase
{
    private Counter c1;
    private Counter c2;
    
    /**
     * Default constructor for test class CounterTest
     */
    public CounterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        c1 = new RollOverCounter(1, 10);
        c2 = new LimitedCounter(1, 10);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
    
    /**
     * Tests the original (lab 8) RollOverCounter methods.
     */
    public void testAllRollOverCounterMethods()
    {    
        /* Verify that the counter is in the correct initial state. */
        assertEquals(1, c1.minimumCount());
        assertEquals(10, c1.maximumCount());
        assertEquals(1, c1.count());        
        
        assertTrue(c1.isAtMinimum());
        assertFalse(c1.isAtMaximum());
  
        /* Count 1 -> 2 */
        
        c1.countUp();
        assertEquals(2, c1.count());
        assertFalse(c1.isAtMinimum());
        
        /* Count 3, 4, ...9, 10 */
        for (int i = 1; i < 9; i++) {
            c1.countUp();
        }
        assertTrue(c1.isAtMaximum());
        
        /* Count 10 -> 1 */
        c1.countUp();
        assertEquals(1, c1.count());
        
        /* Verify that reset works. */
        c1.countUp();
        c1.countUp();
        c1.reset();
        assertEquals(1, c1.count());
    }    
    
    /**
     * Tests the new methods in class RollOverCounter.
     */
    public void testNewRollOverCounterMethods()
    {
        /* Verify that the counter is in the correct initial state. */
        assertEquals(1, c1.minimumCount());
        assertEquals(10, c1.maximumCount());
        assertEquals(1, c1.count());        
        
        assertTrue(c1.isAtMinimum());
        assertFalse(c1.isAtMaximum());
        
        /* Test setToMaximum() method. */
        c1.setToMaximum();
        assertEquals(10, c1.count());
        assertTrue(c1.isAtMaximum());
        
        /* Test countDown() method. */
        c1.decrementCount();
        assertEquals(9, c1.count());
        assertFalse(c1.isAtMaximum());
    }
    
    /**
     * Tests the new methods in class LimitedCounter.
     */
    public void testNewLimitedCounterMethods()
    {
        /* Verify that the counter is in the correct initial state. */
        assertEquals(1, c2.minimumCount());
        assertEquals(10, c2.maximumCount());
        assertEquals(1, c2.count());        
        
        assertFalse(c2.isAtMaximum());
        
        /* Test setToMaximum() method. */
        c2.setToMaximum();
        assertEquals(10, c2.count());
        assertTrue(c2.isAtMaximum());
        
        /* Test countDown() method. */
        c2.decrementCount();
        assertEquals(9, c2.count());
        assertFalse(c2.isAtMaximum());
    }
    
    /**
     * Tests all methods in class LimitedCounter.
     */
    public void testAllLimitedCounterMethods()
    {
        /* Verify that the counter is in the correct initial state. */
        assertEquals(1, c2.minimumCount());
        assertEquals(10, c2.maximumCount());
        assertEquals(1, c2.count());        
        
        assertFalse(c2.isAtMaximum());
  
        /* Count 1 -> 2 */
        
        c2.countUp();
        assertEquals(2, c2.count());
        assertFalse(c2.isAtMinimum());
        
        /* Count 7, 8, 9, 10 */
        for (int i = 1; i < 9; i++) {
            c2.countUp();
        }
        assertTrue(c2.isAtMaximum());
        
        /* Count 10 -> 1 */
        c2.countUp();
        assertEquals(10, c2.count());
    }
}
