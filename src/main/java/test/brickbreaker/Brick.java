package test.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The Brick class
 */
public class Brick {
    private final GameBoardController gameBoardController;

    public Brick(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    /**
     * Generate the brick
     */
    public void GenerateBrick() {

        int height = 0;
        Color primary_color = Color.RED;
        Color secondary_color = Color.RED;

        if (gameBoardController.getLevel() == 2) {
            primary_color = Color.ORANGE;
        } else if (gameBoardController.getLevel() == 3) {
            primary_color = Color.LIMEGREEN;
            secondary_color = Color.ORANGE;
        } else if (gameBoardController.getLevel() > 3) {
            primary_color = Color.LIMEGREEN;
            secondary_color = Color.LIMEGREEN;
        }

        for (int i = 0; i < 3; i++) {

            Rectangle brick;
            int counter;
            if (i % 2 != 0) {

                brick = new Rectangle(0, height, 30, 20);
                counter = 1;
                brick.setFill(secondary_color);
                brick.setStroke(Color.BLACK);
                gameBoardController.getScene().getChildren().add(brick);
                gameBoardController.getBricks().add(brick);

                for (int j = 0; j < 10; j++) {

                    brick = new Rectangle((30 + (j * 60)), height, 60, 20);
                    if (counter == 1 || counter == 2) {
                        brick.setFill(primary_color);
                        counter++;
                    } else {
                        brick.setFill(secondary_color);
                        counter = 1;
                    }
                    brick.setStroke(Color.BLACK);
                    gameBoardController.getScene().getChildren().add(brick);
                    gameBoardController.getBricks().add(brick);
                }
            } else {

                counter = 2;
                for (int j = 0; j < 10; j++) {

                    brick = new Rectangle((j * 60), height, 60, 20);
                    if (counter == 1 || counter == 2) {
                        brick.setFill(secondary_color);
                    } else {
                        brick.setFill(primary_color);
                        counter = 0;
                    }
                    counter++;
                    brick.setStroke(Color.BLACK);
                    gameBoardController.getScene().getChildren().add(brick);
                    gameBoardController.getBricks().add(brick);
                }
            }
            height += 20;
        }
    }
}