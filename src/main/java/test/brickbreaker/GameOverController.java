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

    public void Home(ActionEvent event) throws IOException {

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
        for (String s : list) {
            System.out.println(s);
            if (score >= Integer.parseInt(s)) {
                return true;
            }
        }
        return false;
    }

    public void Leaderboard() {
        try {
            File myObj = new File("Leaderboard.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                PrintWriter myWriter = new PrintWriter(new FileWriter("Leaderboard.txt", true));
                myWriter.println(0);
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Patch can be use here
    //Problem with this leaderboard is it will read all list.
    //Our leaderboard must be top 10 only.
    //path to be fix is limit it to 10
    //so that player only get to save if their score is in top 10.

}
