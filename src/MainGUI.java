import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class MainGUI extends Application {
    private AnchorPane pane;
    Group root = new Group();
    String name = "Steve Jobs";
    PrinterLevel game = new PrinterLevel(this.name);
    ScoreManager score = new ScoreManager();
    int heightMultiple = 35;
    int widthMultiple = 30;
    Canvas canvas = new Canvas(game.getFieldOfObstacle().getFieldWidth() * widthMultiple, game.getFieldOfObstacle().getFieldHeight() * heightMultiple);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    private ParallelTransition parallelTransition;

    private final double GAME_HEIGHT = game.getFieldOfObstacle().getFieldHeight() * heightMultiple;
    private final double GAME_WIDTH = game.getFieldOfObstacle().getFieldWidth() * widthMultiple;
    Image img = new Image(new File(createFilePath("\\img\\wide_background.jpg")).getAbsoluteFile().toURI().toString());
    Image imgLittleSingleCactus = new Image(new File(createFilePath("\\img\\cactus_single_little.png")).getAbsoluteFile().toURI().toString(), 30, 40, false, false);
    Image imgLittleCoupleCactus = new Image(new File(createFilePath("\\img\\cactus_couple_little.png")).getAbsoluteFile().toURI().toString(), 30, 40, false, false);
    Image imgBird = new Image(new File(createFilePath("\\img\\bird.png")).getAbsoluteFile().toURI().toString(), 30, 35, false, false);
    Image imgGround = new Image(new File(createFilePath("\\img\\Ground.png")).getAbsoluteFile().toURI().toString(), 1200, 12, false, false);
    Image imgTrex = new Image(new File(createFilePath("\\img\\Dino-stand.png")).getAbsoluteFile().toURI().toString(), 40, 40, false, false);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run!");
        score.load();
        createContent(stage);
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    /*private VBox menu(){
        final Menu menuOptions = new Menu("Options");
        MenuItem subMenuNewGame = new MenuItem("New Game");
        MenuItem subMenuRanking = new MenuItem("Ranking");
        subMenuRanking.setOnAction(e -> {
            int ranking = 0;
            for (Score score : score.getListOfScore()) {
                gc.fillText(ranking++ + "° Player, Name: " + score.getPlayerName().toUpperCase() +
                        ", Total Score: " + score.getTotalScore(), 50, 50);
            }
        });
        menuOptions.getItems().add(subMenuNewGame);
        menuOptions.getItems().add(subMenuRanking);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuOptions);
        return new VBox(menuBar);
    }*/

    public void initializeBackground() {
        ImageView background = new ImageView(img);
        ImageView background2 = new ImageView(img);
        ImageView ground = new ImageView(imgGround);
        ImageView ground1 = new ImageView(imgGround);
        background2.setX(3181);
        ground1.setX(1200);
        //ground1.setY(game.getFieldOfObstacle().getGround().get(0).getX() * widthMultiple);
        root.getChildren().addAll(background, background2, ground, ground1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200000), background);
        translateTransition.setFromX(0);
        translateTransition.setToX(-3181);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(200000), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-3181);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition3 = new TranslateTransition(Duration.millis(100000), ground);
        translateTransition3.setFromX(0);
        translateTransition3.setFromY(game.getFieldOfObstacle().getGround().get(0).getX() * widthMultiple);
        translateTransition3.setToX(-1200);
        translateTransition3.setToY(game.getFieldOfObstacle().getGround().get(0).getX() * widthMultiple);
        translateTransition3.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition4 = new TranslateTransition(Duration.millis(100000), ground1);
        translateTransition4.setFromX(0);
        translateTransition4.setFromY(game.getFieldOfObstacle().getGround().get(0).getX() * widthMultiple);
        translateTransition4.setToX(-1200);
        translateTransition4.setToY(game.getFieldOfObstacle().getGround().get(0).getX() * widthMultiple);
        translateTransition4.setInterpolator(Interpolator.LINEAR);

        parallelTransition = new ParallelTransition(translateTransition, translateTransition2, translateTransition3, translateTransition4);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
    }

        public void handle(KeyEvent arg0) {
        if (game.getFieldOfObstacle().isInGame() && game.getTrex().lookForFeetOnTheGround()) {
            game.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }

    private void createContent(Stage stage) {
        game.start();
        initializeBackground();
        this.pane = new AnchorPane();
        this.pane.getChildren().addAll(root);
        root.getChildren().add(canvas);

        Scene scene = new Scene(pane, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        root.getChildren().addAll(keyboardNode);
        //root.getChildren().add(menu());
        Media roar = new Media(new File(createFilePath("\\sound\\trex_roar.mp3")).toURI().toString());
        MediaPlayer mediaPlayerRoar = new MediaPlayer(roar);
        Media backgroundAudio = new Media(new File(createFilePath("\\sound\\Welcome_to_Jurassic_Park_background.mp3")).toURI().toString());
        MediaPlayer mediaPlayerBackground = new MediaPlayer(backgroundAudio);
        mediaPlayerBackground.setOnEndOfMedia(() -> mediaPlayerBackground.seek(Duration.ZERO));
        mediaPlayerBackground.play();


        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, game.getFieldOfObstacle().getFieldWidth() * widthMultiple,game.getFieldOfObstacle().getFieldHeight() * heightMultiple);
                parallelTransition.play();
                if(game.getFieldOfObstacle().isInGame()) {
                    if (game.getTrex().isJump())
                        mediaPlayerRoar.play();
                    else mediaPlayerRoar.stop();
                    gc.setFont(Font.font(20));
                    //gc.fillText("PLAYER NAME: ", 450, 50);
                    //gc.strokeText("PLAYER NAME: ", 450, 50);
                    gc.fillText("SCORE: " + game.getFieldOfObstacle().getScore(), 600, 30);
                    gc.strokeText("SCORE: " + game.getFieldOfObstacle().getScore(), 600, 30);
                    //ANIMATING TREX
                    gc.drawImage(imgTrex, game.getTrex().gettRex().getY() * heightMultiple, game.getTrex().gettRex().getX() * widthMultiple - 5);
                    //ANIMATING CACTUS
                    boolean bigCactus = false;
                    for (int i = 0; i < game.getFieldOfObstacle().getCactus().size(); i++) {
                        if (game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY()))
                        && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX() - 1, game.getFieldOfObstacle().getCactus().get(i).getY()))){
                            gc.drawImage(imgLittleSingleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            bigCactus = true;
                        }
                        else if (game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY()))
                                && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() - 1))
                                && !bigCactus){
                            gc.drawImage(imgLittleCoupleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            bigCactus = false;
                        } else if (i != game.getFieldOfObstacle().getCactus().size() && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() + 1))
                                && game.getFieldOfObstacle().getCactus().contains(new Coordinate(game.getFieldOfObstacle().getCactus().get(i).getX(), game.getFieldOfObstacle().getCactus().get(i).getY() - 1))) {
                            gc.drawImage(imgLittleCoupleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple);
                            bigCactus = false;
                        } else {
                            gc.drawImage(imgLittleSingleCactus, game.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                            bigCactus = false;
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
                }
            }
        }.start();
        stage.show();
    }
}