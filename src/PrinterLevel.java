public class PrinterLevel extends Thread {
    private final Trex trex = new Trex();
    private final FieldOfObstacle fieldOfObstacle = new FieldOfObstacle();
    private int[][] gridForGUI = new int[this.fieldOfObstacle.getFieldHeight() + 1][this.fieldOfObstacle.getFieldWidth()];
    String playerName;

    public PrinterLevel(String playerName) {
        this.playerName = playerName;
        this.fieldOfObstacle.start();
        this.trex.start();
    }

    public void run(){
        try {
             do {
                 this.importIntoGridForGUI();
                 this.crashGameOver();
                 System.out.println(this.fieldOfObstacle.getPalm().size());
                 System.out.println("\nIN GAME: " + this.fieldOfObstacle.isInGame());
                 System.out.println("\nPLAYER NAME: " + this.playerName.toUpperCase());
                 System.out.println("SCORE: " + this.fieldOfObstacle.getScore() + "\n");
                 System.out.println(this.toString());
                 Thread.sleep(500);
            } while(this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Trex getTrex() {
        return this.trex;
    }

    public int[][] getGridForGUI() {
        return gridForGUI;
    }

    public FieldOfObstacle getFieldOfObstacle() {
        return this.fieldOfObstacle;
    }

    private void crashGameOver(){
        for (int i = 0; i < this.fieldOfObstacle.getPalm().size(); i++) {
            if(this.trex.getTrex().contains(new Coordinate(this.fieldOfObstacle.getPalm().get(i).getX(), this.fieldOfObstacle.getPalm().get(i).getY())))
                this.fieldOfObstacle.setInGame(false);
        }
        for (int i = 0; i < this.fieldOfObstacle.getBird().size(); i++) {
            if(this.trex.getTrex().contains(new Coordinate(this.fieldOfObstacle.getBird().get(i).getX(), this.fieldOfObstacle.getBird().get(i).getY())))
                this.fieldOfObstacle.setInGame(false);
        }
    }

    private void importIntoGridForGUI(){
        for (int i = 1; i < this.gridForGUI.length; i++) {
            for (int j = 1; j < this.gridForGUI[i].length; j++) {
                if(this.trex.getTrex().contains(new Coordinate(i, j)))
                    this.gridForGUI[i][j] = 1;
                else if(this.fieldOfObstacle.getPalm().contains(new Coordinate(i, j)))
                    this.gridForGUI[i][j] = 2;
                else if(this.fieldOfObstacle.getBird().contains(new Coordinate(i, j)))
                    this.gridForGUI[i][j] = 3;
                else if(this.fieldOfObstacle.getGround().contains(new Coordinate(i, j)))
                    this.gridForGUI[i][j] = 4;
                else this.gridForGUI[i][j] = 0;
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        /*for (int i = 1; i < this.fieldOfObstacle.getFieldHeight() + 1; i++) {
            result.append(i).append("\t").append("[");
            for(int j = 1; j < this.fieldOfObstacle.getFieldWidth(); j++) {
                if (this.fieldOfObstacle.getPalm().contains(new Coordinate(i, j)))
                    result.append("\u001B[32m|\u001B[0m");
                else if (this.fieldOfObstacle.getBird().contains(new Coordinate(i, j)))
                    result.append("\u001B[35m=\u001B[0m");
                else if (this.trex.getTrex().contains(new Coordinate(i, j)))
                    result.append("\u001B[31mO\u001B[0m");
                else if (this.fieldOfObstacle.getGround().contains(new Coordinate(i, j))) {
                    result.append("~");
                }
                else
                    result.append(" ");
            }
            result.append("]\n");*/
        for (int i = 0; i < gridForGUI.length; i++) {
            result.append(i).append("\t").append("[");
            for (int j = 0; j < gridForGUI[i].length - 1; j++) {
                result.append(gridForGUI[i][j]);
            }
            result.append("]\n");
        }
        return result.toString();
    }
}