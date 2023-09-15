/**
 * Player models one player in the game of Chance-It.
 * 
 * @author Lynn Marshall
 * @version Lab 6 Starting Point
 */
public abstract class Player
{
    /* The player's name. */
    private String name;
    
    /* The Dice object shared by all players. */
    private Dice pairOfDice;
    
    /* The value obtained by the most recent roll of the dice. */
    protected int currentRoll;    
    
    /* 
     * The value obtained the first time that the dice are rolled during a turn.
     * During the turn, if the player rolls this value a second time, the turn 
     * ends and the turn score for is 0.
     */ 
    protected int firstRoll;
    
    /* The number of rolls taken so far in this turn. */
    protected int numRolls;
    
    /* The score obtained during the current turn. */
    private int turnScore;
    
    /* 
     * The player's cumulative score; i.e., the sum of the turn scores for 
     * all of the rounds played so far.
     */
    private int totalScore;
    
    /* 
     * The turnScore obtained by the previous player. This value
     * may be useful when formulating the strategy that determines when to 
     * stop rolling the dice.
     */
    protected int opponentsTurnScore;
    
    /**
     * Constructs a new player using the specified dice.
     * 
     * @param d The dice object shared by all players.
     * @param name The player's name.
     */
    public Player(Dice d, String myName)
    {
        pairOfDice = d;
        name = myName;
        totalScore = 0;
    }
    
    /**
     * Resets this player's total score to 0.
     */
    public void resetTotalScore()
    {
        totalScore = 0;
    }

    /**
     * Returns this players's cumulative score.
     * 
     * @return The sum of this player's turn scores for all of the rounds 
     *         played so far.
     */
    public int score() 
    {
        return totalScore;
    }  

    /**
     * Returns this player's name.
     * 
     * @return The name of this player.
     */
    public String name()
    {
        return name;
    }
     
    /**
     * Takes one turn for this player.
     * 
     * @param otherTurnScore The most recent turn score obtained by the 
     *        other player. This value  may be useful when formulating the
     *        strategy that determines when to stop rolling the dice.
     * @return The score obtained by this player during the turn.
     */
    public int takeTurn(int otherTurnScore)
    {      
        opponentsTurnScore = otherTurnScore;
        
        currentRoll = pairOfDice.roll();
        firstRoll = currentRoll;
        turnScore = currentRoll; 
        numRolls = 1;
        System.out.println("\t\tFirst roll: " + firstRoll);
        
        for(;;) {
            if (stopRolling()) {
                totalScore = totalScore + turnScore;
                System.out.print("\t\tStopping " + name);
                System.out.println(" turn with turn score = " + turnScore + 
                                    ", total score = " + totalScore);
                return turnScore;
            }
            
            currentRoll = pairOfDice.roll();
            numRolls++;
            System.out.print("\t\t" + name);
            System.out.println(" rolled: " + currentRoll);
            
            if (currentRoll == firstRoll) {
                turnScore = 0;
                System.out.println("\t\tOops! Rolled a match!");
                System.out.print("\t\tStopping " + name);
                System.out.println(" turn with turn score = " + turnScore + 
                                    ", total score = " + totalScore);
                return turnScore;
            }
            
            // The current roll is not the same as the first roll.           
            if (currentRoll > turnScore)
                turnScore = currentRoll;
        }
    }
  
    /** 
     * Encapsulates the strategy used by this player to determine when to 
     * stop rolling the dice during each turn.
     * 
     * All concrete subclasses must provide a strategy for the player
     * 
     * @return true when the player decides to end their current turn;
     *         otherwise returns false.
     */
    // Update this method so that it functions as intended (see the documentation above).  
    // Can an abstract method be private?  If not, what is the correct choice for visibility?
    protected abstract boolean stopRolling();
}
