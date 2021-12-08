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

public class FinalScoreController implements Initializable {

    ArrayList<String> list = new ArrayList<String>();
    private int score;
    private boolean save = false;

    @FXML
    private Button button;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label label, invalid, nice;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Leaderboard();
        System.out.println(list);
        listView.getItems().addAll(list);
    }

    void get_score(Integer score, Boolean save) {

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
                System.out.println("Final:" + list);
                break;
            }
        }
        if (counter < 10 && !added) {
            list.add(username);
        }
    }
}
