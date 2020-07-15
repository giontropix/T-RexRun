public class Game extends Thread {
    Character[][] field;
    boolean isInGame = true;

    public Game(int landscape){
        field = new Character[10][landscape];
        paintCharacter(this.tRex());
    }

    @Override
    public void run() {
        try {
            while(isInGame){
                Thread.sleep (1200);
                Character palm = generatePalm();
                paintCharacter(palm);
                moveObstacle();
                feetInAir();
                tRexGravity();
                System.out.println(this.toString());
            }

        } catch(Exception e){
            System.out.println(e);
        }
    }

    public Character tRex(){
        return new Character(Character.Species.TREX, 3);
    }

    public boolean feetInAir(){
        return field[field.length - 2][5] == null;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void tRexJump(){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null) {
                    if(field[i][j].getSpecies() == Character.Species.TREX){
                        field[i - 2][j] = field[i][j];
                        field[i][j] = null;
                    }
                }
            }
        }
    }

    public Character generatePalm(){
        double random = Math.random();
        if(random <= 0.1)
            return new Character(Character.Species.LITTLEPALM, 1);
        if(random <= 0.2)
            return new Character(Character.Species.BIGPALM, 2);
        else
            return null;
    }

    public void paintCharacter(Character character){
        if(character != null) {
            if(character.getSpecies() == Character.Species.BIGPALM
                    || character.getSpecies() == Character.Species.LITTLEPALM){
                for (int i = 0; i < character.getHeight(); i++) {
                    field[(field.length - 2) - i][field[0].length - 1] = character;
                }
            }
            else {
                for (int i = 0; i < character.getHeight(); i++) {
                    field[(field.length - 3) - i][5] = character;
                    field[(field.length - 3) - i][6] = character;
                }
            }
        }
    }

    public void tRexGravity(){
        if (feetInAir()) {
            for (int i = field.length - 1; i > 0; i--) {
                for (int j = 0; j < field[i].length - 1; j++) {
                    if(field[i][j] != null) {
                        if (field[i][j].getSpecies() == Character.Species.TREX) {
                            field[i + 1][j] = field[i][j];
                            field[i][j] = null;
                        }
                    }
                }
            }
        }
    }

    public void moveObstacle(){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null) {
                    if (field[i][j].getSpecies() != Character.Species.TREX) {
                        field[i][j - 1] = field[i][j];
                        field[i][j] = null;
                    }
                }
            }
        }
    }

    /*public String generateGround(){
        double random = Math.random();
        if(random <= 0.25)
            return "*";
        else
            return "_";
    }*/

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < this.field.length; x++) {
            result.append(x).append("\t").append("[");
            for(int y = 0; y < this.field[x].length; y++) {
                if(field[x][y] == null)
                    result.append(" ");
                else if(field[x][y].getSpecies() == Character.Species.LITTLEPALM
                || field[x][y].getSpecies() == Character.Species.BIGPALM){
                    result.append("\u001B[32mO\u001B[0m");
                }
                else if(field[x][y].getSpecies() == Character.Species.TREX ){
                    result.append("\u001B[31mO\u001B[0m");
                }
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
