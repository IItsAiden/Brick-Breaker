package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for tutorial scene
 */
public class TutorialController {

    private int choice;

    /**
     * Get the player choice
     *
     * @param choice get the player choice
     */
    void get_choice(Integer choice) {
        this.choice = choice;
    }

    /**
     * Go to the game board scene
     *
     * @param event require to get the stage for the scene
     * @throws IOException when the scene can not be loaded
     */
    public void Start(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("GameBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GameBoardController controller = fxmlLoader.getController();
        controller.getChoice(choice);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
