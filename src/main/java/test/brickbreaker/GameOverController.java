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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GameOverController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> top_ten = new ArrayList<String>();
    private int score;

    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Leaderboard();
    }

    void high_score(Integer score) {
        this.score = score;
        label.setText(score.toString());
    }

    public void Next(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("FinalScore.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FinalScoreController controller = fxmlLoader.getController();
        controller.get_score(score, check_new_highscore());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public Boolean check_new_highscore() {
        int counter = 0;//patch fix unable to save when leaderboard have less than 10 highscore
        for (String s : list) {
            counter++;
            String[] array;
            array = s.split(",");
            if (score >= Integer.parseInt(array[1])) {
                return true;
            }
        }
        return counter < 10;//patch fix unable to save when leaderboard have less than 10 highscore
    }

    public void Leaderboard() {
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
