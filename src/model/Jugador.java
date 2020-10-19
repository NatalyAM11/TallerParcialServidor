package model;

public class Jugador {

    public String type="Jugador";
    public String personaje;


    public Jugador (String personaje){
        this.personaje=personaje;

    }

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public String getPersonaje() {
    return personaje;
}

public void setPersonaje(String personaje) {
    this.personaje = personaje;
}

}


