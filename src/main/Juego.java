package main;

import model.Jugador;
import model.Posicion;

import java.util.ArrayList;

import model.Disparo;
import processing.core.PApplet;
import processing.core.PImage;

public class Juego extends PApplet implements OnMessageListener {
	
	TCPSingleton tcp;
	
	//Imagenes
	PImage cuphead,mugman;
	
	//Jugador
	Jugador jugador;
	
	//Disparos
	private ArrayList<Disparo> disparos;
	
	//Posicion
	Posicion posicion;
	int x,y;
	
	//pantallas
	int pantalla;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Juego");
		
	}

	public void settings() {
		size(800,500);
	}
	
	public void setup() {
		tcp= TCPSingleton.getInstance();
		tcp.setObserver(this);
		
		cuphead=loadImage("img/avionCupHead.png");
		mugman= loadImage("img/avionMugman.png");
		
		pantalla=0;
		
		disparos= new ArrayList<>();
		
	}
	
	
	public void draw() {
		background(0);
		
		//Valido que cuando el jugador ya no sea nulo para poder seguir a la otra pantalla del juego
		if(jugador!=null) {
		pantalla=1;
		
		}
		
		switch (pantalla) {
		case 0: 
			fill(255);
			text("intro",255,255);
			break;
			
		//pantalla juego
		case 1:
			
			if(tcp.posicion!=null) {
				
				//Validamos si escogen a cuphead
				if(jugador.personaje.equals("cuphead")) {
				image(cuphead,posicion.getX(),400,80,100);
				}
				
				//Validamos si esogen a mugman
				if(jugador.personaje.equals("mugman")) {
					image(mugman,posicion.getX(),400,80,100);
				}
				
				}else {
					if(jugador.personaje.equals("cuphead")) {
							image(cuphead,400,400,80,100);	
						}
					if(jugador.personaje.equals("mugman")) {
						image(mugman,400,400,80,100);	
					}
					
				}
			
			
				for(int i=0; i<disparos.size();i++) {
					Disparo disparoN= disparos.get(i);
					
					fill(255);
					ellipse(disparoN.getX(),disparoN.getY(),20,20);
					
					disparoN.setY(disparoN.getY()-disparoN.getVel());
				}
			
			break;
			
		}
		
		
		
		
	}

	
	public void Jugador(model.Jugador jugador) {
		this.jugador=jugador;
		
	}

	
	public void Posicion(model.Posicion posicion) {
	this.posicion=posicion;
		
	}

	public void Disparo(model.Disparo disparo) {
	disparos.add(disparo);
		
	}
}
