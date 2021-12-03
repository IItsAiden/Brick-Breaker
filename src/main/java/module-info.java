module test.brickbreaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens test.brickbreaker to javafx.fxml;
    exports test.brickbreaker;
}