public class PrinterLevel extends Thread {
    Trex trex = new Trex();
    FieldOfPalm fieldOfPalm = new FieldOfPalm();

    public PrinterLevel() {
        this.fieldOfPalm.start();
        this.trex.start();
    }

    public void run(){
        try {
            while (this.fieldOfPalm.isInGame()) {
                System.out.println(this.toString());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < this.fieldOfPalm.getFieldHeight() + 1; i++) {
            result.append(i).append("\t").append("[");
            for(int j = 1; j < this.fieldOfPalm.getFieldWidth(); j++) {
                if (this.fieldOfPalm.palm.contains(new Coordinate(i, j)))
                    result.append("\u001B[32m|\u001B[0m");
                else if (this.trex.trex.contains(new Coordinate(i, j)))
                    result.append("\u001B[31mO\u001B[0m");
                else
                    result.append(" ");
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
