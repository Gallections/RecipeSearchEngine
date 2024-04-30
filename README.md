# *My Personal Project:* **Recipe Generation System**

## Introduction:

- This project is a recipe generation system. It has two primary functions; it allows the user to 
register information about personal recipes to the database and allow other users to view the 
registered recipes. It also allows the user to input ingredients and the system will 
generate the recipes that have the most overlap with the user inputs. Other features include 
showing the details of the recipe selected by the user with graphics and word description; listing 
all the recipes in the database; Liking and saving features; 
- This application is designed for 
users who aspire to learn and share knowledge of cuisines from diverse backgrounds. It aims to break 
cultural barriers and facilitate worldwide spread of regional delicacies. This project is particularly 
intriguing because it can potentially fulfill my goal of experiencing all world delicacies around the world. 
In addition, on a greater scale, it also allows regions with limited food resources to unlock new dishes,
indirectly facilitate the global advancement of human delicacies. 
- This project will enhance my familiarity with searching algorithms, which is common among applications ranging
from modern search engines to similar user-input based systems, these systems are widely used in modern service-based
firms, having a project such as recipe generator would be a great source to enhance my qualification.

## User Stories:
- As a user, I want to be able to add my own recipe to the database.
- As a user, I want to be able to input my available ingredients to search for 
recipes of matching delicacies.
- As a user, I want to be able to save a specific recipe.
- As a user, I want to be able to view all recipes in the database.
- As a user, I want to be able to search a recipe by name;
- As a user, I want to be able to remove a saved recipe.
- As a user, I want to be able to view all my saved recipes.
- As a user, I want to be able to view all of my uploaded recipes. 
- As a user, I want to be able to view all the matching recipes after entering 
my available ingredients.
- As a user, I want to be able to save all my progress.
- As a user, I want to reverse to the most recent saved state when I reload.
- As a user, I want to be reminded to save all progress before quitting.

## Instructions for Grader:
- You can search recipes in the database by name by inputting the name of the intended
recipe into the search field next to "Search Recipes by Name"
- You can search recommended recipes in the database based on available ingredients by inputting
the name of the ingredients separated by a "," into the search field named "Search Recipes by Ingredients"
- You can see all the recipes in the database one by one by clicking the button 
named "See All Recipes" to the right of the application.
- You can see all user created recipes in the database one by one by clicking the 
button "See Created Recipes" to the right of the application.
- You can see all user saved recipes in the database one by one by clicking the
  button "See Saved Recipes" to the right of the application.
- You can create a new recipe by clicking the button "Create Recipe" to the right of the application
You then we see a new frame pop up that allow you to input information about the new recipe.
- In the new "create recipe" frame, you can add name and ingredients of the recipe
into the corresponding text areas, if you want to add steps, click the button add step to create
a new step. Once you are done inputting the new recipe information, you can click
the button "Submit" to complete the creation of the new recipe. Alternatively, if you 
want to exit the application, click the "Exit" button
- If you want to delete a saved or created recipe, make sure you click the 
"See Saved Recipes" or "See Created Recipes" first, then click the 
"next" or "previous" button to find the recipe you want to delete. Once you found the ideal recipe, 
click the button "Delete Recipe" at the bottom of the application.
- If you are viewing recipes from any mode, you can click the "Save Current Recipe" to save
the currently viewing recipe to the user's list of saved recipes.
- Click the "Previous" button if you want to view the previous recipe in the list
- Click the "Next" button if you want to view the next recipe in the list
- You can save all user progress by clicking the "Save All" menu bar in the top left corner.
- You can load all user progress by clicking the "Load All" menu bar in the top left corner.

## Phase 4: Task 3:
- One potential area of improvement that I can make is to allow the user to add
an image of the recipe they created. This can be achieved by adding an extra button on the
user interface and allow the user to upload images on a separate frame. In the backend, 
I would need to create another container that stores all the user image and make sure these images
are stored in a JSON file. 
- My Recipe class lacks cohesion, it performs more than one task such as storing recipe information and 
organizing outputs, one strategy to improve this situation is to create a separate class named "ConsoleOrganizer"
that handles application output specifically. The reason that it is necessary to make such a improvement is to 
make debugging and program expansion easier in the future.