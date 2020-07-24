import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;

public class MainGUI extends Application {
    GUIManager game = new GUIManager();
    private StackPane pane;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("T-Rex Run!");
        createContent(primaryStage);
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    private void createBackground(){
        Image img = new Image(new File(createFilePath("\\img\\background.jpg")).getAbsoluteFile().toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
    }

    private void createContent(Stage stage){
        pane = new StackPane();
        Button button = new Button("START GAME");
        button.setAlignment(Pos.BASELINE_CENTER);
        button.setOnAction(e->{
            try {
                game.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        pane.getChildren().add(button);
        createBackground();
        stage.setScene(new Scene(pane, 25*30, 8*35));
        stage.centerOnScreen();
        stage.show();
    }
}