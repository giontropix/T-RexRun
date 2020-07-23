import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

    //Vector<Rectangle> fieldOfPalms = new Vector<>();
    //Vector<Circle> birds = new Vector<>();
    //Vector<Rectangle> grounds = new Vector<>();

    private final double GAME_HEIGHT = game.getFieldOfObstacle().getFieldHeight() * heightMultiple;
    private final double GAME_WIDTH = game.getFieldOfObstacle().getFieldWidth() * widthMultiple;
    Image imgCactus = new Image(new File(createFilePath("\\img\\cactus.png")).getAbsoluteFile().toURI().toString(), 40, 40, false, false);
    Image imgBird = new Image(new File(createFilePath("\\img\\bird.png")).getAbsoluteFile().toURI().toString(), 40, 40, false, false);
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

    /*private void createTrex(){
        this.trex = new Rectangle(40, 40);
        this.trex.setX(this.game.getTrex().gettRex().getY() * this.heightMultiple);
        this.trex.setY(this.game.getTrex().gettRex().getX() * this.widthMultiple);
        this.trex.setFill(new ImagePattern(imgTrex));
        this.root.getChildren().add(this.trex);
    }*/

    public void animateTrex(){
        //trex.setX(game.getTrex().gettRex().getY() * heightMultiple);
        //trex.setY(game.getTrex().gettRex().getX() * widthMultiple - 5);
        gc.drawImage(imgTrex, game.getTrex().gettRex().getY() * heightMultiple, game.getTrex().gettRex().getX() * widthMultiple - 5);
    }

    /*private void createCactus(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getPalm().size(); i++) {
            Rectangle palm = new Rectangle(30, 30);
            palm.setX(game.getFieldOfObstacle().getPalm().get(i).getX() * this.heightMultiple);
            palm.setY(game.getFieldOfObstacle().getPalm().get(i).getY() * this.widthMultiple);
            palm.setFill(new ImagePattern(imgCactus));
            fieldOfPalms.add(palm);
            root.getChildren().add(palm);
        }
    }*/

    public void animateCactus(){
        /*if (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
            while (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
                Rectangle newPalm = new Rectangle(40, 30);
                newPalm.setFill(new ImagePattern(imgCactus));
                fieldOfPalms.add(newPalm);
                root.getChildren().add(newPalm);
            }
        }*/
        for (int i = 0; i < game.getFieldOfObstacle().getPalm().size(); i++) {
            //fieldOfPalms.get(i).setX(game.getFieldOfObstacle().getPalm().get(i).getY() * heightMultiple);
            //fieldOfPalms.get(i).setY(game.getFieldOfObstacle().getPalm().get(i).getX() * widthMultiple);
            gc.drawImage(imgCactus, game.getFieldOfObstacle().getPalm().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getPalm().get(i).getX() * widthMultiple);
        }
    }

    /*private void createBirds(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getBird().size(); i++) {
            Circle bird = new Circle(20);
            bird.setCenterX(game.getFieldOfObstacle().getBird().get(i).getX() * this.heightMultiple);
            bird.setCenterY(game.getFieldOfObstacle().getBird().get(i).getY() * this.widthMultiple);
            bird.setFill(new ImagePattern(imgBird));
            birds.add(bird);
            root.getChildren().add(bird);
        }
    }*/

    public void animateBird(){
        /*if(birds.size() < game.getFieldOfObstacle().getBird().size()) {
            Circle newBird = new Circle(20);
            newBird.setFill(new ImagePattern(imgBird));
            birds.add(newBird);
            root.getChildren().add(newBird);
        }*/
        for (int i = 0; i < game.getFieldOfObstacle().getBird().size(); i++) {
            //birds.get(i).setCenterX(game.getFieldOfObstacle().getBird().get(i).getY() * heightMultiple);
            //birds.get(i).setCenterY(game.getFieldOfObstacle().getBird().get(i).getX() * widthMultiple);
            gc.drawImage(imgBird, game.getFieldOfObstacle().getBird().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getBird().get(i).getX() * widthMultiple);
        }
    }

    /*private void createGround(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getGround().size(); i++) {
            Rectangle ground = new Rectangle(1200, 12);
            ground.setX(game.getFieldOfObstacle().getGround().get(i).getX() * this.heightMultiple);
            ground.setY(game.getFieldOfObstacle().getGround().get(i).getY() * this.widthMultiple);
            ground.setFill(new ImagePattern(imgGround));
            grounds.add(ground);
            root.getChildren().add(ground);
        }
    }*/

    public void animateGroud(){
        /*if(grounds.size() < game.getFieldOfObstacle().getGround().size()) {
            Rectangle newGround = new Rectangle(20, 12);
            newGround.setFill(new ImagePattern(imgBird));
            grounds.add(newGround);
            root.getChildren().add(newGround);
        }*/
        for (int i = 0; i < game.getFieldOfObstacle().getGround().size(); i++) {
            //grounds.get(i).setX(game.getFieldOfObstacle().getGround().get(i).getY() * heightMultiple);
            //grounds.get(i).setY(game.getFieldOfObstacle().getGround().get(i).getX() * widthMultiple);
            gc.drawImage(imgGround, game.getFieldOfObstacle().getGround().get(i).getY() * heightMultiple, game.getFieldOfObstacle().getGround().get(i).getX() * widthMultiple);
        }
    }

    public void handle(KeyEvent arg0) {
        if (game.getFieldOfObstacle().isInGame() && game.getTrex().lookForFeetOnTheGround()) {
            game.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }

    private void createBackground(){
        String BG_PATH = "\\img\\background.jpg";
        File file = new File(createFilePath(BG_PATH));
        javafx.scene.image.Image img = new Image(file.getAbsoluteFile().toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
    }

    /*private void deleteOutOfViewObstacle(){
        if(fieldOfPalms.size() > game.getFieldOfObstacle().getPalm().size())
            fieldOfPalms.remove(0);
        if (birds.size() > game.getFieldOfObstacle().getBird().size())
            birds.remove(0);
        if (grounds.size() > game.getFieldOfObstacle().getGround().size())
            grounds.remove(0);
    }*/

    private void createContent(Stage stage) {
        game.start();
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
        createBackground();
        //createTrex();
        //createCactus();
        //createBirds();
        //createGround();
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
                if(game.getFieldOfObstacle().isInGame()) {
                    if (game.getTrex().isJump())
                        mediaPlayerRoar.play();
                    else mediaPlayerRoar.stop();
                    gc.setFont(Font.font(20));
                    //gc.fillText("PLAYER NAME: ", 450, 50);
                    //gc.strokeText("PLAYER NAME: ", 450, 50);
                    gc.fillText("SCORE: " + game.getFieldOfObstacle().getScore(), 600, 30);
                    gc.strokeText("SCORE: " + game.getFieldOfObstacle().getScore(), 600, 30);
                    animateTrex();
                    animateCactus();
                    animateBird();
                    animateGroud();
                    //deleteOutOfViewObstacle();
                    /*try {
                        TimeUnit.MILLISECONDS.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
                else {
                    gc.fillText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    gc.strokeText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    mediaPlayerBackground.stop();
                    /*try {
                        TimeUnit.MILLISECONDS.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }.start();
        stage.show();
    }
}