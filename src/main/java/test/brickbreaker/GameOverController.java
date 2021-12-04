package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {

    private int score;

    @FXML
    private Label label;

    void high_score(Integer score) {
        this.score = score;
        label.setText(score.toString());
    }

    public void Home(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("FinalScore.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FinalScoreController controller = fxmlLoader.getController();
        controller.get_score(score);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
