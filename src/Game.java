public class Game extends Thread {
    Character[][] field;
    boolean isInGame = true;

    public Game(int landscape){
        field = new Character[10][landscape];
        paintCharacter(this.Trex());
    }

    @Override
    public void run() {
        try {
            while(isInGame){
                Thread.sleep (1200);
                Character palm = generatePalm();
                paintCharacter(palm);
                moveObstacle();
                System.out.println(this.toString());
            }

        } catch(Exception e){ }
    }

    public Character Trex(){
      return new Character("trex", 3);
    }

    public Character generatePalm(){
        double random = Math.random();
        if(random <= 0.1)
            return new Character("littlePalm", 1);
        if(random <= 0.2)
            return new Character("bigPalm", 2);
        else
            return null;
    }

    public void paintCharacter(Character character){
        if(character != null) {
            if(!character.getName().equalsIgnoreCase("trex")){
                for (int i = 0; i < character.getHeight(); i++) {
                    field[(field.length - 2) - i][field[0].length - 1] = character;
                }
            }
            else {
                for (int i = 0; i < character.getHeight(); i++) {
                    field[(field.length - 2) - i][5] = character;
                }
            }
        }
    }

    public void moveObstacle(){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if(field[i][j] != null)
                    field[i][j - 1] = field[i][j];
                field[i][j] = null;
            }
        }
    }

    public String generateGround(){
        double random = Math.random();
        if(random <= 0.25)
            return "*";
        else
            return "_";
    }

    public String toString() {
        String result = "";
        for (int x = 0; x < this.field.length; x++) {
            result += x + "\t" + "[";
            for(int y = 0; y < this.field[x].length; y++) {
                if(field[x][y] == null)
                    result += " ";
                else if(!field[x][y].getName().equalsIgnoreCase("trex")){
                    result += "\u001B[32mO\u001B[0m";
                }
                else if(field[x][y].getName().equalsIgnoreCase("trex")){
                    result += "\u001B[31mO\u001B[0m";
                }
            }
            result += "]\n";
        }
        return result;
    }
}
