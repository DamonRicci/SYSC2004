
/**
 * Write a description of class PlayerGreedy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerGreedy extends Player
{
    /**
     * Constructs a new random player.
     * 
     * @param d The dice object shared by all players.
     * @param myName The player's name.
     */
    public PlayerGreedy(Dice d, String myName)
    {
        super(d, myName);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public boolean stopRolling() {
        return (currentRoll > opponentsTurnScore);
    }
}
