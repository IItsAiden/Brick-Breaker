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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller for Final Score scene
 */
public class FinalScoreController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    private int score;
    private boolean save = false;

    @FXML
    private Button button;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label label, invalid, nice, username;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        get_leaderboard();
        System.out.println(list);
        listView.getItems().addAll(list);
    }

    /**
     * Get score and if there is a new record
     * @param score player score
     * @param save true if there is a new record
     */
    void get_score(Integer score, boolean save) {

        this.score = score;
        this.save = save;
        label.setText(score.toString());
        if (save) {
            button.setText("Save");
            nice.setText("Congraz!! New High Score!");
            username.setVisible(true);
            textField.setVisible(true);
        }
    }

    /**
     * Get the data of the leaderboard
     */
    public void get_leaderboard() {
        try {
            File myObj = new File("src/main/resources/Leaderboard.txt");
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

    /**
     * Save the new record if there is one then back to home menu scene. Else back to home menu scene
     *
     * @param event require to get the stage for the scene
     * @throws IOException when the scene can not be loaded
     */
    public void Next(ActionEvent event) throws IOException {
        if (textField.getText() == null || textField.getText().trim().isEmpty()) {
            invalid.setText("Please enter username");
            invalid.setVisible(true);
        } else if (textField.getText().contains(",")) {
            invalid.setText("Username can not contain ,");
            invalid.setVisible(true);
        } else {
            if (save) {
                add_score();
                try {
                    PrintWriter myWriter = new PrintWriter("src/main/resources/Leaderboard.txt");
                    for (String s : list) {
                        myWriter.println(s);
                    }
                    myWriter.close();
                } catch (IOException e) {
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

    /**
     * Add the score to the list before saving
     */
    public void add_score() {
        String username = textField.getText();
        username = username.replaceAll("\\s+","");
        username = username + "," + score;
        int counter = 0;
        boolean added = false;
        for (int i = 0; i < list.size();i ++) {
            counter++;
            String[] array;
            array = list.get(i).split(",");
            if (score >= Integer.parseInt(array[1])) {
                list.add(i, username);
                added = true;
                break;
            }
        }
        if (counter < 10 && !added) {
            list.add(username);
        }
    }
}
