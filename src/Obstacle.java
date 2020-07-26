import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Obstacle extends Thread {
    private final int fieldHeight = 8;
    private final int fieldWidth = 25;
    private boolean isInGame = true;
    private int score = 0;
    private int distance = 0;
    private final Vector<Coordinate> cactus = new Vector<>();
    private final Vector<Coordinate> bird = new Vector<>();
    private final Vector<Coordinate> ground = new Vector<>();

    public Obstacle(){
        this.generateGround();
    }

    @Override
    public void run() {
        try {
            while (this.isInGame) {
                this.generateObstacle();
                this.moveObstacle();
                this.score++;
                distance++;
                this.deleteOutOfViewObstacle();
                Thread.sleep (this.speedUpGame());
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Vector<Coordinate> getCactus() {
        return cactus;
    }

    public Vector<Coordinate> getBird() {
        return bird;
    }

    public Vector<Coordinate> getGround() {
        return ground;
    }

    public int getFieldHeight() {
        return this.fieldHeight;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isInGame() {
        return this.isInGame;
    }

    public void setInGame(boolean inGame) {
        this.isInGame = inGame;
    }

    private void generateGround(){
        for (int i = 1; i < this.fieldWidth - 1; i++) {
            this.ground.add(new Coordinate(this.fieldHeight, i));
        }
    }

    private void generateObstacle(){
        if (this.distance % 5 == 0) {
            double random = Math.random();
            if (random <= 0.25) { // probability to add a big cactus
                this.cactus.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
            } else if (random > 0.25 && random <= 0.4) { //probability to add a little cactus
                this.cactus.add(new Coordinate(this.fieldHeight - 1, this.fieldWidth - 1));
                this.cactus.add(new Coordinate(this.fieldHeight - 2, this.fieldWidth - 1));
            } else if (random > 0.4 && random <= 0.5) { //probability to add a bird
                int randomHeight = ThreadLocalRandom.current().nextInt(this.fieldHeight - 5, this.fieldHeight - 1);
                this.bird.add(new Coordinate(randomHeight, this.fieldWidth - 1));
            }
        }
    }

    private void moveObstacle(){
        for (Coordinate coordinate : this.cactus) {
            coordinate.setY(coordinate.getY() - 1);
        }
        for (Coordinate coordinate : this.bird) {
            coordinate.setY(coordinate.getY() - 1);
        }
    }

    private void deleteOutOfViewObstacle() { //TO AVOID THE POSSIBILITY TO FILL THE COLLECTION WITH HUNDREDS OF OBSTACLES
        if(this.cactus.size() > 0 && this.cactus.get(0).getY() < 0)
            this.cactus.remove(0);
        if(this.bird.size() > 0 && this.bird.get(0).getY() < 0)
            this.bird.remove(0);
        if(this.ground.size() > 0 && this.ground.get(0).getY() < 0)
            this.ground.remove(0);
    }

    public long speedUpGame() {
        if (this.score < 200)
            return 400;
        if (this.score < 400)
            return 200;
        if (this.score < 600)
            return 100;
        else return 50;
    }
}