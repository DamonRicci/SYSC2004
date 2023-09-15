/**
 * Car models a car attached to a train, cars contain seats and are either
 * business class or economy class.
 *
 * @author Lynn Marshall, Damon Ricci SCE
 * @version 1.2 September 23, 2012
 */
public class Car
{
    /** This car's identifier. */
    private int id;
   
    /**
     * true == this car is a business-class car,
     * false == this car is an economy-class car.
     */
    private boolean businessClass;
    
    /** The cost of a business class seat, in dollars. */
    public static final double BUSINESS_SEAT_COST = 125.0;
    
    /** The cost of an economy class seat, in dollars. */
    public static final double ECONOMY_SEAT_COST = 50.0;    
  
    /** The number of seats in a business class car. */
    public static final int BUSINESS_SEATS = 30;   
    
    /** The number of seats in an economy class car. */
    public static final int ECONOMY_SEATS = 40;   
   
    /** The list of this car's seats. */
    private Seat[] seats;
   
    /**
     * Constructs a new Car object with the specified id.
     * If parameter isBusinessClass is true, the car is a business-class
     * car. If parameter isBusinessClass is false, the car is an
     * economy-class car.
     */
    public Car(int carId, boolean isBusinessClass)
    {
        // Set the id and businessClass values
        id = carId;
        businessClass = isBusinessClass;
        
        // Initialize the seats array and checks if business or not
        if (businessClass) {
            seats = new Seat[BUSINESS_SEATS];
            for (int i = 0; i < BUSINESS_SEATS; i++) {
                seats[i] = new Seat(i + 1, BUSINESS_SEAT_COST);
            }
        } else {
            seats = new Seat[ECONOMY_SEATS];
            for (int i = 0; i < ECONOMY_SEATS; i++) {
            seats[i] = new Seat(i + 1, ECONOMY_SEAT_COST);
            }
        }
    }

    /**
     * Returns this car's list of seats. This method is intended for 
     * testing purposes only, and should not be called by other objects,
     * as it may be removed from the final version of this class.
     * 
     * @return The seats in this car, an array of Seat objects.
     */
    public Seat[] seats()
    {
        return seats;
    }
 
    /** 
     * Returns true if this is a business-class car,
     * false if this is an economy-class car.
     */
    public boolean isBusinessClass()
    {
        return businessClass;
    }
 
    /**
     * Returns the id of this car.
     */
    public int id()
    {
        return id;
    }
  
    /**
     * This method is called when the specified seat has been booked,
     * to print a ticket for that seat.
     * 
     * @param seatNo The integer identifier of the seat.
     */
    private void printTicket(int seatNo)
    {
        System.out.println("Car ID: " + id);
        System.out.println("Seat number: " + seatNo);
        System.out.println("Price: ");
        if (businessClass) {
            System.out.println(BUSINESS_SEAT_COST);
        } else {
            System.out.println(ECONOMY_SEAT_COST);
        }
    }   
 
    /**
     * Attempts to book a seat. If successful, this method prints a 
     * ticket and returns true.
     * If no seats are available, this method returns false.
     */
    public boolean bookNextSeat()
    {
        // Iterate through the list of seats
        for (int i = 0; i < seats.length; i++)
        {
            // If a seat is available
            if (!seats[i].isBooked())
            {
                // Book the seat, print a ticket and return true
                seats[i].book();
                printTicket(seats[i].number());
                return true;
            }
        }
        // Return false to indicate that no seats are available
        return false;
    }

    /** 
     * Cancels the booking for the specified seat, which must be between
     * 1 and the maximum number of seats in the car.
     * If the seat number is valid and if the seat has been reserved, this
     * method cancels the booking for that seat and returns true. 
     * If the seat number is not valid, this method returns false. 
     * If the seat number is valid, but the seat has not been reserved, 
     * this method returns false.
     */
    public boolean cancelSeat(int seatNo)
    {
        // Check if the seat number is within the range of available seats
        if (seatNo >= 1 && seatNo <= seats.length) {
            // Check if the seat has been reserved
            if (seats[seatNo-1].isBooked()) {
                // Cancel the reservation and return true
                seats[seatNo-1].cancelBooking();
                return true;
            } else {
                // Seat has not been reserved, return false
                return false;
            }
        } else {
            // Seat number is not within the range of available seats, return false
            return false;
        }
    }    
}
