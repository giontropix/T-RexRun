import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class MainGUI extends Application {
    GUIManager game;
    private GridPane pane;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("T-Rex Run!");
        createContent(primaryStage);
    }

    private String createFilePath(){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + "\\img\\background.jpg";
    }

    private void createBackground(){
        Image img = new Image(new File(createFilePath()).getAbsoluteFile().toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
    }

    private void createContent(Stage stage){
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        //ADDING FORM
        Text scenetitle = new Text("Welcome to T-Rex Run!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(scenetitle, 0, 10, 1, 5);
        Label userName = new Label("PLAYER NAME:");
        userName.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 15));
        pane.add(userName, 0, 0);
        TextField playerNameTextField = new TextField();
        pane.add(playerNameTextField, 0, 1);
        //ADDING BUTTON
        Button button = new Button("START GAME");
        button.setAlignment(Pos.BASELINE_CENTER);
        pane.add(button, 0, 6);
        final Text actiontarget = new Text();
        pane.add(actiontarget, 1, 6);
        button.setOnAction(e->{
            if (playerNameTextField.getCharacters().length() == 0) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("INSERT NAME");
            } else {
                try {
                    String name = playerNameTextField.getText().toUpperCase();
                    game = new GUIManager(name);
                    game.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        createBackground();
        //STARTING SCENE
        stage.setScene(new Scene(pane, 25*30, 8*35));
        stage.centerOnScreen();
        stage.show();
    }
}