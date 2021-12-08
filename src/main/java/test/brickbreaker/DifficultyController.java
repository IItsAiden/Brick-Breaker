package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class DifficultyController {

    private int choice = 3;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4, rb5;

    @FXML
    private Label Description, paddle, ball, multiplier;

    public void get_difficulty() {

        if (rb1.isSelected()){
            Description.setText(rb1.getText());
            paddle.setText("Very long paddle");
            ball.setText("Very slow ball speed");
            multiplier.setText("25");
            choice = 1;
        }
        if (rb2.isSelected()){
            Description.setText(rb2.getText());
            paddle.setText("Long paddle");
            ball.setText("Slow ball speed");
            multiplier.setText("50");
            choice = 2;
        }
        if (rb3.isSelected()){
            Description.setText(rb3.getText());
            paddle.setText("Normal paddle");
            ball.setText("Normal ball speed");
            multiplier.setText("100");
            choice = 3;
        }
        if (rb4.isSelected()){
            Description.setText(rb4.getText());
            paddle.setText("Short paddle");
            ball.setText("Fast ball speed");
            multiplier.setText("150");
            choice = 4;
        }
        if (rb5.isSelected()){
            Description.setText(rb5.getText());
            paddle.setText("Very short paddle");
            ball.setText("Very fast ball speed");
            multiplier.setText("200");
            choice = 5;
        }
    }

    public void next(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("Tutorial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TutorialController controller = fxmlLoader.getController();
        controller.get_choice(choice);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
