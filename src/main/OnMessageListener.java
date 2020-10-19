package main;

import model.Jugador;
import model.Posicion;
import model.Disparo;
import model.Vida;

public interface OnMessageListener {
	
	void messageVida (Vida vida);
	
	void messageJugador (Jugador jugador);
	
	void messagePosicion (Posicion posicion);
	
	void messageDisparo (Disparo disparo);
	
}
