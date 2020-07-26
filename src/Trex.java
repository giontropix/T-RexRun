public class Trex extends Thread {

    private boolean isJump = false;
    private final Obstacle obstacle = new Obstacle();
    private final Coordinate trex;

    public Trex() {
        this.trex = new Coordinate(this.obstacle.getFieldHeight() - 1,5);
    }

    @Override
    public void run(){
        try {
             while (this.obstacle.isInGame()) {
                 if(this.isJump) {
                     this.lookForHeadBetweenClouds();
                     this.jump();
                 }
                 else {
                     this.lookForFeetOnTheGround();
                     this.setGravity();
                 }
                 Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Coordinate getTrex() {
        return this.trex;
    }

    public boolean isJump() {
        return this.isJump;
    }

    public void setJump(boolean jump) {
        this.isJump = jump;
    }

    public boolean lookForHeadBetweenClouds() { //CHECK IF THE TREX IS AT MAX JUMP HEIGHT
        return this.trex.equals(new Coordinate(this.obstacle.getFieldHeight() - 5, 5));
    }

    private void jump(){
        if(!lookForHeadBetweenClouds())
            this.trex.setX(this.trex.getX() - 1);
        else this.isJump = false;
    }

    public boolean lookForFeetOnTheGround() { //CHECK IF THE TREX IS ON THE GROUND
        return this.trex.equals(new Coordinate(this.obstacle.getFieldHeight() - 1, 5));
    }

    private void setGravity() {
        if (!lookForFeetOnTheGround()) {
            this.isJump = false; //RESET JUMP TO FALSE TO AVOID ANOTHER JUMP DURING THE JUMP(SEE ALSO MAIN AND GUIMANAGER)
            this.trex.setX(this.trex.getX() + 1);
        }
    }
}