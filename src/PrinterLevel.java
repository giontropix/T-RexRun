public class PrinterLevel extends Thread {
    private final Trex trex = new Trex();
    private final Obstacle obstacle = new Obstacle();
    private final String playerName;

    public PrinterLevel(String playerName) {
        this.playerName = playerName;
        this.obstacle.start();
        this.trex.start();
    }

    public void run(){
        try {
             while (this.obstacle.isInGame()) {
                 this.crashGameOver();
                 this.addScoreFromJumpedObstacle();
                 //System.out.println("\nPLAYER NAME: " + this.playerName.toUpperCase());
                 //System.out.println("SCORE: " + this.obstacle.getScore() + "\n");
                 System.out.println(this.toString());
                 Thread.sleep(this.obstacle.speedUpGame()/2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Trex getTrex() {
        return this.trex;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    private void addScoreFromJumpedObstacle() {
        if (this.obstacle.getCactus().contains(new Coordinate(this.getObstacle().getFieldHeight() - 2, 4))) {
            this.obstacle.setScore(this.obstacle.getScore() + 10);
            return;
        }
        if (this.obstacle.getCactus().contains(new Coordinate(this.getObstacle().getFieldHeight() - 1, 4))) {
            this.obstacle.setScore(this.obstacle.getScore() + 5);
        }
        if (this.obstacle.getBird().size() > 0) { //ADDED THIS CONDITION BECAUSE OF THE RARE POSSIBILITY A BIRD CAN APPEAR
            for (int i = 0; i < this.obstacle.getBird().size(); i++) {
                if ((this.trex.getTrex().getX() < this.getObstacle().getBird().get(i).getX()) && (this.trex.getTrex().getY() == this.getObstacle().getBird().get(i).getY())) {
                    this.obstacle.setScore(this.obstacle.getScore() + 15);
                }
            }
        }
    }

    public void crashGameOver(){
        for (int i = 0; i < this.obstacle.getCactus().size(); i++) {
            if(this.trex.getTrex().equals(new Coordinate(this.obstacle.getCactus().get(i).getX(), this.obstacle.getCactus().get(i).getY())))
                this.obstacle.setInGame(false);
        }
        for (int i = 0; i < this.obstacle.getBird().size(); i++) {
            if(this.trex.getTrex().equals(new Coordinate(this.obstacle.getBird().get(i).getX(), this.obstacle.getBird().get(i).getY())))
                this.obstacle.setInGame(false);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < this.obstacle.getFieldHeight() + 1; i++) {
            result.append(i).append("\t").append("[");
            for(int j = 1; j < this.obstacle.getFieldWidth(); j++) {
                if (this.obstacle.getCactus().contains(new Coordinate(i, j)))
                    result.append("\u001B[32m|\u001B[0m");
                else if (this.obstacle.getBird().contains(new Coordinate(i, j)))
                    result.append("\u001B[35m=\u001B[0m");
                else if (this.trex.getTrex().equals(new Coordinate(i, j)))
                    result.append("\u001B[31mO\u001B[0m");
                else if (this.obstacle.getGround().contains(new Coordinate(i, j))) {
                    result.append("~");
                }
                else
                    result.append(" ");
            }
            result.append("]\n");
        }
        return result.toString();
    }
}