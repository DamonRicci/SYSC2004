import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes 
 * @version 2006.03.30
 * 
 * @author Lynn Marshall and Damon Ricci
 * @version A2 Submission
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRoomStack;
    private Item currentItem;
    private int itemsAllowedToPickUp;
        
    /**
     * Create the game and initialise its internal map, as well
     * as the previous room (none) and previous room stack (empty).
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        itemsAllowedToPickUp = 1;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, transporterRoom;
        Item chair1, chair2, chair3, chair4, bar, computer1, computer2, computer3, tree1, tree2;
        
        // create some items
        chair1 = new Item("Chair1", "a wooden chair", 5);
        chair2 = new Item("Chair2", "a wooden chair", 5);
        chair3 = new Item("Chair3", "a wooden chair", 5);
        chair4 = new Item("Chair4", "a wooden chair", 5);
        bar = new Item("Bar", "a long bar with stools", 95.67);
        computer1 = new Item("PC1", "a PC", 10);
        computer2 = new Item("Mac1", "a Mac", 5);
        computer3 = new Item("PC2", "a PC", 10);
        tree1 = new Item("Tree1", "a fir tree", 500.5);
        tree2 = new Item("Tree2", "a fir tree", 500.5);
        Item cookie = new Item("cookie", "a delicious cookie", 0.1);
        Item beamer1 = new Beamer("a beamer", 1);
        Item beamer2 = new Beamer("a beamer", 1);
       
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporterRoom = new TransporterRoom("in the transporter room");
        
        // put items in the rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        theatre.addItem(chair1);
        pub.addItem(bar);
        lab.addItem(chair2);
        lab.addItem(computer1);
        lab.addItem(chair3);
        lab.addItem(computer2);
        office.addItem(chair4);
        office.addItem(computer3);
        theatre.addItem(cookie);
        lab.addItem(cookie);
        theatre.addItem(beamer1);
        pub.addItem(beamer2);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
    
        theatre.setExit("west", outside);
    
        pub.setExit("east", outside);
    
        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", transporterRoom); // Connect the transporter room to the lab
    
        office.setExit("west", lab);
    
        transporterRoom.setExit("north", lab); // Connect the transporter room back to the lab
    
        currentRoom = outside; // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("charge")) {
            chargeBeamer(command);
        }
        else if (commandWord.equals("fire")) {
            fireBeamer(command);
        }
        else if (commandWord.equals("inventory")) {
            printInventory();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom; // store the previous room
            previousRoomStack.push(currentRoom); // and add to previous room stack
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room
            System.out.println(currentRoom.getLongDescription());
    
            // display information about the player's currently held item
            if (currentItem != null) {
                System.out.println("You are carrying " + currentItem.getName() + ".");
            } else {
                System.out.println("You are not carrying anything.");
            }
        }
    }
    
    /**
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     *
     * @param command The command to be processed
     */
    private void eat(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Eat what?");
        } else {
            if (currentItem != null && currentItem.getName().equalsIgnoreCase("cookie")) {
                System.out.println("You have eaten the cookie and are no longer hungry.");
                itemsAllowedToPickUp = 5;
                currentItem = null;
            } else {
                System.out.println("You have no cookie to eat.");
            }
        }
    }
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /**
     * "Take" was entered. Attempt to take an item from the current room.
     * If the player is already carrying an item or the item is not in the room,
     * display an appropriate message.
     * 
     * @param command The command to be processed
     */
    private void takeItem(Command command) {
        if (currentItem != null) {
            System.out.println("You are already holding something.");
            return;
        }
    
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
    
        String itemName = command.getSecondWord();
        Item item = currentRoom.removeItem(itemName);
    
        if (item == null) {
            System.out.println("That item is not in the room.");
        } else {
            if (itemName.equals("cookie")) {
                currentItem = item;
                System.out.println("You picked up " + item.getName() + ".");
            } else if (itemsAllowedToPickUp > 0) {
                currentItem = item;
                System.out.println("You picked up " + item.getName() + ".");
                itemsAllowedToPickUp--;
            } else {
                System.out.println("You must eat a cookie before you can pick up any other items.");
                currentRoom.addItem(item); // Put the item back in the room
            }
        }
    }
    
    /**
     * "Drop" was entered. Attempt to drop the item the player is carrying.
     * If the player is not carrying anything, display an appropriate message.
     * 
     * @param command The command to be processed
     */
    private void dropItem(Command command) {
        if (currentItem == null) {
            System.out.println("You have nothing to drop.");
            return;
        }
    
        currentRoom.addItem(currentItem);
        System.out.println("You have dropped " + currentItem.getName() + ".");
        currentItem = null;
    }
    
    private void chargeBeamer(Command command) {
        if (currentItem != null && currentItem instanceof Beamer) {
            Beamer beamer = (Beamer) currentItem;
            if (!beamer.isCharged()) {
                beamer.charge(currentRoom);
                System.out.println("Beamer charged.");
            } else {
                System.out.println("Beamer is already charged.");
            }
        } else {
            System.out.println("You must be carrying a beamer to charge it.");
        }
    }
    
    private void fireBeamer(Command command) {
        if (currentItem != null && currentItem instanceof Beamer) {
            Beamer beamer = (Beamer) currentItem;
            if (beamer.isCharged()) {
                currentRoom = beamer.fire();
                System.out.println("Beamer fired. You are now in the charged room.");
                System.out.println(currentRoom.getLongDescription());
            } else {
                System.out.println("Beamer is not charged.");
            }
        } else {
            System.out.println("You must be carrying a beamer to fire it.");
        }
    }
    
    private void printInventory() {
        System.out.println("Inventory:");
        if (currentItem == null) {
            System.out.println(" - Nothing");
        } else {
        System.out.println(" - " + currentItem.getName());
        }
    }
}