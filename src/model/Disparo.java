package model;


public class Disparo {

    public int x,y,vel;
    public String type="Disparo";
    public String id;

    public Disparo(int x, int y, int vel,String id){
        this.x=x;
        this.y=y;
        this.vel=vel;
        this.id=id;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


