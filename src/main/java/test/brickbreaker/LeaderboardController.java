package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller for leaderboard scene
 */
public class LeaderboardController implements Initializable {


    ArrayList<String> list = new ArrayList<String>();


    @FXML
    private ListView<String> listView;

    /**
     * Fill up the list view with data acquire from get_leaderboard()
     *
     * @param url require for the method to initialize
     * @param resourceBundle require for the method to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        get_leaderboard();
        listView.getItems().addAll(list);
    }

    /**
     * Return to the game menu
     *
     * @param event require to get the stage for the scene
     * @throws IOException when the scene can not be loaded
     */
    public void back(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Get the data of the leaderboard
     */
    public void get_leaderboard() {
        try {
            File myObj = new File("src/main/resources/Leaderboard.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                PrintWriter myWriter = new PrintWriter(new FileWriter("src/main/resources/Leaderboard.txt", true));
                myWriter.println("Admin,0");
                myWriter.close();
            }
            int counter = 0;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext() && counter < 10) {
                list.add(myReader.next());
                counter++;
            }
            myReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
