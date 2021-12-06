package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TutorialController {

    private int choice;

    void get_choice(Integer choice) {
        this.choice = choice;
    }

    public void Start(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("GameBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GameBoardController controller = fxmlLoader.getController();
        controller.get_choice(choice);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
