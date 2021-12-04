package test.brickbreaker;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private final Circle ball;
    private int velocity_x = 4;//3 for debugging.release is 4
    private int velocity_y = 3;//5 for debugging.release is 3

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
        boolean rightBorder = ball.getLayoutX() >= (1280 - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (0 + ball.getRadius());
        boolean bottomBorder = ball.getLayoutY() >= (720 - ball.getRadius());//200 for debugging.Release is 720
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
            ball.setLayoutX(640);
            ball.setLayoutY(686);//175 for debugging.Release is 686
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
}