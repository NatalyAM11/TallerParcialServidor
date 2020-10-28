package main;

import model.Jugador;
import model.Posicion;
import model.Disparo;
import model.Vida;

public interface OnMessageListener {
	
	
	void messageJugador (Jugador jugador, String id);
	
	void messagePosicion (Posicion posicion);
	
	void messageDisparo (Disparo disparo, String id);
	
}
