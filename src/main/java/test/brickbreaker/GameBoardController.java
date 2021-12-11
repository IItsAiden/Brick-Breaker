package test.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Game scene
 */
public class GameBoardController implements Initializable {

    private final Brick brick = new Brick(this);
    private final ArrayList<Rectangle> bricks = new ArrayList<>();

    private Ball ball;
    private int ball_count = 3;
    private double velocity_x;
    private double velocity_y;

    private int score = 0;
    private int multiplier;
    private int paddle_speed;
    private Character start_key = null;

    private int level = 1;
    private boolean collided = false;
    private boolean blitz_mode = false;
    private boolean paused = false;
    private Character pause_key = null;

    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private boolean start_pressed = false;

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle paddle;

    @FXML
    private Label description, onscreen_score;

    @FXML
    private Button nextLevel, leave;

    @FXML
    private ImageView heart1, heart2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timer.start();
        keyInputSetup();
        brick.GenerateBrick();
        ball = new Ball(circle);
        paddle.setLayoutX(300 - paddle.getWidth()/2);
    }

    /**
     * Check if the ball touch a brick
     *
     * @param brick the brick
     * @return true if touched. false otherwise
     */
    public boolean checkBrickCollision(Rectangle brick) {

        if (circle.getBoundsInParent().intersects(brick.getBoundsInParent()) && !collided){

            collided = true;
            boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());

            if (leftBorder && bottomBorder) {
                if (ball.velocity_x < 0) {
                    ball.reverse_y();
                } else if (ball.velocity_y < 0) {
                    ball.reverse_x();
                } else {
                    ball.reverse_x();
                    ball.reverse_y();
                }
            } else if (rightBorder && topBorder) {
                if (ball.velocity_x > 0) {
                    ball.reverse_y();
                } else if (ball.velocity_y > 0) {
                    ball.reverse_x();
                } else {
                    ball.reverse_x();
                    ball.reverse_y();
                }
            } else if (leftBorder && topBorder) {
                if (ball.velocity_x < 0) {
                    ball.reverse_y();
                } else if (ball.velocity_y > 0) {
                    ball.reverse_x();
                } else {
                    ball.reverse_x();
                    ball.reverse_y();
                }
            } else if (rightBorder && bottomBorder) {
                if (ball.velocity_x > 0) {
                    ball.reverse_y();
                } else if (ball.velocity_y < 0) {
                    ball.reverse_x();
                } else {
                    ball.reverse_x();
                    ball.reverse_y();
                }
            } else {
                if (rightBorder || leftBorder) {
                    ball.reverse_x();
                }
                if (bottomBorder || topBorder) {
                    ball.reverse_y();
                }
            }

            switch (getBrickState(brick)) {
                case 3:
                    brick.setFill(Color.ORANGE);
                    score += multiplier;
                    break;
                case 2:
                    brick.setFill(Color.RED);
                    score += multiplier;
                    break;
                case 1:
                    scene.getChildren().remove(brick);
                    score += multiplier;
                    return true;
            }
        }
        return false;
    }

    AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long timestamp) {

            onscreen_score.setText(String.valueOf(score));

            ball.checkCollisionScene();
            ball.checkCollisionPaddle(paddle);
            if (ball.checkCollisionBottomZone()) {

                ball.moving(false);
                ball_count--;
                paddle.setLayoutX(300 - (paddle.getWidth()/2));
                start_pressed = false;
                start_key = null;
                if (ball_count == 2) {
                    heart1.setVisible(false);
                }
                if (ball_count == 1) {
                    heart2.setVisible(false);
                }
                if (ball_count == 0) {
                    gameOver();
                }
            }

            if(aPressed.get() && start_pressed){
                if(paddle.getLayoutX() > 0) {
                    paddle.setLayoutX(paddle.getLayoutX() - paddle_speed);
                }
            }

            if(dPressed.get() && start_pressed){
                if(paddle.getLayoutX() < (600 - paddle.getWidth())) {
                    paddle.setLayoutX(paddle.getLayoutX() + paddle_speed);
                }
            }

            if(start_pressed){
                ball.moving(true);
            }

            if (!bricks.isEmpty()){
                bricks.removeIf(brick -> checkBrickCollision(brick));
            } else {
                ball.moving(false);
                start_pressed = false;
                description.setText("Level Cleared!");
                description.setVisible(true);
                nextLevel.setVisible(true);
                if(level >= 3) {
                    blitz_mode = true;
                }
            }
            collided = false;
        }
    };

    /**
     * Showed when player clear a level
     */
    public void nextLevel() {

        if (blitz_mode) {
            if (paddle.getWidth() > 60) {
                paddle.setWidth(paddle.getWidth() - 30);
                if (paddle.getWidth() == 120) {
                    paddle_speed = 4;
                    multiplier = 150;
                    velocity_x = 2;
                    velocity_y = 3;
                }
                if (paddle.getWidth() == 90) {
                    paddle_speed = 5;
                    multiplier = 200;
                    velocity_x = 3;
                    velocity_y = 4;
                }
            } else if (paddle.getWidth() <= 60) {
                paddle_speed++;
                ball.velocity_x++;
                ball.velocity_y++;
            }
        }

        ball.moving(false);
        circle.setLayoutX(300);
        circle.setLayoutY(419);
        paddle.setLayoutX(300 - paddle.getWidth()/2);
        brick.GenerateBrick();
        level++;
        start_key = null;
        description.setText("Press S to start");
        nextLevel.setVisible(false);
    }

    /**
     * Get the key input of player
     */
    public void keyInputSetup(){
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }

            if(e.getCode() == KeyCode.S) {
                if (start_key == null || start_key != 'S'){
                    start_key = 'S';
                    start_pressed = true;
                    ball.get_ball_movement(velocity_x, velocity_y);
                    description.setVisible(false);
                }
            }

            if(e.getCode() == KeyCode.W) {
                if (pause_key == null || pause_key != 'W'){
                    pause_key = 'W';
                    paused();
                }
            }
        });

        scene.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }

            if(e.getCode() == KeyCode.W) {
                pause_key = null;
            }
        });
    }

    /**
     * Pause and resume game
     */
    public void paused() {
        paused = !paused;
        if(paused) {
            timer.stop();
            description.setVisible(true);
            description.setText("Game Paused");
            leave.setVisible(true);
        } else {
            timer.start();
            if (start_pressed) {
                description.setVisible(false);
            }
            leave.setVisible(false);
            description.setText(" Press S to start");
        }
    }

    /**
     * Game over
     */
    public void gameOver() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("GameOver.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.scene.getScene().getWindow();
            GameOverController controller = fxmlLoader.getController();
            controller.get_score(score);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the status of the brick
     *
     * @param brick the brick
     * @return number of hits left for the brick to break
     */
    public int  getBrickState(Rectangle brick) {
        int HP = 3;
        if (brick.getFill() == Color.ORANGE) HP = 2;
        else if (brick.getFill() == Color.RED) HP = 1;
        return HP;
    }

    /**
     * Get the user choice
     *
     * @param choice player choice
     */
    public void getChoice(Integer choice) {
        switch (choice) {
            case 1:
                paddle.setWidth(150);
                paddle_speed = 2;
                multiplier = 25;
                velocity_x = 0.5;
                velocity_y = 1.0;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 2:
                paddle.setWidth(150);
                paddle_speed = 3;
                multiplier = 50;
                velocity_x = 1;
                velocity_y = 2;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 3:
                paddle.setWidth(120);
                paddle_speed = 3;
                multiplier = 100;
                velocity_x = 1;
                velocity_y = 2;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 4:
                paddle.setWidth(120);
                paddle_speed = 4;
                multiplier = 150;
                velocity_x = 2;
                velocity_y = 3;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 5:
                paddle.setWidth(90);
                paddle_speed = 5;
                multiplier = 200;
                velocity_x = 3;
                velocity_y = 4;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
        }
    }

    /**
     * Leave the game
     *
     * @param event require to get the stage for the scene
     */
    public void leave(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Leave");
        alert.setHeaderText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the level
     *
     * @return player current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the brick
     *
     * @return the brick
     */
    public ArrayList<Rectangle> getBricks() {
        return bricks;
    }

    /**
     * Get the scene
     *
     * @return the scene
     */
    public AnchorPane getScene() {
        return scene;
    }
}