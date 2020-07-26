import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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

public class GUIManager extends Application {
    private Group root = new Group();
    private String playerName;
    private PrinterLevel printerLevel = new PrinterLevel(this.playerName);
    private ScoreManager score = new ScoreManager();
    private final int heightMultiple = 35;
    private final int widthMultiple = 30;
    private final double GAME_HEIGHT = printerLevel.getFieldOfObstacle().getFieldHeight() * heightMultiple;
    private final double GAME_WIDTH = printerLevel.getFieldOfObstacle().getFieldWidth() * widthMultiple;
    private Canvas canvas = new Canvas(printerLevel.getFieldOfObstacle().getFieldWidth() * widthMultiple, printerLevel.getFieldOfObstacle().getFieldHeight() * heightMultiple);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private ParallelTransition parallelTransitionBackground;
    private ParallelTransition parallelTransitionGround;
    private final Image img = new Image(new File(createFilePath("\\img\\wide_background.jpg")).getAbsoluteFile().toURI().toString());
    private final Image imgLittleSingleCactus = new Image(new File(createFilePath("\\img\\cactus_single_little.png")).getAbsoluteFile().toURI().toString(), 30, 40, false, false);
    private final Image imgBird = new Image(new File(createFilePath("\\img\\bird.png")).getAbsoluteFile().toURI().toString(), 30, 30, false, false);
    private final Image imgGround = new Image(new File(createFilePath("\\img\\Ground.png")).getAbsoluteFile().toURI().toString(), 1200, 12, false, false);
    private final Image imgTrex = new Image(new File(createFilePath("\\img\\Dino-stand.png")).getAbsoluteFile().toURI().toString(), 40, 40, false, false);
    private final Media roar = new Media(new File(createFilePath("\\sound\\trex_roar.mp3")).toURI().toString());
    private final MediaPlayer mediaPlayerRoar = new MediaPlayer(roar);
    private final Media backgroundAudio = new Media(new File(createFilePath("\\sound\\Welcome_to_Jurassic_Park_background.mp3")).toURI().toString());
    private final MediaPlayer mediaPlayerBackground = new MediaPlayer(backgroundAudio);

    public GUIManager(String name){
        this.playerName = name;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run! Player Name: " + this.playerName);
        reset();
        this.score.load();
        createContent(stage);
    }

    public double getGAME_HEIGHT() {
        return GAME_HEIGHT;
    }

