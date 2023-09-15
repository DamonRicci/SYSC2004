
/**
 * Beams the player back to where it was charged.
 *
 * @author Damon Ricci
 * @version 1.0
 */
public class Beamer extends Item {
    private Room chargedRoom;

    public Beamer(String description, double weight) {
        super("Beamer", description, weight); // Pass "Beamer" as the custom name
        chargedRoom = null;
    }

    public boolean isCharged() {
        return chargedRoom != null;
    }

    public void charge(Room room) {
        chargedRoom = room;
    }

    public Room fire() {
        Room targetRoom = chargedRoom;
        chargedRoom = null;
        return targetRoom;
    }
}
