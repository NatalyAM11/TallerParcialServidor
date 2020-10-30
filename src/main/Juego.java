package main;

import model.Jugador;
import model.Posicion;
import model.Vida;

import java.util.ArrayList;

import model.Disparo;
import processing.core.PApplet;
import processing.core.PImage;

public class Juego extends PApplet implements OnMessageListener {

	TCPSingleton tcp;

	// Imagenes
	PImage cuphead, mugman, vidaR, vidaA, puntajeA, puntajeR;

	// Arraylist de la vida de los jugadores
	private ArrayList<Vida> vidas;

	// vidas
	Vida vida;

	// Disparos
	private ArrayList<Disparo> disparos;

	// Posicion
	Posicion posicion;
	int x, y;

	// pantallas
	int pantalla;

	// Reina de dulce
	ReinaDulce reina;

	// int que controla la vida total de la reina
	int vidaReina;

	// int que controla el puntaje de ambos jugadores
	int puntajeJ1;
	int puntajeJ2;

	Session s1, s2;

	Jugador j;
	
	boolean perdio1, perdio2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Juego");

	}

	public void settings() {
		size(800, 500);
	}

	public void setup() {
		tcp = TCPSingleton.getInstance();
		tcp.setObserver(this);

		// Cargamos las imagenes de los personajes
		cuphead = loadImage("img/avionCupHead.png");
		mugman = loadImage("img/avionMugman.png");
		vidaR = loadImage("img/vidaR.png");
		vidaA = loadImage("img/vidaA.png");
		puntajeR = loadImage("img/puntajeR.png");
		puntajeA = loadImage("img/puntajeA.png");

		pantalla = 0;

		// Arraylist del disparo de los jugadores
		disparos = new ArrayList<>();

		// Arraylist del disparo de los jugadores
		vidas = new ArrayList<>();

		vidaReina = 1000;

		// Creamos a la reina dulce
		reina = new ReinaDulce(this, 200, 100, 5, vidaReina);

		puntajeJ1 = 0;
		puntajeJ2 = 0;
		
		perdio1=false;
		perdio2=false;
		
		

	}

	public void draw() {
		background(0);

		text("x=" + mouseX + "y=" + mouseY, mouseX, mouseY);

		// Valido que cuando el jugador ya no sea nulo para poder seguir a la otra
		// pantalla del juego
		if (tcp.getSesion().size() >= 1) {
			pantalla = 1;
		}

		if (vidaReina == 0) {
			pantalla = 3;
		}

		switch (pantalla) {
		case 0:
			fill(255);
			text("intro", 255, 255);

			break;

		// pantalla juego
		case 1:

			// Enemigos
			reina.pintar();
			reina.movimiento();

			for (int i = 0; i < tcp.getSesion().size(); i++) {

				j = tcp.getSesion().get(i).jugador;
				Posicion p = tcp.getSesion().get(i).posicion;

				// Validar el ataque del enemigo a los personajes
				validarAtaqueEnemigo(j, p);

				if (j != null && p != null) {

					// opciones de personaje para jugador 1

					if (i == 0) {
						// Validamos si escogen a cuphead
						if (j.personaje.equals("cuphead")) {
							image(cuphead, p.getX(), p.getY(), 80, 100);

							// pinta texto puntaje del color del personaje
							image(puntajeR, 20, 50, 90, 30);
							fill(255, 0, 0);
						}
						// Validamos si escogen a mugman
						if (j.personaje.equals("mugman")) {
							image(mugman, p.getX(), p.getY(), 80, 100);

							// pinta texto puntaje del color del personaje
							image(puntajeA, 20, 50, 90, 30);
							fill(0, 0, 255);
						}

						// nombre del J1
						text("Jugador1", p.getX() + 20, p.getY() + 110);

						// for que permite pintar las vidas
						for (int k = 0; k < j.getVidas(); k++) {

							// los if permiten diferenciar de que color es el icono se vida depenediendo del
							// personaje
							if (j.personaje.equals("cuphead")) {
								image(vidaR, k * 40 + 20, 20, 38, 20);
							}

							if (j.personaje.equals("mugman")) {
								image(vidaA, k * 40 + 20, 20, 38, 20);
							}
						}

						// pintamos el puntaje del jugador 1
						text(puntajeJ1, 112, 68);

						// Validamos cuando el jugador 1 pierde todas sus vidas
						if (j.getVidas() == 0) {
							textSize(25);
							text("El jugador 1 ha perdido", 255, 80);
							perdio1=true;
							System.out.println("Perdio el jugador 1");
						}
					}

					// opciones de personajes para jugador 2

					if (i == 1) {
						// Validamos si escogen a cuphead
						if (j.personaje.equals("cuphead")) {
							image(cuphead, p.getX(), p.getY(), 80, 100);

							// pinta texto puntaje del color del personaje
							image(puntajeR, 630, 50, 90, 30);
							fill(255, 0, 0);
						}

						// Validamos si esogen a mugman
						if (j.personaje.equals("mugman")) {
							image(mugman, p.getX(), p.getY(), 80, 100);

							// pinta texto puntaje del color del personaje
							image(puntajeA, 630, 50, 90, 30);
							fill(0, 0, 255);
						}

						// nombre del J2
						text("Jugador2", p.getX() + 20, p.getY() + 110);

						// for que permite pintar las vidas
						for (int k = 0; k < j.getVidas(); k++) {
							// los if permiten diferenciar de que color es el icono se vida depenediendo del
							// personaje

							if (j.personaje.equals("cuphead")) {
								image(vidaR, k * 40 + 660, 20, 38, 20);
							}

							if (j.personaje.equals("mugman")) {
								image(vidaA, k * 40 + 660, 20, 38, 20);
							}

						}

						// pintamos el puntaje del jugador 2
						text(puntajeJ2, 730, 68);

						// Validamos cuando el jugador 2 pierde todas sus vidas
						if (j.getVidas() == 0) {
							text("El jugador 1 ha perdido", 255, 80);
							perdio2=true;
							System.out.println("Perdio el jugador 2");
						}
					}
					
					if(perdio1==true && perdio2==true) {
						System.out.println("perdieron todos");
					}
						
						
					
			
				}

			}

			// for que recorre el arraylist de disparo
			if (tcp.getSesion().size() > 1) {
				s1 = tcp.getSesion().get(0);
				s2 = tcp.getSesion().get(1);
			}

			// for (int i = 0; i < tcp.getSesion().size(); i++) {
			for (int j = 0; j < disparos.size(); j++) {

				// Disparo disparoN = tcp.getSesion().get(i).disparo;
				Disparo disparoN = disparos.get(j);

				fill(255);
				ellipse(disparoN.getX(), disparoN.getY(), 20, 20);

				// Hacemos que las balas se muevan hacia arriba
				disparoN.setY(disparoN.getY() - disparoN.getVel());

				// Validamos que si la bala del jugador toca a la reina, esta resulta lastimada
				if (dist(disparoN.getX(), disparoN.getY(), reina.getPosX(), reina.getPosY()) < 80) {
					disparos.remove(j);
					vidaReina = vidaReina - 50;

					System.out.println("puntaje J1" + puntajeJ1);

					if (s1.getID().equals(disparoN.getId())) {
						puntajeJ1 = puntajeJ1 + 50;
					}

					if (s2.getID().equals(disparoN.getId())) {
						puntajeJ2 = puntajeJ2 + 50;
					}
				}

			}

			// Validamos cuando gana determinado jugador
			if (puntajeJ1 == 900) {
				System.out.println("GANO EL J1");
			}

			if (puntajeJ2 == 900) {
				System.out.println("GANO EL J2");
			}

			break;

		case 3:

			fill(255);
			textSize(50);
			text("Gano", 255, 255);
			break;

		}

	}

	// validamos cuando alguno de los ataques de la reina toque a los jugadores,
	// estos pierden vida
	public void validarAtaqueEnemigo(Jugador j, Posicion posicion) {

		/*for (int i = 0; i < reina.enemigos.size(); i++) {

			if (posicion != null) {

				if (dist(reina.enemigos.get(i).getPosX() + 15, reina.enemigos.get(i).getPosY() + 30,
						posicion.getX() + 80, posicion.getY()) < 25) {

					// Eliminamos el enemigo que toque al jugador
					reina.enemigos.remove(reina.enemigos.get(i));

					// elminamos una vida del jugador
					j.setVidas(j.getVidas() - 1);
				}
			}
		}*/
		

		
		  for (int i = 0; i < reina.enemigos.size(); i++) {
		  
		  if (posicion != null) {
		  
		  if ((reina.enemigos.get(i).getPosX() + 15 < posicion.getX() + 80 &&
		  reina.enemigos.get(i).getPosX() + 15 > posicion.getX()) &&
		  (reina.enemigos.get(i).getPosY() + 30 > posicion.getY() &&
		  reina.enemigos.get(i).getPosY() + 30 < posicion.getY()+50)) {
		  
		  // Eliminamos el enemigo que toque al jugador
		  reina.enemigos.remove(reina.enemigos.get(i));
		  
		  // elminamos una vida del jugador 
		   	j.setVidas(j.getVidas() - 1);
		   	 } 
		  }
		  }
		 

	}

	// OnMessageListener
	public void messagePosicion(model.Posicion posicion) {
		this.posicion = posicion;

	}

	public void messageDisparo(model.Disparo disparo, String id) {
		Disparo dis = new Disparo(disparo.getX(), disparo.getY(), disparo.getVel(), id);
		disparos.add(dis);

	}

	@Override
	public void messageJugador(Jugador jugador, String id) {
		// TODO Auto-generated method stub

	}

}
