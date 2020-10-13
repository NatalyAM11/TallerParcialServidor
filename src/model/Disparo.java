package model;


public class Disparo {

    public int x,y,vel;
    public String type="Disparo";

    public Disparo(int x, int y, int vel){
        this.x=x;
        this.y=y;
        this.vel=vel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


