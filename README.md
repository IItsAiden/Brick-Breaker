# Brick Destroy
This is a simple arcade video game. Player's goal is to destroy a wall with a small ball.

More information about the [Brick Breaker](https://heroconcept.com/a-brief-history-of-brick-breaker-video-games/) game and its history.

---

## Key Changes

### Code Maintenance

- The code has been converted into Javafx.

- MVC has been implemented with View being the [name]fxml, Controller being the [name]Controller.java, and Model being [name].java.

- Portion of GUI related code has been converted to fxml.

- The different brick type classes have been merge into brick.java for easier and simpler maintenance.

- Fix the ball movement speed being random for every different game. The ball now has a set speed. The ball speed that is set to random in the ball.java have been adjusted to make the ball have a fix speed.

- Adjust the steel brick from being probability to a fix number of 3 hits. Reason being there is no clear indication of how many hits it takes to break which lead to player thinking the game is bug and the brick is unbreakable.

- Player.java has been merge with GameController.java as the GUI related code has been move to fxml.

---

### Addition Feature

- Added a game over scene that will show player high score after every game. An additional file call game over file is created together with it fxml file.

- Player will now be able to know how many score they have acquire during the gameplay.

- Added a leader board to store player high score. An additional file call leader board file is created together with it fxml file.

- Player will now be able to store their high score and compete with other or break try to break the record.

- Added a simple and informative guide before the game to let player understands the game better. An additional file call tutorial file is created together with it fxml file.

- Player will now be able to understand the control and the game mechanic much easier with an attractive visual guide.

- Added additional level into the game. Player can now enjoy the game more with more content to play. This is implemented in the gameboard file.

- Added a difficulty system where player can select their desire difficulty with different score multiplier. This is implemented in the gameboard file.

- Player can select harder difficulty for more challenging gameplay. With harder difficulty the score they get will also be higher.

---

### Visual Enhancement

- Added a background for the game

- Adjust the brick appearance to make it more distinguishable on how many hits left for the brick to break.

- The brick is now more appealing and easily distinguishable for each brick type.

- Slight increase in the size of ball to make it more trackable by the eyes.

- Visual improvement on the pause game screen. The pause screen is now clearer and the option in the pause screen is much more visible.

---

### Git history
![Screenshot (115)](https://user-images.githubusercontent.com/79147150/145801294-0e425b89-d95f-4550-be9d-f8ac05d92deb.png)
![Screenshot (116)](https://user-images.githubusercontent.com/79147150/145801334-bba5196a-34cd-4af2-9e11-3926ae793e1e.png)
![Screenshot (117)](https://user-images.githubusercontent.com/79147150/145801339-ca1b61d4-bdd5-4faf-90e9-935f413489b2.png)
![Screenshot (118)](https://user-images.githubusercontent.com/79147150/145801355-64f3d12f-e863-4fc7-b995-8c4b4cf72fc4.png)
![Screenshot (119)](https://user-images.githubusercontent.com/79147150/145801422-3614d374-b5cd-4316-88f9-cd0ea5efb352.png)
![Screenshot (120)](https://user-images.githubusercontent.com/79147150/145816316-518a371c-cb28-4555-a8b9-57500d2ad611.png)
![Screenshot (121)](https://user-images.githubusercontent.com/79147150/145816332-cbc029bd-1464-4859-8010-aae744749a06.png)

 The [GitHub repository](https://github.com/IItsAiden/COMP2042_CW_hfyyv1) of this project.
