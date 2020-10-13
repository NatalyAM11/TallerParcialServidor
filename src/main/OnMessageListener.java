package main;

import model.Jugador;
import model.Posicion;
import model.Disparo;

public interface OnMessageListener {
	
	void Jugador (Jugador jugador);
	
	void Posicion (Posicion posicion);
	
	void Disparo (Disparo disparo);

}
