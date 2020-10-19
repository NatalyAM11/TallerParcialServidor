package model;


public class Vida {

    public String type="Vida";
    public int vidas ;

    public Vida(int vidas){
        this.vidas=vidas;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
