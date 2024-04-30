package ui;

import model.*;
import model.Event;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// This is the main GUI class that creates a Graphical user interface of the recipe search engine, it offers
// functions ranging from searching, saving, and accessing recipes.

public class RecipeGUI extends JFrame {

    private JPanel displayPanel;
    private JPanel searchPanel;
    private JPanel imagePanel;
    private ImageIcon iconPrev =
            new ImageIcon("data/images/prev.png");
    private ImageIcon iconNext =
            new ImageIcon("data/images/next.png");
    private ImageIcon question =
            new ImageIcon("data/images/question.png");
    private ImageIcon sorry =
            new ImageIcon("data/images/sorry_image.png");

    private ImageIcon loadingImage =
            new ImageIcon("data/images/loading.gif");

    private JButton prev;
    private JButton next;
    private JButton save;
    private JButton seeAllRecipesButton;
    private JButton seeAllSavedRecipesButton;
    private JButton deleteCurrentRecipeButton;
    private JButton createRecipeButton;
    private JButton seeAllCreatedRecipeButton;

    private JLabel imageLabel;
    private JLabel title;
    private JLabel recipeName;
    private JLabel recipeTitle;

    private JTextField searchField1;
    private JTextField searchField2;

    private JButton searchFieldButton1;
    private JButton searchFieldButton2;

    private JMenuItem saveMenu;
    private JMenuItem loadMenu;

    private GuiEngine engine;

    private JTextPane info;
    private JScrollPane infoContainer;
    private ImageIcon recipeImage;

    private SwingWorker<Void, Void> worker;

    private CreatorGUI creator;

    // EFFECTS: creates an instance of the MainGUI class
    public RecipeGUI() {
        engine = new GuiEngine("John");

        displayPanel = new JPanel();
        searchPanel = new JPanel();

        displayPanelSetup();
        searchPanelSetup();

        engine.loadProgress();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new PrintingEventsAdaptor());


        this.setSize(1000, 600);
        this.setResizable(false);

        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        addMenuBars(this);
        // addSearchFields(this);

