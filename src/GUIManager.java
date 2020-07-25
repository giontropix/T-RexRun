import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class GUIManager extends Application {
    Group root = new Group();
    String name;
    PrinterLevel game = new PrinterLevel(this.name);
    ScoreManager score = new ScoreManager();
    int heightMultiple = 35;
    int widthMultiple = 30;
    Canvas canvas = new Canvas(game.getFieldOfObstacle().getFieldWidth() * widthMultiple, game.getFieldOfObstacle().getFieldHeight() * heightMultiple);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    private ParallelTransition parallelTransitionBackground;
    private ParallelTransition parallelTransitionGround;
    private final double GAME_HEIGHT = game.getFieldOfObstacle().getFieldHeight() * heightMultiple;
    private final double GAME_WIDTH = game.getFieldOfObstacle().getFieldWidth() * widthMultiple;
    Image img = new Image(new File(createFilePath("\\img\\wide_background.jpg")).getAbsoluteFile().toURI().toString());
    Image imgLittleSingleCactus = new Image(new File(createFilePath("\\img\\cactus_single_little.png")).getAbsoluteFile().toURI().toString(), 30, 40, false, false);
    Image imgLittleCoupleCactus = new Image(new File(createFilePath("\\img\\cactus_couple_little.png")).getAbsoluteFile().toURI().toString(), 30, 40, false, false);
    Image imgBird = new Image(new File(createFilePath("\\img\\bird.png")).getAbsoluteFile().toURI().toString(), 30, 30, false, false);
    Image imgGround = new Image(new File(createFilePath("\\img\\Ground.png")).getAbsoluteFile().toURI().toString(), 1200, 12, false, false);
    Image imgTrex = new Image(new File(createFilePath("\\img\\Dino-stand.png")).getAbsoluteFile().toURI().toString(), 40, 40, false, false);
    Media roar = new Media(new File(createFilePath("\\sound\\trex_roar.mp3")).toURI().toString());
    MediaPlayer mediaPlayerRoar = new MediaPlayer(roar);
    Media backgroundAudio = new Media(new File(createFilePath("\\sound\\Welcome_to_Jurassic_Park_background.mp3")).toURI().toString());
    MediaPlayer mediaPlayerBackground = new MediaPlayer(backgroundAudio);

    public GUIManager(String name){
        this.name = name;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run! Player Name: " + this.name);
        reset();
        this.score.load();
        createContent(stage);
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    private void reset(){
        this.root = new Group();
        this.game = new PrinterLevel(this.name);
        this.score = new ScoreManager();
        this.canvas = new Canvas(this.game.getFieldOfObstacle().getFieldWidth() * this.widthMultiple, this.game.getFieldOfObstacle().getFieldHeight() * this.heightMultiple);
        this.gc = canvas.getGraphicsContext2D();
        this.mediaPlayerBackground.stop();
        this.mediaPlayerRoar.stop();
        this.parallelTransitionGround.stop();
        this.parallelTransitionBackground.stop();
    }

    private VBox menu(Stage stage){
        final Menu menuOptions = new Menu("Options");
        MenuItem subMenuNewGame = new MenuItem("New Game");
        subMenuNewGame.setOnAction(e -> {
            stage.close();
            this.reset();
            this.start(stage);
                });
        MenuItem subMenuRanking = new MenuItem("Ranking");
        subMenuRanking.setOnAction(e -> {
            ArrayList<String> test = new ArrayList<>();
            int ranking = 0;
            for (Score listOfScore : this.score.getListOfScore()) {
                test.add(ranking++ + "° Player, Name: " + listOfScore.getPlayerName().toUpperCase() +
                        ", Total Score: " + listOfScore.getTotalScore() + "\n");
            }
           Label secondLabel = new Label();
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            Scene secondScene = new Scene(secondaryLayout, 300, 500);
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
        menuOptions.getItems().add(subMenuNewGame);
        menuOptions.getItems().add(subMenuRanking);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuOptions);
        return new VBox(menuBar);
    }

    public void inizializeGround() {
        ImageView ground = new ImageView(this.imgGround);
        ImageView ground1 = new ImageView(this.imgGround);
        ground1.setX(1200);
        this.root.getChildren().addAll(ground, ground1);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(this.game.getFieldOfObstacle().speedUpGame() * 50), ground);
        translateTransition.setFromX(0);
        translateTransition.setFromY(this.game.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition.setToX(-1200);
        translateTransition.setToY(this.game.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(this.game.getFieldOfObstacle().speedUpGame() * 50), ground1);
        translateTransition2.setFromX(0);
        translateTransition2.setFromY(this.game.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition2.setToX(-1200);
        translateTransition2.setToY(this.game.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        this.parallelTransitionGround = new ParallelTransition(translateTransition, translateTransition2);
        this.parallelTransitionGround.setCycleCount(Animation.INDEFINITE);
    }

    public void initializeBackground() {
        ImageView background = new ImageView(this.img);
        ImageView background2 = new ImageView(this.img);
        background2.setX(3181);
        this.root.getChildren().addAll(background, background2);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200000), background);
        translateTransition.setFromX(0);
        translateTransition.setToX(-3181);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(200000), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-3181);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        this.parallelTransitionBackground = new ParallelTransition(translateTransition, translateTransition2);
        this.parallelTransitionBackground.setCycleCount(Animation.INDEFINITE);
    }

        public void handle(KeyEvent arg0) {
        if (this.game.getFieldOfObstacle().isInGame() && game.getTrex().lookForFeetOnTheGround()) {
            this.game.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }

    private void createContent(Stage stage) {
        this.game.start();
        initializeBackground();
        inizializeGround();
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(this.root);
        this.root.getChildren().add(this.canvas);
        BorderPane borderPane = new BorderPane(pane);
        borderPane.setTop(menu(stage));
        Scene scene = new Scene(borderPane, this.GAME_WIDTH, this.GAME_HEIGHT);
        //Scene scene = new Scene(pane, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        this.root.getChildren().addAll(keyboardNode);
        this.mediaPlayerBackground.setOnEndOfMedia(() -> this.mediaPlayerBackground.seek(Duration.ZERO));
        this.mediaPlayerBackground.play();
        this.parallelTransitionBackground.play();
        this.parallelTransitionGround.play();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, game.getFieldOfObstacle().getFieldWidth() * widthMultiple,game.getFieldOfObstacle().getFieldHeight() * heightMultiple);
                if(game.getFieldOfObstacle().isInGame()) {

                    if (game.getTrex().isJump())
                        mediaPlayerRoar.play();
                    if(game.getTrex().lookForFeetOnTheGround())
                        mediaPlayerRoar.stop();
                    gc.setFont(Font.font(20));
                    //gc.fillText("PLAYER NAME: ", 450, 50);
                    //gc.strokeText("PLAYER NAME: ", 450, 50);
                    gc.fillText("SCORE: " + game.getFieldOfObstacle().getScore(), 620, 30);
                    gc.strokeText("SCORE: " + game.getFieldOfObstacle().getScore(), 620, 30);
                    if (game.getFieldOfObstacle().speedUpGame() == 200 && game.getFieldOfObstacle().getScore() < 230){
                        gc.fillText("SPEED UP!", 620, 50);
                        gc.strokeText("SPEED UP!", 620, 50);
                    }
                    if (game.getFieldOfObstacle().speedUpGame() == 100 && game.getFieldOfObstacle().getScore() < 440){
                        gc.fillText("MAX SPEED!", 620, 50);
                        gc.strokeText("MAX SPEED!", 620, 50);
                    }

                    //ANIMATING TREX
                    gc.drawImage(imgTrex, game.getTrex().gettRex().getY() * heightMultiple, game.getTrex().gettRex().getX() * widthMultiple - 5);
                    //ANIMATING CACTUS
                    //boolean bigCactus = false;
                    for (int i = 0; i < game.getFieldOfObstacle().getCactus().size(); i++) {
                        if (game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY()))
                        && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX() - 1, game.getFieldOfObstacle().getCactus().get(i).getY()))){
                            gc.drawImage(imgLittleSingleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            //bigCactus = true;
                        }
                        /*else if (game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY()))
                                && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() - 1))
                                && !bigCactus){
                            gc.drawImage(imgLittleCoupleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            bigCactus = false;
                        } else if (i != game.getFieldOfObstacle().getCactus().size() && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() + 1))
                                && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() - 1))) {
                            gc.drawImage(imgLittleCoupleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple);
                            bigCactus = false;
                        }*/ else {
                            gc.drawImage(imgLittleSingleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            //bigCactus = false;
                        }

                    }
                    //ANIMATING BIRD
                    for (int i = 0; i < game.getFieldOfObstacle().getBird().size(); i++) {
                        gc.drawImage(imgBird, game.getFieldOfObstacle().getBird().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getBird().get(i).getX() * widthMultiple);
                    }
                }
                else {
                    gc.fillText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    gc.strokeText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    mediaPlayerBackground.stop();
                    score.getListOfScore().add(new Score(name.toUpperCase(), game.getFieldOfObstacle().getScore()));
                    score.store();
                }
            }
        }.start();
        stage.show();
    }
}