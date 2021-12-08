# Brick Destroy
This is a simple arcade video game. Player's goal is to destroy a wall with a small ball.

More information about the [Brick Breaker](https://heroconcept.com/a-brief-history-of-brick-breaker-video-games/) game and its history.

---

## Key Changes

### Code Maintenance

- The code has been converted into Javafx.

- Portion of GUI related code has been converted to fxml.

- The different brick type classes have been merge into brick.java for easier and simpler maintenance.

- Fix the ball movement speed being random for every different game. The ball now has a set speed. The ball speed that is set to random in the ball.java have been adjusted to make the ball have a fix speed.

- Adjust the steel brick from being probability to a fix number of 3 hits. Reason being there is no clear indication of how many hits it takes to break which lead to player thinking the game is bug and the brick is unbreakable.

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