    public double getGAME_WIDTH() {
        return GAME_WIDTH;
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    private void reset(){
        this.root = new Group();
        this.printerLevel = new PrinterLevel(this.playerName);
        this.score = new ScoreManager();
        this.canvas = new Canvas(this.printerLevel.getFieldOfObstacle().getFieldWidth() * this.widthMultiple, this.printerLevel.getFieldOfObstacle().getFieldHeight() * this.heightMultiple);
        this.gc = canvas.getGraphicsContext2D();
        this.mediaPlayerBackground.stop();
        this.mediaPlayerRoar.stop();
    }

    private VBox menu(Stage stage){
        final Menu menuOptions = new Menu("Options");
        MenuItem subMenuNewGame = new MenuItem("New Game");
        subMenuNewGame.setOnAction(e -> {
            stage.close();
            this.reset();
            MainGUI gui = new MainGUI();
            gui.start(stage);
            //this.start(stage);
                });
        MenuItem subMenuRanking = new MenuItem("Ranking");
        subMenuRanking.setOnAction(e -> {
            TilePane secondaryLayout = new TilePane();
            int ranking = 1;
            for (Score listOfScore : this.score.getListOfScore()) {
                Label secondLabel = new Label(ranking++ + "° Player, Name: " + listOfScore.getPlayerName().toUpperCase() +
                        ", Total Score: " + listOfScore.getTotalScore() + "\n");
                secondLabel.setAlignment(Pos.TOP_LEFT);
                secondaryLayout.getChildren().add(secondLabel);
            }
            ScrollPane scrollPane = new ScrollPane(secondaryLayout);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        menuOptions.getItems().add(subMenuNewGame);
        menuOptions.getItems().add(subMenuRanking);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuOptions);
        return new VBox(menuBar);
    }

    private void inizializeGround() {
        ImageView ground = new ImageView(this.imgGround);
        ImageView ground1 = new ImageView(this.imgGround);
        ground1.setX(1200);
        this.root.getChildren().addAll(ground, ground1);
        //FIST PICTURE TO SLIDE
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(this.printerLevel.getFieldOfObstacle().speedUpGame() * 50), ground);
        translateTransition.setFromX(0);
        translateTransition.setFromY(this.printerLevel.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition.setToX(-1200);
        translateTransition.setToY(this.printerLevel.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        //SECOND PICTURE TO SLIDE
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(this.printerLevel.getFieldOfObstacle().speedUpGame() * 50), ground1);
        translateTransition2.setFromX(0);
        translateTransition2.setFromY(this.printerLevel.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition2.setToX(-1200);
        translateTransition2.setToY(this.printerLevel.getFieldOfObstacle().getGround().get(0).getX() * this.widthMultiple);
        translateTransition2.setInterpolator(Interpolator.LINEAR);
        //ADD BOTH PICTURES TO TRANSITION
        this.parallelTransitionGround = new ParallelTransition(translateTransition, translateTransition2);
        this.parallelTransitionGround.setCycleCount(Animation.INDEFINITE);
    }

    private void initializeBackground() {
        ImageView background = new ImageView(this.img);
        ImageView background2 = new ImageView(this.img);
        background2.setX(3181);
        this.root.getChildren().addAll(background, background2);
        //FIST PICTURE TO SLIDE
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200000), background);
        translateTransition.setFromX(0);
        translateTransition.setToX(-3181);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        //SECOND PICTURE TO SLIDE
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(200000), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-3181);
        translateTransition2.setInterpolator(Interpolator.LINEAR);
        //ADD BOTH PICTURES TO TRANSITION
        this.parallelTransitionBackground = new ParallelTransition(translateTransition, translateTransition2);
        this.parallelTransitionBackground.setCycleCount(Animation.INDEFINITE);
    }

