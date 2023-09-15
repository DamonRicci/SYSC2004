/**
 * An up/down counter with a simple GUI.
 * 
 * @author Lynn Marshall and Damon Ricci
 * @version November 17, 2012
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Counter implements ActionListener {
    /* The current value of the counter. */
    private int count;

    // The constants
    public static final int MINIMUM = 0;
    public static final int RESET_TO = 5;
    public static final int MAXIMUM = 10;

    /* A JTextField displays the current value of the counter. */
    private JTextField counterDisplay;

    /* The button that is clicked to increment the counter. */
    private JButton upButton;

    /* The button that is clicked to decrement the counter. */
    private JButton downButton;

    /* The button that is clicked to reset the counter. */
    private JButton resetButton;

    /* The button that is clicked to set a random count. */
    private JButton randomButton;

    /* The reset menu item */
    private JMenuItem resetItem;

    /* The clear menu item */
    private JMenuItem clearItem;

    /* The quit menu item */
    private JMenuItem quitItem;

    /* The history area */
    private JTextArea history;

    public Counter() {
        // model
        count = 0;

        // JFrame
        JFrame frame = new JFrame("Counter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Creating menu items
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        resetItem = new JMenuItem("Reset");
        optionsMenu.add(resetItem);

        clearItem = new JMenuItem("Clear");
        optionsMenu.add(clearItem);

        quitItem = new JMenuItem("Quit");
        optionsMenu.add(quitItem);

        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
        clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, SHORTCUT_MASK));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));

        // Add action listeners
        resetItem.addActionListener(this);
        clearItem.addActionListener(this);
        quitItem.addActionListener(event -> System.exit(0));

        // Top area with buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        upButton = new JButton("Up");
        buttonPanel.add(upButton);

        downButton = new JButton("Down");
        buttonPanel.add(downButton);
        downButton.setEnabled(false);

        resetButton = new JButton("Reset");
        buttonPanel.add(resetButton);

        randomButton = new JButton("Random");
        buttonPanel.add(randomButton);

        upButton.addActionListener(this);
        downButton.addActionListener(this);
        resetButton.addActionListener(this);
        randomButton.addActionListener(this);

        contentPane.add(buttonPanel, BorderLayout.NORTH);

        // Middle area with counter display
        JPanel counterPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Counter Value: ");
        label.setHorizontalAlignment(JLabel.RIGHT);
        counterPanel.add(label, BorderLayout.WEST);

        counterDisplay = new JTextField(5);
        counterDisplay.setEditable(false);
        counterDisplay.setFont(new Font(null, Font.BOLD, 18));
        counterDisplay.setHorizontalAlignment(JTextField.RIGHT);
        counterDisplay.setText("" + count);
        counterPanel.add(counterDisplay, BorderLayout.EAST);

        contentPane.add(counterPanel, BorderLayout.CENTER);

        // Bottom area with history text area
        history = new JTextArea(20, 10);
                history.append("\nThe counter value is: " + count);
        history.setCaretPosition(history.getDocument().getLength());

        JScrollPane scrollPane = new JScrollPane(history);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o instanceof JButton) {
            JButton button = (JButton) o;

            if (button == downButton) {
                count--;
            } else if (button == upButton) {
                count++;
            } else if (button == resetButton) {
                count = RESET_TO;
            } else if (button == randomButton) {
                Random random = new Random();
                count = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
            }
        } else {
            JMenuItem item = (JMenuItem) o;

            if (item == resetItem) {
                count = RESET_TO;
            } else if (item == clearItem) {
                history.setText("");
            }
        }

        downButton.setEnabled(count != MINIMUM);
        upButton.setEnabled(count != MAXIMUM);
        resetButton.setEnabled(count != RESET_TO);

        counterDisplay.setText("" + count);
        history.append("\nThe counter value is: " + count);
        history.setCaretPosition(history.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Counter());
    }
}
