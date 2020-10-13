package model;


public class Posicion {

    public int x;
    public String type="Posicion";

    public Posicion(int x){
        this.x=x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
