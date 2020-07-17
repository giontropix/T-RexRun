import java.util.ArrayList;

public class Trex extends Thread {

    private boolean isJump = false;
    final FieldOfPalm fieldOfPalm = new FieldOfPalm();
    ArrayList<Coordinate> trex = new ArrayList<>();

    public Trex() {
        printTrex();
    }

    public void printTrex(){
        for (int i = this.fieldOfPalm.getFieldHeight() - 2; i > 5; i--) {
            this.trex.add(new Coordinate(i, 5));
            this.trex.add(new Coordinate(i, 6));
        }
    }

    @Override
    public void run(){
        try {
            while (this.fieldOfPalm.isInGame()) {
                lookForHeadBetweenClouds();
                lookForFeetInAir();
                if(this.isJump)
                    jump();
                else
                    configureGravity();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public boolean lookForFeetInAir(){
        return !this.trex.contains(new Coordinate(this.fieldOfPalm.getFieldHeight() - 1, 5));
    }

    private void configureGravity(){
        if (lookForFeetInAir()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() + 1);
            }
        }
    }
}