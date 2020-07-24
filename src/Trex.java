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
                 Thread.sleep(200);
            } while (this.fieldOfObstacle.isInGame());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Coordinate gettRex() {
        return tRex;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean lookForHeadBetweenClouds() {
        return this.tRex.equals(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 5, 5));
    }

    public void jump(){
        if(!lookForHeadBetweenClouds())
            tRex.setX(tRex.getX() - 1);
        else this.isJump = false;
    }

    public boolean lookForFeetOnTheGround() {
        return this.tRex.equals(new Coordinate(this.fieldOfObstacle.getFieldHeight() - 1, 5));
    }

    public void setGravity() {
        if (!lookForFeetOnTheGround()) {
            this.isJump = false;
            tRex.setX(tRex.getX() + 1);
        }
    }
}