import java.util.ArrayList;

public class Trex extends Thread {

    private boolean isJump = false;
    final FieldOFObstacle fieldOFObstacle = new FieldOFObstacle(100);
    ArrayList<Coordinate> trex = new ArrayList<>();

    public Trex() {
        printTrex();
        this.fieldOFObstacle.start();
    }

    public void printTrex(){
        for (int i = this.fieldOFObstacle.getHeightField() - 2; i > 5; i--) {
            trex.add(new Coordinate(i, 5));
            trex.add(new Coordinate(i, 6));
        }
    }

    @Override
    public void run(){
        try {
            while (this.fieldOFObstacle.isInGame()) {
                Thread.sleep(1200);
                headBetweenClouds();
                feetInAir();
                if(this.isJump)
                    tRexJump();
                else
                    tRexGravity();
                System.out.println(this.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean headBetweenClouds() {
        return this.trex.contains(new Coordinate(3, 5));
    }

    public void tRexJump(){
        if(!headBetweenClouds()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() - 1);
            }
        }
        else this.isJump = false;
    }

    public boolean feetInAir(){
        return !this.trex.contains(new Coordinate(this.fieldOFObstacle.getHeightField() - 1, 5));
    }

    private void tRexGravity(){
        if (feetInAir()) {
            for (Coordinate coordinate : this.trex) {
                coordinate.setX(coordinate.getX() + 1);
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < this.fieldOFObstacle.getHeightField() + 1; i++) {
            result.append(i).append("\t").append("[");
            for(int j = 1; j < this.fieldOFObstacle.getWeightField(); j++) {
                if (this.fieldOFObstacle.palm.contains(new Coordinate(i, j)))
                    result.append("\u001B[32mO\u001B[0m");
                else if (this.trex.contains(new Coordinate(i, j)))
                    result.append("\u001B[31mO\u001B[0m");
                else
                    result.append(" ");
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
