package model;

public class Jugador {

    public String type="Jugador";
    public String personaje;
    int vidas;
    public String id;

    public Jugador (String personaje, String id){
        this.personaje=personaje;
        this.vidas=3;
        this.id=id;
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

public String getPersonaje() {
    return personaje;
}

public void setPersonaje(String personaje) {
    this.personaje = personaje;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

}


