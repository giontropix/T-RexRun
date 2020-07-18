import java.util.ArrayList;

public class Trex extends Thread {

    private boolean isJump = false;
    private final FieldOfObstacle fieldOfObstacle = new FieldOfObstacle();
    private final ArrayList<Coordinate> trex = new ArrayList<>();

    public Trex() {
        printTrex();
    }

    public void printTrex(){
        for (int i = this.fieldOfObstacle.getFieldHeight() - 2; i > 5; i--) {
            this.trex.add(new Coordinate(i, 5));
            this.trex.add(new Coordinate(i, 6));
        }
    }

    @Override
    public void run(){
        try {
             do {
                 this.lookForHeadBetweenClouds();
                 this.lookForFeetInAir();
                 if(this.isJump)
                     this.jump();
                 else
                     this.setGravity();
                 Thread.sleep(1000);
            } while (this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Coordinate> getTrex() {
        return trex;
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean lookForHeadBetweenClouds() {
        return this.trex.contains(new Coordinate(3, 5));
    }

    public void jump(){
        if(!lookForHeadBetweenClouds()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() - 1);
            }
        }
        else this.isJump = false;
    }

    private boolean lookForFeetInAir() {
        return !this.trex.contains(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 1, 5));
    }

    private void setGravity() {
        if (lookForFeetInAir()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() + 1);
            }
        }
    }
}