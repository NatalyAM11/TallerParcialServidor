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
	PImage cuphead, mugman;

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

	}

	public void draw() {
		background(0);

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

				if (j != null && p != null) {

					// opciones de personaje para jugador 1
					if (i == 0) {
						// Validamos si escogen a cuphead
						if (j.personaje.equals("cuphead")) {
							image(cuphead, p.getX(), p.getY(), 80, 100);
							fill(255, 0, 0);
						}
						// Validamos si escogen a mugman
						if (j.personaje.equals("mugman")) {
							image(mugman, p.getX(), p.getY(), 80, 100);
							fill(0, 0, 255);
						}

						// nombre del J1
						text("Jugador1", p.getX() + 20, p.getY() + 110);
					}

					// opciones de personajes para jugador 2
					if (i == 1) {
						// Validamos si escogen a cuphead
						if (j.personaje.equals("cuphead")) {
							image(cuphead, p.getX(), p.getY(), 80, 100);
							fill(255, 0, 0);
						}

						// Validamos si esogen a mugman
						if (j.personaje.equals("mugman")) {
							image(mugman, p.getX(), p.getY(), 80, 100);
							fill(0, 0, 255);
						}

						// nombre del J2
						text("Jugador2", p.getX() + 20, p.getY() + 110);
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
			// }

			// Texto del puntaje de los jugadores
			fill(255);
			text(puntajeJ1, 50, 20);
			text(puntajeJ2, 200, 20);

			// for para pintar las vidas
			
					//Vida v = vidas.get(i);
					if(j!=null) {
						System.out.println(j.getVidas());
					if (s1.getID().equals(j.getId())) {
						fill(241, 101, 248);
						ellipse(j.getVidas()*2, 50, 20, 20);
					}

					if (s2.getID().equals(j.getId())) {
						fill(237, 248, 101);
						ellipse(j.getVidas()*2, 50, 20, 20);
					}
					}
			

			// metodo que valida que el jugador pierda vida cuando es golpeado por ataque de
			// la reina
			// validarAtaqueEnemigo();

			// Validamos cuando el jugador pierda todas sus vidas
			/*
			 * if (vidas.size() == 0) { System.out.println("FIN DEL JUEGOOOOOOOOOOOOOOO!");
			 * }
			 */

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
	public void validarAtaqueEnemigo() {

		for (int i = 0; i < reina.enemigos.size(); i++) {

			if (posicion != null) {
				if (dist(reina.enemigos.get(i).getPosX() + 15, reina.enemigos.get(i).getPosY() + 15,
						posicion.getX() + 40, posicion.getY() + 50) < 20) {

					// Eliminamos el enemigo que toque al jugador
					reina.enemigos.remove(reina.enemigos.get(i));

					// quitamos las vida del jugador
					for (int j = 0; j < tcp.getSesion().size(); j++) {

						Session J1 = tcp.getSesion().get(0);
						Session J2 = tcp.getSesion().get(1);

						Vida v = vidas.get(i);

						if (vidas.size() > 0) {
							vidas.remove(vidas.size() - 1);
						}
					}

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
	public void messageJugador(Jugador jugador,String id) {
		// TODO Auto-generated method stub
		
	}

}
