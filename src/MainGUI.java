import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainGUI extends Application {
    private AnchorPane pane;
    Group root = new Group();
    Canvas canvas = new Canvas(500, 500);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    String name = "Steve Jobs";
    PrinterLevel game = new PrinterLevel(this.name);
    ScoreManager score = new ScoreManager();
    Vector<Rectangle> fieldOfPalms = new Vector<>();
    Vector<Circle> birds = new Vector<>();
    Vector<Rectangle> grounds = new Vector<>();
    Rectangle trex = new Rectangle(60, 60);
    int heightMultiple = 35;
    int widthMultiple = 30;
    private final double GAME_HEIGHT = game.getFieldOfObstacle().getFieldHeight() * heightMultiple;
    private final double GAME_WIDTH = game.getFieldOfObstacle().getFieldWidth() * widthMultiple;
    private final String BIRD_PATH = "\\img\\bird.png";
    private final String CACTUS_PATH = "\\img\\cactus.png";
    private final String GROUND_PATH = "\\img\\Ground.png";

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

    private VBox menu(){
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
    }

    private void createTrex(){
        trex.setX(game.getTrex().gettRex().getY() * this.heightMultiple);
        trex.setY(game.getTrex().gettRex().getX() * this.widthMultiple);
        String TREX_PATH = "\\img\\Dino-stand.png";
        File file = new File(createFilePath(TREX_PATH));
        javafx.scene.image.Image img = new Image(file.getAbsoluteFile().toURI().toString());
        trex.setFill(new ImagePattern(img));
        root.getChildren().add(trex);
    }

    public void animateTrex(){
        trex.setX(game.getTrex().gettRex().getY() * heightMultiple);
        trex.setY(game.getTrex().gettRex().getX() * widthMultiple - 25);
    }

    private void createCactus(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getPalm().size(); i++) {

            Rectangle palm = new Rectangle(30, 30);
            palm.setX(game.getFieldOfObstacle().getPalm().get(i).getX() * this.heightMultiple);
            palm.setY(game.getFieldOfObstacle().getPalm().get(i).getY() * this.widthMultiple);
            File fileCactus = new File(createFilePath(CACTUS_PATH));
            javafx.scene.image.Image imgCactus = new Image(fileCactus.getAbsoluteFile().toURI().toString());
            palm.setFill(new ImagePattern(imgCactus));
            fieldOfPalms.add(palm);
        }
        for (Rectangle palm : fieldOfPalms) {
            root.getChildren().addAll(palm);
        }
    }

    public void animateCactus(){
        if (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
            while (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
                Rectangle newPalm = new Rectangle(30, 30);
                File fileCactus = new File(createFilePath(CACTUS_PATH));
                javafx.scene.image.Image imgCactus = new Image(fileCactus.getAbsoluteFile().toURI().toString());
                newPalm.setFill(new ImagePattern(imgCactus));
                fieldOfPalms.add(newPalm);
                root.getChildren().add(newPalm);
            }
        }
        for (int i = 0; i < game.getFieldOfObstacle().getPalm().size(); i++) {
            fieldOfPalms.get(i).setX(game.getFieldOfObstacle().getPalm().get(i).getY() * heightMultiple);
            fieldOfPalms.get(i).setY(game.getFieldOfObstacle().getPalm().get(i).getX() * widthMultiple);
        }
    }

    private void createBirds(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getBird().size(); i++) {
            Circle bird = new Circle(20);
            bird.setCenterX(game.getFieldOfObstacle().getBird().get(i).getX() * this.heightMultiple);
            bird.setCenterY(game.getFieldOfObstacle().getBird().get(i).getY() * this.widthMultiple);
            File fileBird = new File(createFilePath(BIRD_PATH));
            javafx.scene.image.Image imgBird = new Image(fileBird.getAbsoluteFile().toURI().toString());
            bird.setFill(new ImagePattern(imgBird));
            birds.add(bird);
        }
        for (Circle bird : birds) {
            root.getChildren().addAll(bird);
        }
    }

    public void animateBird(){
        if(birds.size() < game.getFieldOfObstacle().getBird().size()) {
            Circle newBird = new Circle(20);
            File fileBird = new File(createFilePath(BIRD_PATH));
            javafx.scene.image.Image imgBird = new Image(fileBird.getAbsoluteFile().toURI().toString());
            newBird.setFill(new ImagePattern(imgBird));
            birds.add(newBird);
            root.getChildren().add(newBird);
        }
        for (int i = 0; i < game.getFieldOfObstacle().getBird().size(); i++) {
            birds.get(i).setCenterX(game.getFieldOfObstacle().getBird().get(i).getY() * heightMultiple);
            birds.get(i).setCenterY(game.getFieldOfObstacle().getBird().get(i).getX() * widthMultiple);
        }
    }

    private void createGround(){
        for (int i = 0; i < this.game.getFieldOfObstacle().getGround().size(); i++) {
            Rectangle ground = new Rectangle(1200, 12);
            ground.setX(game.getFieldOfObstacle().getGround().get(i).getX() * this.heightMultiple);
            ground.setY(game.getFieldOfObstacle().getGround().get(i).getY() * this.widthMultiple);
            File fileGround = new File(createFilePath(GROUND_PATH));
            javafx.scene.image.Image imgBird = new Image(fileGround.getAbsoluteFile().toURI().toString());
            ground.setFill(new ImagePattern(imgBird));
            grounds.add(ground);
        }
        for (Rectangle ground : grounds) {
            root.getChildren().addAll(ground);
        }
    }

    public void animateGroud(){
        if(grounds.size() < game.getFieldOfObstacle().getGround().size()) {
            Rectangle newGround = new Rectangle(1200, 12);
            File fileGround = new File(createFilePath(GROUND_PATH));
            javafx.scene.image.Image imgBird = new Image(fileGround.getAbsoluteFile().toURI().toString());
            newGround.setFill(new ImagePattern(imgBird));
            grounds.add(newGround);
            root.getChildren().add(newGround);
        }
        for (int i = 0; i < game.getFieldOfObstacle().getGround().size(); i++) {
            grounds.get(i).setX(game.getFieldOfObstacle().getGround().get(i).getY() * heightMultiple);
            grounds.get(i).setY(game.getFieldOfObstacle().getGround().get(i).getX() * widthMultiple);
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

    private void deleteOutOfViewObstacle(){
        if(fieldOfPalms.size() > game.getFieldOfObstacle().getPalm().size())
            fieldOfPalms.remove(0);
        if (birds.size() > game.getFieldOfObstacle().getBird().size())
            birds.remove(0);
        if (grounds.size() > game.getFieldOfObstacle().getGround().size())
            grounds.remove(0);
    }

    private void createContent(Stage stage) {
        game.start();
        this.pane = new AnchorPane();
        this.pane.getChildren().addAll(root);
        root.getChildren().add(canvas);
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        root.getChildren().addAll(keyboardNode);
        root.getChildren().add(menu());
        createBackground();
        createTrex();
        createCactus();
        createBirds();
        createGround();


        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, 512,512);
                if(game.getFieldOfObstacle().isInGame()) {
                    gc.setFont(Font.font(20));
                    gc.fillText("PLAYER NAME: ", 20, 50);
                    gc.strokeText("PLAYER NAME: ", 20, 50);
                    gc.fillText("SCORE: " + game.getFieldOfObstacle().getScore(), 20, 70);
                    gc.strokeText("SCORE: " + game.getFieldOfObstacle().getScore(), 20, 70);
                    animateTrex();
                    animateCactus();
                    animateBird();
                    animateGroud();
                    deleteOutOfViewObstacle();
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    gc.fillText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    gc.strokeText("GAME OVER", GAME_WIDTH/2 - 58, GAME_HEIGHT/2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scene scene = new Scene(pane, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}