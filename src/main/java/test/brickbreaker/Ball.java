package test.brickbreaker;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private final Circle ball;
    private int velocity_x = 3;//4
    private int velocity_y = 5;//3

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
        boolean bottomBorder = ball.getLayoutY() >= (720 - ball.getRadius());//set to 200 for debugging.Release version is 720
        boolean topBorder = ball.getLayoutY() <= (0 + ball.getRadius());

        if (rightBorder || leftBorder) {
            reverse_x();
        }
        if (bottomBorder || topBorder) {
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
            ball.setLayoutY(175);//set to 175 for debugging.Release version is 686
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