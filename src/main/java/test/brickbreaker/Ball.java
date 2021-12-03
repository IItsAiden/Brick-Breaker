package test.brickbreaker;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private final AnchorPane scene;
    private final Circle ball;
    private final Rectangle paddle;
    private int velocity_x = 5;
    private int velocity_y = 9;

    public Ball(AnchorPane scene, Circle ball, Rectangle paddle) {

        this.scene = scene;
        this.ball = ball;
        this.paddle = paddle;
        start();
    }

    public void move() {

        ball.setLayoutX(ball.getLayoutX() + velocity_x);
        ball.setLayoutY(ball.getLayoutY() + velocity_y);

        boolean rightBorder = ball.getLayoutX() >= (1280 - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (0 + ball.getRadius());
        boolean bottomBorder = ball.getLayoutY() >= (200 - ball.getRadius());
        boolean topBorder = ball.getLayoutY() <= (0 + ball.getRadius());
        boolean paddleBorder = ball.getBoundsInParent().intersects(paddle.getBoundsInParent());

        if (rightBorder || leftBorder) {
            reverse_x();
        }
        if (topBorder || paddleBorder) {
            reverse_y();
        }
        if (bottomBorder) {
            reverse_y();
        }
    }

    public void start() {
        ball.setLayoutX(640);
        ball.setLayoutY(180);
        reverse_y();
//        if (velocity_y > 0) {
//            reverse_y();
//        }
    }

    public void reverse_x() {
        velocity_x *= -1;
    }

    public void reverse_y() {
        velocity_y *= -1;
    }
}