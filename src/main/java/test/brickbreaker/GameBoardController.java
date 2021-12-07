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
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    //test

    private Ball ball;
    private ArrayList<Rectangle> bricks = new ArrayList<>();
    private boolean paused = false;
    private int level = 1;//3 for debugging. release is 1
    private int ball_count = 3;
    private int score = 0;
    private boolean blitz_mode = false;
    private int choice;
    private int multiplier = 1;
    private int wcount = 0;
    private int velocity;
    private int velocity_y;
    private Character lastkey = null;
    private Character playkey = null;
    private boolean already = false;

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle paddle;

    @FXML
    private Label label, onscreen_score;

    @FXML
    private Button Next_level, leave;

    @FXML
    private ImageView heart1, heart2;

    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private Boolean wPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timer.start();
        key_input_Setup();
        ball = new Ball(circle);
        GenerateBrick();
        paddle.setLayoutX(300 - paddle.getWidth()/2);
    }

    public void GenerateBrick() {

        int k = 0;
        Color color = Color.LIMEGREEN;//Red for debugging.release is limegreen
        if (level == 1) {
            color = Color.RED;
        } else if (level == 2) {
            color = Color.ORANGE;
        } else if (level == 3) {
            color = Color.LIMEGREEN;//Red for debugging.release is limegreen
        }
        for (int i = 0; i<3;i++){//1 for debugging. Release is 3
            if (i%2 != 0) {
                Rectangle rectangle = new Rectangle(0,k,30,20);
                rectangle.setFill(color);
                rectangle.setStroke(Color.BLACK);
                scene.getChildren().add(rectangle);
                bricks.add(rectangle);
                for (int j = 0; j<10;j++){
                    Rectangle rectangle2 = new Rectangle((30+(j*60)),k,60,20);
                    rectangle2.setFill(color);
                    rectangle2.setStroke(Color.BLACK);
                    scene.getChildren().add(rectangle2);
                    bricks.add(rectangle2);
                }
            } else {
                for (int j = 0; j<10;j++){
                    Rectangle rectangle = new Rectangle((j*60),k,60,20);
                    rectangle.setFill(color);
                    rectangle.setStroke(Color.BLACK);
                    scene.getChildren().add(rectangle);
                    bricks.add(rectangle);
                }
            }
            k+=20;

        }
    }

    public boolean CheckBrickCollision(Rectangle brick) {
        if (circle.getBoundsInParent().intersects(brick.getBoundsInParent()) && !already){
            already = true;
            boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());

            if (leftBorder && bottomBorder) {
                if (ball.velocity_x < 0) {
                    ball.reverse_y();
                    System.out.println("down");
                } else if (ball.velocity_y < 0) {
                    ball.reverse_x();
                    System.out.println("left");
                } else {
                    ball.reverse_x();
                    ball.reverse_y();
                    System.out.println("opposite");
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
                    System.out.println("down");
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
                    score = score + multiplier;
                    System.out.println("Score:" + score);
                    break;
                case 2:
                    brick.setFill(Color.RED);
                    score = score + multiplier;
                    System.out.println("Score:" + score);
                    break;
                case 1:
                    scene.getChildren().remove(brick);
                    score = score + multiplier;
                    System.out.println("Score:" + score);
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
                wPressed = false;
                playkey = null;
                ball_count--;
                if (ball_count == 2) {
                    heart1.setVisible(false);
                }
                if (ball_count == 1) {
                    heart2.setVisible(false);
                }
                paddle.setLayoutX(300 - (paddle.getWidth()/2));
                System.out.println("health: " + ball_count);
                if (ball_count == 0) {
                    GameOver();
                }
            }

            int paddle_movement = 6;
            if(aPressed.get() && wPressed){
                if(paddle.getLayoutX() > 0) {
                    paddle.setLayoutX(paddle.getLayoutX() - paddle_movement);
                }
            }

            if(dPressed.get() && wPressed){
                if(paddle.getLayoutX() < (640 - paddle.getWidth())) {
                    paddle.setLayoutX(paddle.getLayoutX() + paddle_movement);
                }
            }

            if(wPressed){
                ball.moving(true);
            }

            if (!bricks.isEmpty()){
                bricks.removeIf(brick -> CheckBrickCollision(brick));
            } else {
                ball.moving(false);
                wPressed = false;
                label.setText("Level Cleared!");
                label.setVisible(true);
                Next_level.setVisible(true);
                if(level >= 3) {
                    blitz_mode = true;
                }
            }
            already = false;
        }
    };

    public void NextLevel(ActionEvent event) {
        //check extra level
        if (blitz_mode) {
            if (paddle.getWidth() > 60) {
                paddle.setWidth(paddle.getWidth() - 30);
            }
            //more level
            if (paddle.getWidth() <= 60) {
                ball.velocity_x++;
                ball.velocity_y++;
            }
        }
        ball.moving(false);
        circle.setLayoutX(300);
        circle.setLayoutY(419);//180 for debugging. release is 690
        paddle.setLayoutX(300 - paddle.getWidth()/2);
        bricks.clear();
        level++;
        playkey = null;
        GenerateBrick();
        label.setText("Press S to start");
        Next_level.setVisible(false);
    }

    public void key_input_Setup(){
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }

            if(e.getCode() == KeyCode.S) {
                if (playkey == null || playkey != 'S'){
                    playkey = 'S';
                    wPressed = true;
                    ball.get_ball_movement(velocity);
                    label.setVisible(false);
                }
            }

            if(e.getCode() == KeyCode.W) {
                if (lastkey == null || lastkey != 'W'){
                    lastkey = 'W';
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
                lastkey = null;
            }

            if(e.getCode() == KeyCode.W) {
            }
        });
    }

    public void paused() {

        paused = !paused;
        if(paused) {
            timer.stop();
            label.setVisible(true);
            label.setText("Game Paused");
            System.out.println("Game paused");
            leave.setVisible(true);
        } else {
            timer.start();
            if (wPressed) {
                label.setVisible(false);
            }
            leave.setVisible(false);
            label.setText(" Press S to start");
            System.out.println("Game resume");
        }
    }

    public void GameOver() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("GameOver.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.scene.getScene().getWindow();
            GameOverController controller = fxmlLoader.getController();
            controller.high_score(score);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int  getBrickState(Rectangle brick) {
        int HP = 3;
        if(brick.getFill() == Color.LIMEGREEN) HP = 3;
        else if (brick.getFill() == Color.ORANGE) HP = 2;
        else if (brick.getFill() == Color.RED) HP = 1;
        return HP;
    }

    public void get_choice(Integer choice) {
        this.choice = choice;
        switch (choice) {
            case 1:
                paddle.setWidth(210);
                multiplier = 25;
                velocity = 1;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 2:
                paddle.setWidth(150);
                multiplier = 50;
                velocity = 2;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 3:
                paddle.setWidth(120);
                multiplier = 100;
                velocity = 2;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 4:
                paddle.setWidth(120);
                multiplier = 150;
                velocity = 3;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
            case 5:
                paddle.setWidth(90);
                multiplier = 200;
                velocity = 3;
                paddle.setLayoutX(300 - paddle.getWidth()/2);
                break;
        }
    }

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
}