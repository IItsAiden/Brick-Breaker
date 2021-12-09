package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
 * Controller for Game Over scene
 */
public class GameOverController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    private int score;

    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        get_leaderboard();
    }

    /**
     * Get player score
     *
     * @param score player score
     */
    void get_score(Integer score) {
        this.score = score;
        label.setText(score.toString());
    }

    /**
     * Go to the final scene
     *
     * @param event require to get the stage for the scene
     * @throws IOException when the scene can not be loaded
     */
    public void Next(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("FinalScore.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FinalScoreController controller = fxmlLoader.getController();
        controller.get_score(score, check_new_record());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Check if player make a new record
     *
     * @return true if player make a new record. False otherwise
     */
    public boolean check_new_record() {
        int counter = 0;
        for (String s : list) {
            counter++;
            String[] array;
            array = s.split(",");
            if (score >= Integer.parseInt(array[1])) {
                return true;
            }
        }
        return counter < 10;
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
