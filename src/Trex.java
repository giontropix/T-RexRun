import java.util.Vector;

public class Trex extends Thread {

    private boolean isJump = false;
    private final FieldOfObstacle fieldOfObstacle = new FieldOfObstacle();
    private final Vector<Coordinate> trex = new Vector<>();

    public Trex() {
        printTrex();
    }

    public void printTrex(){
        //for (int i = this.fieldOfObstacle.getFieldHeight() - 2; i > 4; i--) {
            //this.trex.add(new Coordinate(i, 5));
            //this.trex.add(new Coordinate(i, 6));
        this.trex.add(new Coordinate(5, 5));
        //}
    }

    @Override
    public void run(){
        try {
             do {
                 if(this.isJump) {
                     this.lookForHeadBetweenClouds();
                     this.jump();
                 }
                 else {
                     this.lookForFeetOnTheGround();
                     this.setGravity();
                 }
                 Thread.sleep(500);
            } while (this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Vector<Coordinate> getTrex() {
        return trex;
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean lookForHeadBetweenClouds() {
        return this.trex.contains(new Coordinate(1, 5));
    }

    public void jump(){
        if(!lookForHeadBetweenClouds()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() - 1);
            }
        }
        else this.isJump = false;
    }

    public boolean lookForFeetOnTheGround() {
        return this.trex.contains(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 1, 5));
    }

    public void setGravity() {
        if (!lookForFeetOnTheGround()) {
            this.isJump = false;
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() + 1);
            }
        }
    }
}