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
    private Label label;

    public void get_difficulty(ActionEvent event) {

        if (rb1.isSelected()){
            label.setText(rb1.getText());
            choice = 1;
        }
        if (rb2.isSelected()){
            label.setText(rb2.getText());
            choice = 2;
        }
        if (rb3.isSelected()){
            label.setText(rb3.getText());
            choice = 3;
        }
        if (rb4.isSelected()){
            label.setText(rb4.getText());
            choice = 4;
        }
        if (rb5.isSelected()){
            label.setText(rb5.getText());
            choice = 5;
        }
    }

    public void next(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("Tutorial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TutorialController controller = fxmlLoader.getController();
        controller.get_choice(choice);
        System.out.println(choice);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
