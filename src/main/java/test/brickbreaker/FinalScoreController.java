package test.brickbreaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FinalScoreController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    private double score;
    private boolean save = false;

    @FXML
    private Button button;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label label;

    @FXML
    private Label nice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Leaderboard();
        System.out.println(list);
        listView.getItems().addAll(list);
    }

    void get_score(Double score, Boolean save) {

        this.score = score;
        this.save = save;
        label.setText(score.toString());
        if (save) {

            button.setText("Save");
            nice.setText("Congraz!! New High Score!");
        }
    }

    public void Leaderboard() {
        try {
            File myObj = new File("Leaderboard.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                Scanner myReader = new Scanner(myObj);
                System.out.println("Leaderboard:");
                while (myReader.hasNext()) {
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

        if (save) {

            try {
                PrintWriter myWriter = new PrintWriter(new FileWriter("Leaderboard.txt", true));
                myWriter.println(score);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader(HomeMenu.class.getResource("HomeMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}