        this.setBackground(new Color(198, 228, 245));
        this.add(searchPanel, BorderLayout.CENTER);
        this.add(displayPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the search Panel
    public void searchPanelSetup() {
        JPanel textPanel = new JPanel();

        searchPanel.setPreferredSize(new Dimension(1000, 200));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(new Color(198, 228, 245));

        createTitlePanel();
        addSearchFields(textPanel);

        searchPanel.add(textPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the display panel
    public void displayPanelSetup() {
        JPanel recipePanel = new JPanel();
        recipePanel.setLayout(new BorderLayout());

        displayPanel.setPreferredSize(new Dimension(1000, 400));
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(new Color(198, 228, 245));

        addRecipeInfo(recipePanel);
        displayPanel.add(recipePanel);
    }


    // MODIFIES: this
    // EFFECTS: adds a menu bar to the JFrame with options of save and load;
    public void addMenuBars(JFrame jframe) {
        JPanel panel = new JPanel();
        Color bgColor = new Color(180, 199, 212);
        BorderLayout layout = new BorderLayout();
        Border border1 = BorderFactory.createEmptyBorder(0, 15, 0, 15);
        Border border2 = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(border1, border2);

        JMenuBar menuBar = new JMenuBar();

        saveMenu = new JMenuItem("Save all");
        loadMenu = new JMenuItem("Load all");
        saveMenu.setBorder(border1);
        loadMenu.setBorder(border1);

        saveMenu.addActionListener(new SaveProgressBarAction());
        loadMenu.addActionListener(new LoadProgressBarAction());

        panel.setPreferredSize(new Dimension(800, 26));
        panel.setLayout(layout);

        menuBar.add(saveMenu);
        menuBar.add(loadMenu);

        panel.add(menuBar, BorderLayout.WEST);

        panel.setBackground(bgColor);
        jframe.add(panel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: creates a title for the main search engine panel
    public void createTitlePanel() {
        title = new JLabel("Recipe Search Engine");
        title.setPreferredSize(new Dimension(1000, 30));
        title.setFont(new Font("Impact", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        searchPanel.add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: create a JLabel with the given text and panelToAdd
    public void createLabel(String text, JPanel panelToAdd, Dimension dimension, Boolean center) {
        recipeName = new JLabel(text);
        recipeName.setPreferredSize(dimension);
        if (center) {
            recipeName.setHorizontalAlignment(SwingConstants.CENTER);
        }

        panelToAdd.add(recipeName);
    }

    // MODIFIES: this
    // EFFECTS: adds the search fields to the frame
    @SuppressWarnings({"checkstyle:MethodParamPad", "checkstyle:SuppressWarnings", "checkstyle:MethodLength"})
    public void addSearchFields(JPanel searchPanel) {
        ImageIcon questionMark = imageResizer(question, 14, 14);
        JLabel questionLabel = new JLabel(questionMark);
        questionLabel.setToolTipText("Use ',' to separate each ingredient");

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 300));
        panel.setBackground(new Color(198, 228, 245));

        searchField1 = new JTextField();
        searchFieldButton1 = new JButton("Search");
        searchField1.setHorizontalAlignment(SwingConstants.LEFT);

        searchField2 = new JTextField();
        searchFieldButton2 = new JButton("Search");
        searchField2.setHorizontalAlignment(SwingConstants.LEFT);

        searchField1.setPreferredSize(new Dimension(400, 20));
        searchField2.setPreferredSize(new Dimension(400, 20));

        searchFieldButton1.setPreferredSize(new Dimension (100, 20));
        searchFieldButton2.setPreferredSize(new Dimension (100, 20));

        searchFieldButton1.addActionListener(new SearchFieldOneAction());
        searchFieldButton2.addActionListener(new SearchFieldTwoAction());

        createLabel("Search Recipes by Name", panel, new Dimension(800, 20), true);
        panel.add(searchField1);
        panel.add(searchFieldButton1);
        createLabel("Search Recipes by Ingredients", panel, new Dimension(800, 20), true);
        panel.add(searchField2);
        panel.add(questionLabel);
        panel.add(searchFieldButton2);
        searchPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: adds the recipe information to the search panel
    public void addRecipeInfo(JPanel panel) {
        addRecipeImage(panel);
        addDisplayButtons(panel);
        addRecipeText(panel);
        addOtherFunctions(panel);
    }

    // MODIFIES: this
    // EFFECTS: adds the initial background image for the recipe image.
    public void addRecipeImage(JPanel panel) {
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(400, 300));
        imagePanel.setBackground(new Color(198, 228, 245));

        int width = 380;
        int height = 300;

        addRecipeTitle(panel);

        recipeImage = new ImageIcon("data/images/poster.png");
        ImageIcon newIcon = imageResizer(recipeImage, width, height);

        Border border = BorderFactory.createEmptyBorder(0, 10, 0, 10);

        imageLabel = new JLabel();
        imageLabel.setIcon(newIcon);
        imageLabel.setBorder(border);

        // label.setHorizontalAlignment(SwingConstants.CENTER);

        imagePanel.add(imageLabel);
        panel.add(imagePanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: adds the buttons that controls saves, prev, next
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void addDisplayButtons(JPanel panel) {
        JPanel buttonPanel = new JPanel();

        int width = 16;
        int height = 16;

        prev = new JButton();
        next = new JButton();
        save = new JButton();
        deleteCurrentRecipeButton = new JButton("Delete recipe");

//        Image scaledPrev = iconPrev.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        ImageIcon newPrev = new ImageIcon(scaledPrev);
        ImageIcon newNext = imageResizer(iconNext, width, height);
//        Image scaledNext = iconNext.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        ImageIcon newNext = new ImageIcon(scaledNext);
        ImageIcon newPrev = imageResizer(iconPrev, width, height);

        prev.setText("Previous");
        next.setText("Next");
        save.setText("Save Current Recipe");
        next.setHorizontalTextPosition(SwingConstants.LEFT);

        prev.setIcon(newPrev);
        next.setIcon(newNext);

        prev.setPreferredSize(new Dimension(120, 20));
        save.setPreferredSize(new Dimension(230, 20));
        next.setPreferredSize(new Dimension(120, 20));
        deleteCurrentRecipeButton.setPreferredSize(new Dimension(150, 20));

        prev.addActionListener(new PrevButtonAction());
        next.addActionListener(new NextButtonAction());
        save.addActionListener(new SaveRecipesButtonAction());
        deleteCurrentRecipeButton.addActionListener(new DeleteRecipeButtonAction());

        buttonPanel.add(prev);
        buttonPanel.add(save);
        buttonPanel.add(deleteCurrentRecipeButton);
        buttonPanel.add(next);

        buttonPanel.setBackground(new Color(180, 199, 212));
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: resize and returns a resized image
    public ImageIcon imageResizer(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);

        return newIcon;
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe title for the current recipe
    public void addRecipeTitle(JPanel panel) {
        JPanel titlePanel = new JPanel();
        recipeTitle = new JLabel("Recipe name: none");
        recipeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.setPreferredSize(new Dimension(600, 40));
        recipeTitle.setFont(new Font("Impact", Font.PLAIN, 18));
        recipeTitle.setForeground(new Color(250, 135, 12));
        titlePanel.setBackground(new Color(198, 228, 245));
        titlePanel.add(recipeTitle);
        panel.add(titlePanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe information box next to the image
    public void addRecipeText(JPanel panel) {
        JPanel recipe = new JPanel();
        recipe.setPreferredSize(new Dimension(380, 300));
        recipe.setBackground(new Color(198, 228, 245));

        info = new JTextPane();
        info.setText("No recipe information to display!");
        info.setAlignmentX(SwingConstants.CENTER);

        infoContainer = new JScrollPane(info);
        infoContainer.setPreferredSize(new Dimension(380, 300));
        recipe.add(infoContainer);
        panel.add(recipe, BorderLayout.CENTER);

    }

    // MODIFIES: this
    // EFFECTS: includes other functionalities such as see all saved recipes, see all recipes in database.
    public void addOtherFunctions(JPanel panel) {
        JPanel functions = new JPanel();

        seeAllRecipesButton = new JButton("See All Recipes");
        seeAllSavedRecipesButton = new JButton("See Saved Recipes");
        createRecipeButton = new JButton("Create Recipe");
        seeAllCreatedRecipeButton = new JButton("See Created Recipes");

        functions.setPreferredSize(new Dimension(190, 300));
        functions.setBackground(new Color(198, 228, 245));

        seeAllRecipesButton.setPreferredSize(new Dimension(150, 50));
        seeAllSavedRecipesButton.setPreferredSize(new Dimension(150, 50));
        createRecipeButton.setPreferredSize(new Dimension(150, 50));
        seeAllCreatedRecipeButton.setPreferredSize(new Dimension(150, 50));

        seeAllSavedRecipesButton.addActionListener(new SeeSavedRecipesAction());
        seeAllRecipesButton.addActionListener(new SeeAllRecipesButtonAction());
        createRecipeButton.addActionListener(new CreateRecipeButtonAction());
        seeAllCreatedRecipeButton.addActionListener(new SeeAllCreatedButtonAction());

        functions.add(seeAllRecipesButton);
        functions.add(seeAllSavedRecipesButton);
        functions.add(createRecipeButton);
        functions.add(seeAllCreatedRecipeButton);

        seeAllRecipesButton.setAlignmentY(SwingConstants.CENTER);

        panel.add(functions, BorderLayout.EAST);
    }

    // this class creates an Action Listener that calls the searchRecipeByNameHandler function to see all
    // name-matching recipes when the search recipe by name button is pressed.
    private class SearchFieldOneAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: retrieves all matching recipes by name

        @Override
        public void actionPerformed(ActionEvent e) {
            engine.searchRecipeByNameHandler(searchField1.getText(), info);
        }
    }

    // this class creates an Action Listener that calls the searchRecipe function to see all user
    // matching recipes when the search recipe by ingredients button is pressed.
    private class SearchFieldTwoAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: retrieves all matching recipes by ingredients
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.searchRecipe(searchField2.getText(), info);
        }
    }

    // this class creates an Action Listener that calls the saveCurrentRecipe function to save the current viewing
    // recipe when the save Current Recipe button is pressed.
    private class SaveRecipesButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: save the current viewing recipe to the list of saved recipes.
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.saveCurrentRecipe(info);
        }
    }

    // this class creates an Action Listener that calls the seeAllCreatedRecipe function to see all user saved recipes
    // when the see saved recipes button is pressed.
    private class SeeSavedRecipesAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads the recipe image, and retrieves all the saved recipes in the list
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.seeAllSavedRecipes(info);
            recipeImageOrganizer(imageLabel);
            recipeNameTracker(recipeTitle);
        }
    }

    // this class creates an Action Listener that calls the seeAllRecipe function to see all recipes in database
    // when the see Recipes button is pressed.
    private class SeeAllRecipesButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads the recipe image and retrieves all the recipes in the database.
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.seeAllRecipes(info);
            recipeImageOrganizer(imageLabel);
            recipeNameTracker(recipeTitle);
        }
    }

    // this class creates an Action Listener that calls the previousRecipe function to see previous recipes
    // when the previous button is pressed.
    private class PrevButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads the recipe image and retrieves the next recipe in the list
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.previousRecipe(info);
            recipeImageOrganizer(imageLabel);
            recipeNameTracker(recipeTitle);
        }
    }

