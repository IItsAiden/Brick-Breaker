package test.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {

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
    private int velocity_x;
    private int velocity_y;
    private Character lastkey = null;

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle paddle;

    @FXML
    private Label label, onscreen_score, onscreen_health;

    @FXML
    private Button Next_level;

    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private Boolean wPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timer.start();
        key_input_Setup();
        ball = new Ball(circle);
        GenerateBrick();
        paddle.setLayoutX(320 - paddle.getWidth()/2);
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
            for (int j = 0; j<10;j++){
                Rectangle rectangle = new Rectangle((j*64),k,63,29);
                rectangle.setFill(color);
                scene.getChildren().add(rectangle);
                bricks.add(rectangle);
            }
            k+=30;
        }
    }

    public boolean CheckBrickCollision(Rectangle brick) {
        if (circle.getBoundsInParent().intersects(brick.getBoundsInParent())){
            boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());

            if (rightBorder || leftBorder) {
                ball.reverse_x();
            }
            if (bottomBorder || topBorder) {
                ball.reverse_y();
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
            onscreen_health.setText(String.valueOf(ball_count));

            ball.checkCollisionScene();
            ball.checkCollisionPaddle(paddle);
            if (ball.checkCollisionBottomZone()) {
                ball.moving(false);
                wPressed = false;
                ball_count--;
                paddle.setLayoutX(320 - (paddle.getWidth()/2));
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
        }
    };

    public void NextLevel(ActionEvent event) {
        if (blitz_mode) {
            if (paddle.getWidth() > 90) {
                paddle.setWidth(paddle.getWidth() - 20);
            }
        }
        ball.moving(false);
        circle.setLayoutX(320);
        circle.setLayoutY(693);//180 for debugging. release is 693
        paddle.setLayoutX(320 - paddle.getWidth()/2);
        bricks.clear();
        level++;
        GenerateBrick();
        label.setText("Press W to start");
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

            if(e.getCode() == KeyCode.W) {
                wPressed = true;
                ball.get_ball_movement(choice);
                label.setVisible(false);
            }

            if(e.getCode() == KeyCode.Q) {
                if (lastkey == null || lastkey != 'Q'){
                    lastkey = 'Q';
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

            if(e.getCode() == KeyCode.Q) {
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
        } else {
            timer.start();
            label.setVisible(false);
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
                paddle.setWidth(390);
                multiplier = 25;
                paddle.setLayoutX(320 - paddle.getWidth()/2);
                break;
            case 2:
                paddle.setWidth(240);
                multiplier = 50;
                paddle.setLayoutX(320 - paddle.getWidth()/2);
                break;
            case 3:
                paddle.setWidth(180);
                multiplier = 100;
                paddle.setLayoutX(320 - paddle.getWidth()/2);
                break;
            case 4:
                paddle.setWidth(150);
                multiplier = 150;
                paddle.setLayoutX(320 - paddle.getWidth()/2);
                break;
            case 5:
                paddle.setWidth(120);
                multiplier = 200;
                paddle.setLayoutX(320 - paddle.getWidth()/2);
                break;
        }
    }
}