import java.util.Random;

/**
 * Transports you to a random room
 *
 * @author Damon Ricci
 * @version 1.0
 */
public class TransporterRoom extends Room {
    /**
     * Constructor for objects of class TransporterRoom
     * @param description The room's description
     */
    public TransporterRoom(String description) {
        super(description);
    }

    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction Ignored.
     * @return A randomly selected room.
     */
    public Room getExit(String direction) {
        return findRandomRoom();
    }

    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
     */
    private Room findRandomRoom() {
        Random random = new Random();
        int randomIndex = random.nextInt(Room.allRooms.size());
        return Room.allRooms.get(randomIndex);
    }
}