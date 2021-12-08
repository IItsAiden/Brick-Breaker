package test.brickbreaker;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private final Circle ball;
    public Double velocity_x;
    public double velocity_y;

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
        boolean rightBorder = ball.getLayoutX() >= (600 - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (0 + ball.getRadius());
        boolean bottomBorder = ball.getLayoutY() >= (450 - ball.getRadius());//200 for debugging.Release now is 720. final will remove
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
            boolean topBorder = ball.getLayoutY() <= (paddle.getLayoutY() + ball.getRadius());


            if (rightBorder && topBorder) {
                if (velocity_x > 0) {
                    reverse_y();
                } else if (velocity_y > 0) {
                    reverse_x();
                } else {
                    reverse_x();
                    reverse_y();
                }
            } else if (leftBorder && topBorder) {
                if (velocity_x < 0) {
                    reverse_y();
                } else if (velocity_y > 0) {
                    reverse_x();
                } else {
                    reverse_x();
                    reverse_y();
                }
            } else {
                if (rightBorder || leftBorder) {
                    reverse_x();
                }
                if (topBorder) {
                    reverse_y();
                }
            }
        }
    }

    public Boolean checkCollisionBottomZone(){
        if(ball.getLayoutY() >= (450 - ball.getRadius())){
            ball.setLayoutX(300);
            ball.setLayoutY(419);//180 for debug. 690
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

    public void get_ball_movement(Double x, Double y) {
        velocity_x = x;
        velocity_y = y;
    }
}