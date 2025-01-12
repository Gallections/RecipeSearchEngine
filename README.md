# Recipe Search Engine

## Table of Contents
- [Content Description](#content-description)
- [Core_Features](#core-features)
- [Installation](#installation)
- [Usage](#usage)
- [Demo/Site](#demosite)
- [Q/A](#q/a)
- [Contributing](#contributing)
- [Technologies Used](#technologies-used)
- [In Progress](#in-progress)
- [License](#license)

## Content Description
Recipe Search Engine is a full stack application designed to help users find recipes based on ingredients, recipe names, or specific dietary preferences. This project is intended for personal use, which user can add recipes they wish in the database that is created upon the download of this application. Whether you're looking to cook with what's available in your kitchen or trying to find a new meal to try, this application makes recipe discovery easy and fast. With a user-friendly interface, it allows users to search, create, save recipes, and many more.

## Core Features:
- Contains a core database inside the application
- Search recipe by name using the top search bar
- Search recipe by ingredients using the seconddary search bar, this process will recommend possible recipes given the ingredients the user enter.
- See all recipes currently stored in the database by tapping the "see all recipes" Button.
- User can create their custom recipe by clicking the "create recipe" button.
- User can toggle through different recipes by clicking through the buttons at the bottom.
- User can save and delete recipes that were created.
- More answers can be found in the [Q/A](#Q/A) section.

## Installation
Follow these steps to set up the project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/Gallections/RecipeSearchEngine.git

2. Navigate to the src folder and find MainGUI in the ui sub folder.
3. Run the MainGUI file.
4. Now you have access to the User Interface of the application.
   
## Usage
1. Use the search bars at the top to search for recipes either based on name or ingredients.
2. Set up your own database that stores personal recipes.
3. Create your personal recipes and save it to the database.

## Demo/Site
Visit [LINK](https://www.youtube.com/watch?v=sPfJuewbq0E) for a video demonstation of the project

## Q/A
1. Will this project be expanded to a larger database that allow user authentication and open the database for all users across the network?
   A: Yes, I have recently began to building upon this project, with plans to expand the scale of the database and add AI features.
2. Will the UI be improved?
   A: Yes, right now, the application is tailored for personal uses. I aim to turn this into a public full stack application.

## Contributing
If you'd like to contribute to the project, follow these steps:
1. Fork the repository.
2. Create a new branch.
3. Commit your changes.
4. Open a pull request.

## Technologies Used
<p style="display: flex; justify-content: center; align-items: center; gap:"20px">
  <img src = "https://static-00.iconduck.com/assets.00/java-icon-1511x2048-6ikx8301.png" alt="Java" width="50" height="50">
  <img src = "https://raw.githubusercontent.com/kmajhi/java-swing/main/java%20swing.png" alt = "Java Swing" width ="50" height = "50">
  <img src = "https://avatars.githubusercontent.com/u/874086?s=280&v=4" alt ="JUnit" width="50" height = "50">
  <img src = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/JSON_vector_logo.svg/1024px-JSON_vector_logo.svg.png" alt = "JSON" width = "50" height = "50">
</p>

## In Progress
- Connecting a database using SpringBoot
- Create a front end using React
- Set up user authentication using jwt
- more to come...

## License
This project is under the MIT License - see the [LICENSE](https://opensource.org/license/MIT) file for details.
