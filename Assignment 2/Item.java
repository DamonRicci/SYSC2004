
/**
 * This class represents an item which may be put
 * in a room in the game of Zuul.
 * 
 * @author Lynn Marshall and Damon Ricci
 * @version A2 Submission
 */
public class Item
{
    // name of the item
    private String name;

    // description of the item
    private String description;
    
    // weight of the item in kilograms 
    private double weight;

    /**
     * Constructor for objects of class Item.
     * 
     * @param name The name of the item
     * @param description The description of the item
     * @param weight The weight of the item
     */
    public Item(String name, String description, double weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Additional constructor for objects of class Item.
     *
     * @param description The description of the item
     * @param weight      The weight of the item
     */
    public Item(String description, double weight) {
        this.name = description; // Set the name to be the same as the description
        this.description = description;
        this.weight = weight;
    }

    /**
     * Returns the name of the item.
     * 
     * @return The name of the item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns a description of the item, including its
     * description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return name + ": "+ description + " that weighs " + weight + "kg.";
    }
}
