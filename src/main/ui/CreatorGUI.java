package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


// This is the sub-level Graphical interface that creates a frame solely for the creator mode, this class
// will be used when the user clicks the Create Recipe button from the RecipeGUI interface.

public class CreatorGUI extends JFrame {

    private JButton submit;
    private JButton exit;
    private JButton addStep;
    private JTextArea ingredients;
    private JTextArea name;
    private JLabel title;
    private List<JTextArea> steps;
    private JPanel mainPanel;
    private JPanel stepsPanel;
    private int maxSteps = 10;
    private GuiEngine engine;
    private ImageIcon bgImage =
            new ImageIcon("C:\\UBC Documents\\CPSC 210\\project_r7h3s\\src\\main\\images\\creator_bg.png");


    // EFFECTS: constructs a new frame for the creator mode
    public CreatorGUI(GuiEngine engine) {
        this.engine = engine;
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 750));
        mainPanel.setBackground(new Color(255, 216, 107));

        this.setSize(500, 750);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setResizable(false);

        addAllComponents(mainPanel);


        this.add(mainPanel);
        this.setVisible(true);


    }

    // MODIFIES: this
    // EFFECTS: adds all the component the creator frame
    public void addAllComponents(JPanel panel) {
        addTitle(panel);
        addName(panel);
        addIngredients(panel);
        addTextAreas(panel);
        addSubmitButton(panel);

    }

    // MODIFIES: this
    // EFFECTS: adds a title to the frame
    public void addTitle(JPanel panel) {
        title = new JLabel("Create Your Own Recipe");
        title.setPreferredSize(new Dimension(450, 50));
        title.setFont(new Font("Impact", Font.BOLD, 24));
        title.setForeground(Color.darkGray);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);
    }


    // MODIFIES: this
    // EFFECTS: adds ingredients text area to the frame
    public void addIngredients(JPanel panel) {
        JLabel ingredientsLabel = new JLabel("Enter Ingredients: (separate each ingredient by ',')");

        ingredientsLabel.setPreferredSize(new Dimension(450,20));

        ingredients = new JTextArea();

        ingredients.setLineWrap(true); // Enable line wrapping
        ingredients.setWrapStyleWord(true); // Wrap at word boundaries
        ingredients.setPreferredSize(new Dimension(400, 100));
        ingredients.setFont(new Font("Arial", Font.PLAIN,16));
        ingredients.setBackground(new Color(247, 235, 200));

        panel.add(ingredientsLabel);
        panel.add(ingredients);
    }

    // MODIFIES: this
    // EFFECTS: adds a name input section to the frame
    public void addName(JPanel panel) {
        JLabel nameLabel = new JLabel("Enter Recipe Name: ");
        nameLabel.setPreferredSize(new Dimension(450, 20));
        name = new JTextArea();
        name.setPreferredSize(new Dimension(400, 25));
        name.setBackground(new Color(247, 235, 200));

        panel.add(nameLabel);
        panel.add(name);
    }

    // MODIFIES: this
    // EFFECTS: adds all teh textarea sections to the frame
    public void addTextAreas(JPanel panel) {
        JLabel stepsLabel = new JLabel("Enter Your Steps:");
        stepsLabel.setPreferredSize(new Dimension(250, 20));
        steps = new ArrayList<>();

        stepsPanel = new JPanel();
        stepsPanel.setPreferredSize(new Dimension(450, 415));
        stepsPanel.setBackground(new Color(255, 216, 107));

        addStep = new JButton("add step");
        addStep.setPreferredSize(new Dimension(150, 25));
        addStep.addActionListener(new AddStepAction());

        stepsPanel.add(stepsLabel);
        stepsPanel.add(addStep);

        panel.add(stepsPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds a submit button to the frame
    public void addSubmitButton(JPanel panel) {
        submit = new JButton("Submit");
        exit = new JButton("Exit");

        submit.setPreferredSize(new Dimension(150, 30));
        submit.setAlignmentX(SwingConstants.RIGHT);
        submit.addActionListener(new SubmitAction());

        exit.setPreferredSize(new Dimension(150, 30));
        exit.setAlignmentX(SwingConstants.RIGHT);
        exit.addActionListener(new ExitAction());

        panel.add(submit);
        panel.add(exit);
    }

    // this class creates an ActionListener that makes a pop up message when the exit button is pressed
    private class ExitAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: creates a pop-up frame when the user exits.
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();

            JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp, "You have exit creator mode!");
        }
    }

    // this class creates an ActionListener that calls the createRecipe function from the GuiEngine or
    // make a popup message when the submit button is pressed.
    private class SubmitAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: calls the createRecipe method and make a pop up frame.
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<String> procedures = listTextAreasToStringConverter(steps);
            engine.createRecipe(name.getText(), ingredients.getText(), procedures);

            dispose();

            JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp, "You have successfully created the recipe: " + name.getText());
        }
    }

    // EFFECTS: converts a list of JTextAreas to a list of string.
    public ArrayList<String> listTextAreasToStringConverter(List<JTextArea> areas) {
        ArrayList<String> procedures = new ArrayList<>();
        for (JTextArea area : areas) {
            procedures.add(area.getText());
        }
        return procedures;
    }

    // this class creates an ActionListener that creates a new textarea for user to input a new step as long as
    // the number of steps is within the maximum bound.
    private class AddStepAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: creates a new textarea for user input for steps
        @Override
        public void actionPerformed(ActionEvent e) {
            if (steps.size() + 1 <= maxSteps) {
                JLabel label = new JLabel("Step " + (steps.size() + 1) + ": ");
                label.setPreferredSize(new Dimension(55, 20));

                JTextArea newStep = new JTextArea();
                newStep.setPreferredSize(new Dimension(370,30));
                JScrollPane scroll = new JScrollPane(newStep);

                newStep.setLineWrap(true); // Enable line wrapping
                newStep.setWrapStyleWord(true); // Wrap at word boundaries
                newStep.setBackground(new Color(247, 235, 200));
                stepsPanel.add(label);
                stepsPanel.add(scroll);

                steps.add(newStep);
                revalidate();
                repaint();

            } else {
                JFrame popUp = new JFrame();
                JOptionPane.showMessageDialog(popUp, "Max steps used!");
            }
        }
    }

    // EFFECTS: resize the given image and return the resized version
    public ImageIcon imageResizer(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);

        return newIcon;
    }

}
