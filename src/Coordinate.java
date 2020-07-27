public class Coordinate { //BASE CLASS FOR BUILD OBSTACLES AND TREX
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) { //EQUALS OVERRIDED BECAUSE WE NEED TO KNOW IF THE COORDINATES OF OBSTACLES AND TREX WILL BE THE SAME
        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate)o;
            return c.x == this.x && c.y == this.y;
        }
        return false;
    }
}