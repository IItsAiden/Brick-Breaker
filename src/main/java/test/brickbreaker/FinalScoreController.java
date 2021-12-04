package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FinalScoreController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    private int score;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label label;

    void get_score(Integer score) {
        this.score = score;
        label.setText(score.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Leaderboard();
        System.out.println(list);
        listView.getItems().addAll(list);
    }

    public void Leaderboard() {
        try {
            File myObj = new File("Leaderboard.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                Scanner myReader = new Scanner(myObj);
                System.out.println("Leaderboard:");
                while (myReader.hasNextLine()) {
                    list.add(myReader.next());
                }
                myReader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void Next(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
