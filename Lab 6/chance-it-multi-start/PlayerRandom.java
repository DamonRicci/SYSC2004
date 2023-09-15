
/**
* The PlayerRandom class is a subclass of the abstract Player class that
* implements a random strategy for playing the game of Chance-It.
* This player stops rolling when the current roll is less than or equal to the
* first roll.
* 
* @author Damon Ricci
* @version 1.0
*/
public class PlayerRandom extends Player
{
    /**
     * Constructs a new random player.
     * 
     * @param d The dice object shared by all players.
     * @param myName The player's name.
     */
    public PlayerRandom(Dice d, String myName)
    {
        super(d, myName);
    }
    
    /**
     * Returns true if the player decides to end their current turn; 
     * false otherwise.
     * 
     * This player stops rolling when the current roll is less than or 
     * equal to the first roll.
     * 
     * @return true if the player decides to end their current turn; 
     *         false otherwise.
     */
    public boolean stopRolling() 
    {
        return (currentRoll <= firstRoll);
    }
}
