package test.brickbreaker;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private final Circle ball;
    private int velocity_x;
    private int velocity_y;

    public Ball(Circle ball) {
        this.ball = ball;
    }

    public void reverse_x() {
        velocity_x *= -1;
    }

    public void reverse_y() {
        velocity_y *= -1;
    }

    public void checkCollisionScene(){
        boolean rightBorder = ball.getLayoutX() >= (640 - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (0 + ball.getRadius());
        boolean bottomBorder = ball.getLayoutY() >= (720 - ball.getRadius());//200 for debugging.Release now is 720. final will remove
        boolean topBorder = ball.getLayoutY() <= (0 + ball.getRadius());

        if (rightBorder || leftBorder) {
            reverse_x();
        }
        if (bottomBorder || topBorder) {//bottomBorder for debugging. Release will be remove
            reverse_y();
        }
    }

    public void checkCollisionPaddle(Rectangle paddle){

        if(ball.getBoundsInParent().intersects(paddle.getBoundsInParent())){

            boolean rightBorder = ball.getLayoutX() >= ((paddle.getLayoutX() + paddle.getWidth()) - ball.getRadius());
            boolean leftBorder = ball.getLayoutX() <= (paddle.getLayoutX() + ball.getRadius());
            boolean bottomBorder = ball.getLayoutY() >= ((paddle.getLayoutY() + paddle.getHeight()) - ball.getRadius());
            boolean topBorder = ball.getLayoutY() <= (paddle.getLayoutY() + ball.getRadius());

            if (rightBorder || leftBorder) {
                reverse_x();
            }
            if (bottomBorder || topBorder) {
                reverse_y();
            }
        }
    }

    public Boolean checkCollisionBottomZone(){
        if(ball.getLayoutY() >= (720 - ball.getRadius())){
            ball.setLayoutX(320);
            ball.setLayoutY(693);
            return true;
        }
        return false;
    }

    public void moving(Boolean move) {
        if (move){
            ball.setLayoutX(ball.getLayoutX() + velocity_x);
            ball.setLayoutY(ball.getLayoutY() - velocity_y);
        }
    }

    public void get_ball_movement(Integer velocity) {
        velocity_x = velocity + 1;
        velocity_y = velocity;
    }
}