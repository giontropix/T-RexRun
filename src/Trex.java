public class Trex extends Thread {

    private boolean isJump = false;
    private final FieldOfObstacle fieldOfObstacle = new FieldOfObstacle();
    private final Coordinate tRex;

    public Trex() {
        tRex = new Coordinate(5,5);
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
                 Thread.sleep(this.fieldOfObstacle.speedUpGame()/2);
            } while (this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Coordinate gettRex() {
        return this.tRex;
    }

    public boolean isJump() {
        return this.isJump;
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean lookForHeadBetweenClouds() {
        return this.tRex.equals(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 5, 5));
    }

    public void jump(){
        if(!lookForHeadBetweenClouds())
            this.tRex.setX(this.tRex.getX() - 1);
        else this.isJump = false;
    }

    public boolean lookForFeetOnTheGround() {
        return this.tRex.equals(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 1, 5));
    }

    public void setGravity() {
        if (!lookForFeetOnTheGround()) {
            this.isJump = false;
            this.tRex.setX(this.tRex.getX() + 1);
        }
    }
}