    // this class creates an Action Listener that calls the nextRecipe function to see next recipes
    // when the next button is pressed.
    private class NextButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads the recipe image and retrieve the next recipe in the list
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.nextRecipe(info);
            recipeImageOrganizer(imageLabel);
            recipeNameTracker(recipeTitle);
        }
    }

    // this class creates an Action Listener that calls the loadProgress function to load all user progress
    // when the load all menu bar is pressed.
    private class LoadProgressBarAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads user progress
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.loadProgress();
        }
    }

    // this class creates an Action Listener that calls the saveProgress function to store all user progress
    // when the save all menu bar is pressed.
    private class SaveProgressBarAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: save user progress
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.saveProgress();
        }
    }

    // this class creates an Action Listener that calls the seeAllCreatedRecipe function to see all user created recipes
    // when the see Created Recipes button is pressed.
    private class SeeAllCreatedButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads the image and retrieves the created recipes.
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.seeAllCreatedRecipes(info);
            recipeImageOrganizer(imageLabel);
            recipeNameTracker(recipeTitle);
        }
    }

    // this class creates an Action Listener that calls the deleteRecipe function to delete the current recipe
    // when the Delete button is pressed.
    private class DeleteRecipeButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: deletes the recipe from the internal list of recipes
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.deleteRecipe(info);
        }
    }

    // this class creates an Action Listener that calls the createModeFrameCreator function to create a new frame
    // when the Create Recipe button is pressed.
    private class CreateRecipeButtonAction implements ActionListener {
        // MODIFIES: this
        // EFFECTS: calls the createModeFrameCreator for an instance of CreatorGui
        @Override
        public void actionPerformed(ActionEvent e) {
            createModeFrameCreator();
        }
    }

    // EFFECTS: creates an instance of CreatorGUI class
    public void createModeFrameCreator() {
        creator = new CreatorGUI(engine);
    }

    // MODIFIES: this
    // EFFECTS: change the image of the recipe according to the user action
    private void recipeImageOrganizer(JLabel label) {
        ImageIcon newSorry = imageResizer(sorry, 380, 300);
        label.setIcon(loadingImage);

        worker = new SwingWorker<Void, Void>() {
            // MODIFIES: this
            // EFFECTS: cease the background process and have the loading gif display for 3 seconds.
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000); // Simulate loading time
                return null;
            }

            // MODIFIES: this
            // EFFECTS: change the recipe icon to the sorry icon
            @Override
            protected void done() {
                // Remove the loading GIF and display the actual content
//                frame.remove(loadingLabel);
//                frame.add(contentPanel, BorderLayout.CENTER);
//                frame.revalidate();
//                frame.repaint();
                label.setIcon(newSorry);
            }
        };
        worker.execute();
    }

    // This class extends the WindowAdapter class, which prints out the all the key events that user have
    // performed through this application.
    private class PrintingEventsAdaptor extends WindowAdapter {

        // EFFECTS: logs all the key events the user has performed in the application.
        @Override
        public void windowClosing(WindowEvent e) {
            EventLog eventLog = EventLog.getInstance();
            System.out.println("You have launched the application!");
            for (Event event : eventLog) {
                System.out.println(event);
                System.out.println("");
            }
            System.out.println("You have exit the application!");
            // Close the application window
            dispose();
            System.exit(0); // End the program
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies the name section on the panel
    public void recipeNameTracker(JLabel label) {
        label.setText("Recipe Name: " + engine.getRecipeName());
    }

}