    private void handle(KeyEvent arg0) {
        if (this.printerLevel.getFieldOfObstacle().isInGame() && printerLevel.getTrex().lookForFeetOnTheGround()) {
            this.printerLevel.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }

    private void createContent(Stage stage) {
        //INITIALIZE GAME FROM PRINTERLEVEL
        this.printerLevel.start();
        //INITIALIZE BACKGROUND
        initializeBackground();
        //INITIALIZE GROUND
        inizializeGround();
        //PLACE WHERE COLLECT ALL THE ELEMENT OF GUI
        AnchorPane pane = new AnchorPane();
        //ADDING TO ROOT 2D GRAPHIC MANAGER
        this.root.getChildren().add(this.canvas);
        //KEYBOARD INPUT CATCH
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        this.root.getChildren().addAll(keyboardNode);
        //ADDING TO PANE ALL THE ELEMENTS OF ROOT TO BE ABLE TO SHOW THEM
        pane.getChildren().addAll(this.root);
        //BORDERPANE FOR MENU ON TOP
        BorderPane borderPane = new BorderPane(pane);
        borderPane.setTop(menu(stage));
        //CREATING THE SCENE THAT WILL BE SHOWED
        Scene scene = new Scene(borderPane, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        //ALLOCATING AND STARTING ALL AUDIO FILES
        this.mediaPlayerBackground.setOnEndOfMedia(() -> this.mediaPlayerBackground.seek(Duration.ZERO));
        this.mediaPlayerBackground.play();
        this.parallelTransitionBackground.play();
        this.parallelTransitionGround.play();
        //MANAGING ANIMATION
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                //CANVAS NEED TO BE CLEARED TO SHOW US NO GRAPHIC ARTIFACTS
                gc.clearRect(0, 0, printerLevel.getFieldOfObstacle().getFieldWidth() * widthMultiple, printerLevel.getFieldOfObstacle().getFieldHeight() * heightMultiple);
                gc.setFont(Font.font(20));
                //ADDING SCORE ON THE TOP-RIGHT
                gc.fillText("SCORE: " + printerLevel.getFieldOfObstacle().getScore(), 620, 30);
                gc.strokeText("SCORE: " + printerLevel.getFieldOfObstacle().getScore(), 620, 30);
                //MANAGING ALL THE ELEMENTS INTO THE GAME SESSION
                if(printerLevel.getFieldOfObstacle().isInGame()) {
                    if (printerLevel.getTrex().isJump())
                        mediaPlayerRoar.play();
                    if(printerLevel.getTrex().lookForFeetOnTheGround())
                        mediaPlayerRoar.stop();
                    if (printerLevel.getFieldOfObstacle().speedUpGame() == 200 && printerLevel.getFieldOfObstacle().getScore() < 230){
                        gc.fillText("SPEED UP!", 620, 50);
                        gc.strokeText("SPEED UP!", 620, 50);
                    }
                    if (printerLevel.getFieldOfObstacle().speedUpGame() == 100 && printerLevel.getFieldOfObstacle().getScore() < 440){
                        gc.fillText("MAX SPEED!", 620, 50);
                        gc.strokeText("MAX SPEED!", 620, 50);
                    }
                    //ANIMATING TREX
                    gc.drawImage(imgTrex, printerLevel.getTrex().gettRex().getY() * heightMultiple, printerLevel.getTrex().gettRex().getX() * widthMultiple - 5);
                    //ANIMATING CACTUS
                    for (int i = 0; i < printerLevel.getFieldOfObstacle().getCactus().size(); i++) {
                        if (printerLevel.getFieldOfObstacle().getCactus().contains(new Coordinate(printerLevel.getFieldOfObstacle().getCactus().get(i).getX(), printerLevel.getFieldOfObstacle().getCactus().get(i).getY()))
                        && printerLevel.getFieldOfObstacle().getCactus().contains(new Coordinate(printerLevel.getFieldOfObstacle().getCactus().get(i).getX() - 1, printerLevel.getFieldOfObstacle().getCactus().get(i).getY()))){
                            gc.drawImage(imgLittleSingleCactus, printerLevel.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, printerLevel.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                        }
                        else {
                            gc.drawImage(imgLittleSingleCactus, printerLevel.getFieldOfObstacle().getCactus().get(i).getY() * heightMultiple, printerLevel.getFieldOfObstacle().getCactus().get(i).getX() * widthMultiple - 5);
                        }
                    }
                    //ANIMATING BIRD
                    for (int i = 0; i < printerLevel.getFieldOfObstacle().getBird().size(); i++) {
                        gc.drawImage(imgBird, printerLevel.getFieldOfObstacle().getBird().get(i).getY() * heightMultiple, printerLevel.getFieldOfObstacle().getBird().get(i).getX() * widthMultiple);
                    }
                }
                else {
                    //WHAT TO DO IF GAME OVER
                    gc.fillText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    gc.strokeText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    mediaPlayerBackground.stop();
                    score.getListOfScore().add(new Score(playerName.toUpperCase(), printerLevel.getFieldOfObstacle().getScore()));
                    int ranking = 1;
                    for (Score score : score.getListOfScore()) {
                        ranking++;
                        if (score.equals(new Score(playerName.toUpperCase(), printerLevel.getFieldOfObstacle().getScore())))
                            gc.fillText("RANKING: " + ranking, 620, 50);
                    }

                    gc.strokeText("RANKING: ", 620, 50);
                    score.store();
                }
            }
        }.start();
        stage.show();
    }
}