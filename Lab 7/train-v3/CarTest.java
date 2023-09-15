/**
 * The test class CarTest.
 *
 * @author  Lynn Marshall, SCE
 * @version 1.2 May 1st, 2015
 */
public class CarTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class CarTest
     */
    public CarTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
    
    public void testCreateBusinessCar()
    {
        Car aCar = new Car(1385, true);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */
        assertEquals(Car.BUSINESS_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.BUSINESS_SEAT_COST, seats[i].price());
        }
    }
    
    public void testCreateEconomyCar()
    {
        Car aCar = new Car(1400, false);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */        
        assertEquals(Car.ECONOMY_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */       
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.ECONOMY_SEAT_COST, seats[i].price());
        }
    }    
    
    public void testID()
    {
         Car aCar;
         aCar= new Car(1385, true);
         assertEquals(1385, aCar.id());
         aCar = new Car(1400, false);
         assertEquals(1400, aCar.id());
    }
    
    public void testIsBusinessClass()
    {
         Car aCar;
         aCar = new Car(1385, true);
         assertTrue(aCar.isBusinessClass());
         aCar = new Car(1400, false);
         assertFalse(aCar.isBusinessClass()); 
    }
    
    /**
     * Test that booking consecutive seats in a car works as expected for both economy and business cars.
     */
    public void testBookNextSeat() {
        // create two cars, one economy and one business
        Car economyCar = new Car(1234, false);
        Car businessCar = new Car(5678, true);

        // get the seats for each car
        Seat[] economySeats = economyCar.seats();
        Seat[] businessSeats = businessCar.seats();

        // check that no seats are initially booked
        for (Seat seat : economySeats) {
            assertFalse(seat.isBooked());
        }

        for (Seat seat : businessSeats) {
            assertFalse(seat.isBooked());
        }

        // book each seat in order and check that it is booked
        for (int i = 0; i < economySeats.length; i++) {
            assertFalse(economySeats[i].isBooked()); // not booked
            assertTrue(economyCar.bookNextSeat()); // book it
            assertTrue(economySeats[i].isBooked()); // now booked
            if (i != economySeats.length - 1) {
                assertFalse(economySeats[i + 1].isBooked()); // but next isn't
            }
        }

        for (int i = 0; i < businessSeats.length; i++) {
            assertFalse(businessSeats[i].isBooked()); // not booked
            assertTrue(businessCar.bookNextSeat()); // book it
            assertTrue(businessSeats[i].isBooked()); // now booked
            if (i != businessSeats.length - 1) {
                assertFalse(businessSeats[i + 1].isBooked()); // but next isn't
            }
        }

        // try to book a seat now that all the seats have been booked and make sure it fails
        assertFalse(economyCar.bookNextSeat());
        assertFalse(businessCar.bookNextSeat());
    }
    
public void testCancelSeat() {
        // create two cars, one economy and one business
        Car economyCar = new Car(1234, false);
        Car businessCar = new Car(5678, true);
    
        // get the seats for each car
        Seat[] economySeats = economyCar.seats();
        Seat[] businessSeats = businessCar.seats();

        /* Cancel seat 0. cancelSeat() should return false, as there
         * is no seat 0.
         */
        assertFalse(economyCar.cancelSeat(0));
        assertFalse(businessCar.cancelSeat(0));

        /* Try cancelling a seat whose number is one higher than 
         * the highest valid seat number (seats.length - 1). 
         * cancelSeat() should return false.
         */
        assertFalse(economyCar.cancelSeat(economySeats.length));
        assertFalse(businessCar.cancelSeat(businessSeats.length));

        /* Try cancelling all the seats in the car, even though 
         * they haven't been booked. cancelSeat() should 
         * return false.
         */
        for (Seat seat : economySeats) {
            assertFalse(economyCar.cancelSeat(seat.number()));
        }

        for (Seat seat : businessSeats) {
            assertFalse(businessCar.cancelSeat(seat.number()));
        }

        /* Book all the seats */
        for (Seat seat : economySeats) {
            economyCar.bookNextSeat();
        }

        for (Seat seat : businessSeats) {
            businessCar.bookNextSeat();
        }

        /* Try cancelling all the seats in the car. */
        for (Seat seat : economySeats) {
            assertTrue(economyCar.cancelSeat(seat.number()));
        }

        for (Seat seat : businessSeats) {
            assertTrue(businessCar.cancelSeat(seat.number()));
        }

        /* In case seat numbers are off, try some more tests. */
        Car bCar;
        bCar = new Car (4321,false);
        // book 2 seats
        assertTrue(bCar.bookNextSeat());
        assertTrue(bCar.bookNextSeat());
        // try to cancel the 3rd (not booked)
        assertFalse(bCar.cancelSeat(3));
        // cancel the 1st and 2nd (were both booked)
        assertTrue(bCar.cancelSeat(1));
        assertTrue(bCar.cancelSeat(2));


        Car cCar;
        cCar = new Car (4321,true);
        // book 2 seats
        assertTrue(cCar.bookNextSeat());
        assertTrue(cCar.bookNextSeat());
        // try to cancel the 3rd (not booked)
        assertFalse(cCar.cancelSeat(3));
        // cancel the 1st and 2nd (were both booked)
        assertTrue(cCar.cancelSeat(1));
        assertTrue(cCar.cancelSeat(2));
        
        /* Try cancelling a booking of seat 50 in each car.
         * This should be unsuccessful, as there is no seat 50
         * in either car.
         */
        assertFalse(economyCar.cancelSeat(50));
        assertFalse(businessCar.cancelSeat(50));
    }
}
