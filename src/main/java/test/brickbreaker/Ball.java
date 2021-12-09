package test.brickbreaker;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The Ball class
 */
public class Ball {
    private final Circle ball;
    public double velocity_x;
    public double velocity_y;

    public Ball(Circle ball) {
        this.ball = ball;
    }

    /**
     * Control the ball moving left and right
     */
    public void reverse_x() {
        velocity_x *= -1;
    }

    /**
     * Control the ball moving up and down
     */
    public void reverse_y() {
        velocity_y *= -1;
    }

    /**
     * Maintain the ball inside the scene
     */
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

    /**
     * Make the ball can bounce off the paddle
     *
     * @param paddle the paddle
     */
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

    /**
     * Check if the ball touch the bottom zone
     *
     * @return true if touched; false otherwise
     */
    public boolean checkCollisionBottomZone(){
        if(ball.getLayoutY() >= (450 - ball.getRadius())){
            ball.setLayoutX(300);
            ball.setLayoutY(419);//180 for debug. 690
            return true;
        }
        return false;
    }

    /**
     * make the ball move
     * @param move move if true. no move otherwise.
     */
    public void moving(boolean move) {
        if (move){
            ball.setLayoutX(ball.getLayoutX() + velocity_x);
            ball.setLayoutY(ball.getLayoutY() - velocity_y);
        }
    }

    /**
     * Get the ball movement
     *
     * @param x left and right
     * @param y up and down
     */
    public void get_ball_movement(double x, double y) {
        velocity_x = x;
        velocity_y = y;
    }
}