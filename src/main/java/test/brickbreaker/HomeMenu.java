package test.brickbreaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main
 */
public class HomeMenu extends Application {

    /**
     * Start the game
     *
     * @param stage get the stage
     * @throws IOException when the scene can not be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("Block.png");
        stage.getIcons().add(icon);
        stage.setTitle("Brick Breaker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}