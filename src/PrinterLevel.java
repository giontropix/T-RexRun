public class PrinterLevel extends Thread {
    private final Trex trex = new Trex();
    private final FieldOfObstacle fieldOfObstacle = new FieldOfObstacle();
    String playerName;

    public PrinterLevel(String playerName) {
        this.playerName = playerName;
        this.fieldOfObstacle.start();
        this.trex.start();
    }

    public void run(){
        try {
             do {
                 this.crashGameOver();
                 System.out.println(this.fieldOfObstacle.getCactus().size());
                 System.out.println("\nIN GAME: " + this.fieldOfObstacle.isInGame());
                 System.out.println("\nPLAYER NAME: " + this.playerName.toUpperCase());
                 System.out.println("SCORE: " + this.fieldOfObstacle.getScore() + "\n");
                 System.out.println(this.toString());
                 Thread.sleep(this.fieldOfObstacle.increaseSpeedAccordingToPoints());
            } while(this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Trex getTrex() {
        return this.trex;
    }

    public FieldOfObstacle getFieldOfObstacle() {
        return this.fieldOfObstacle;
    }

    private void crashGameOver(){
        for (int i = 0; i < this.fieldOfObstacle.getCactus().size(); i++) {
            if(this.trex.gettRex().equals(new Coordinate(this.fieldOfObstacle.getCactus().get(i).getX(), this.fieldOfObstacle.getCactus().get(i).getY())))
                this.fieldOfObstacle.setInGame(false);
        }
        for (int i = 0; i < this.fieldOfObstacle.getBird().size(); i++) {
            if(this.trex.gettRex().equals(new Coordinate(this.fieldOfObstacle.getBird().get(i).getX(), this.fieldOfObstacle.getBird().get(i).getY())))
                this.fieldOfObstacle.setInGame(false);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < this.fieldOfObstacle.getFieldHeight() + 1; i++) {
            result.append(i).append("\t").append("[");
            for(int j = 1; j < this.fieldOfObstacle.getFieldWidth(); j++) {
                if (this.fieldOfObstacle.getCactus().contains(new Coordinate(i, j)))
                    result.append("\u001B[32m|\u001B[0m");
                else if (this.fieldOfObstacle.getBird().contains(new Coordinate(i, j)))
                    result.append("\u001B[35m=\u001B[0m");
                else if (this.trex.gettRex().equals(new Coordinate(i, j)))
                    result.append("\u001B[31mO\u001B[0m");
                else if (this.fieldOfObstacle.getGround().contains(new Coordinate(i, j))) {
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