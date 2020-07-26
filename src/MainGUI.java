import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;

public class MainGUI extends Application {
    private GUIManager guiManager = new GUIManager(null);
    private final GridPane pane = new GridPane();
    private final ScoreManager scoreManager = new ScoreManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("T-Rex Run!");
        this.scoreManager.load();
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
        this.pane.setBackground(new Background(bgImg));
    }

    private VBox menu(Stage stage){
        final Menu menuOptions = new Menu("Options");
        MenuItem subMenuRanking = new MenuItem("Ranking");
        subMenuRanking.setOnAction(e -> {
            TilePane secondaryLayout = new TilePane();
            int ranking = 1;
            for (Score listOfScore : this.scoreManager.getListOfScore()) {
                Label secondLabel = new Label(ranking++ + "° Player, Name: " + listOfScore.getPlayerName().toUpperCase() +
                        ", Total Score: " + listOfScore.getTotalScore());
                secondLabel.setAlignment(Pos.TOP_LEFT);
                secondaryLayout.getChildren().add(secondLabel);
            }
            ScrollPane scrollPane = new ScrollPane(secondaryLayout);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setFitToWidth(true);
            Scene secondScene = new Scene(scrollPane, 300, 500);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Second Stage");
            newWindow.setScene(secondScene);
            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);
            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(stage);
            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 200);
            newWindow.setY(stage.getY() + 100);
            newWindow.show();
        });
        menuOptions.getItems().add(subMenuRanking);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuOptions);
        return new VBox(menuBar);
    }

    private void createContent(Stage stage){
        this.pane.setAlignment(Pos.CENTER);
        //ADDING FORM
        Text sceneTitle = new Text("Welcome to T-Rex Run!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.pane.add(sceneTitle, 0, 10, 1, 5);
        Label insertPlayerName = new Label("PLAYER NAME:");
        insertPlayerName.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 15));
        this.pane.add(insertPlayerName, 0, 0);
        TextField playerNameTextField = new TextField();
        this.pane.add(playerNameTextField, 0, 1);
        //ADDING BUTTON
        Button buttonStartGame = new Button("START GAME");
        buttonStartGame.setDefaultButton(true);
        buttonStartGame.setAlignment(Pos.BASELINE_CENTER);
        this.pane.add(buttonStartGame, 0, 6);
        final Text playerNameNotInsertedWarning = new Text();
        this.pane.add(playerNameNotInsertedWarning, 1, 6);
        buttonStartGame.setOnAction(e->{
            if (playerNameTextField.getCharacters().length() == 0) {
                playerNameNotInsertedWarning.setFill(Color.FIREBRICK);
                playerNameNotInsertedWarning.setText("PLEASE INSERT NAME");
            } else {
                try {
                    String playerName = playerNameTextField.getText().toUpperCase();
                    guiManager = new GUIManager(playerName);
                    guiManager.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        createBackground();
        //STARTING SCENE
        BorderPane borderPane = new BorderPane(pane);
        borderPane.setTop(menu(stage));
        stage.setScene(new Scene(borderPane, this.guiManager.getGAME_WIDTH(), this.guiManager.getGAME_HEIGHT()));
        stage.centerOnScreen();
        stage.show();
    }
}