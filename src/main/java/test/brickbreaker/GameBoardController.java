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
    //today goal
    //ball count
    //extra
    //check game feature note

    private Ball ball;
    private ArrayList<Rectangle> bricks = new ArrayList<>();
    private boolean paused = false;
    private int level = 1;
    private int ball_count = 3;

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle paddle;

    @FXML
    private Label label;

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
    }

    public void GenerateBrick() {

        int k = 0;
        Color color = Color.LIMEGREEN;
        if (level == 1) {
            color = Color.RED;
        } else if (level == 2) {
            color = Color.ORANGE;
        } else if (level == 3) {
            color = Color.LIMEGREEN;
        }
        for (int i = 0; i<1;i++){//change to 1 for debug. Release version will be 3.
            for (int j = 0; j<10;j++){
                Rectangle rectangle = new Rectangle((j*128),k,128,30);
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
                    break;
                case 2:
                    brick.setFill(Color.RED);
                    break;
                case 1:
                    scene.getChildren().remove(brick);
                    return true;
            }
        }
        return false;
    }

    AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long timestamp) {

            ball.checkCollisionScene();
            ball.checkCollisionPaddle(paddle);
            if (ball.checkCollisionBottomZone()) {
                ball.moving(false);
                wPressed = false;
                ball_count--;
                paddle.setLayoutX(535);
                System.out.println(ball_count);
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
                if(paddle.getLayoutX() < 1080) {
                    paddle.setLayoutX(paddle.getLayoutX() + paddle_movement);
                }
            }

            if(wPressed){
                ball.moving(true);
            }

            if (!bricks.isEmpty()){
                bricks.removeIf(brick -> CheckBrickCollision(brick));
            } else {
                System.out.println("No more brick");
                timer.stop();
                label.setVisible(true);
                Next_level.setVisible(true);
                if(level == 3) {
                    GameOver();
                }
            }

        }
    };

    public void NextLevel(ActionEvent event) {
        paused();
        circle.setLayoutX(640);
        circle.setLayoutY(180);
        System.out.println("Level Clear");
        bricks.clear();
        level++;
        GenerateBrick();
        label.setVisible(false);
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
            }

            if(e.getCode() == KeyCode.Q) {
                paused();
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
            }
        });
    }

    public void paused() {

        paused = !paused;
        if(paused) {
            timer.stop();
            System.out.println("Game paused");
        } else {
            timer.start();
            System.out.println("Game resume");
        }
    }

    public void GameOver() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("GameOver.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.scene.getScene().getWindow();
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
}