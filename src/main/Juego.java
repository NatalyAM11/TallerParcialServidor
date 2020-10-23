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

	int vidaReina;

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
			System.out.println(tcp.getSesion().size());
			for (int i = 0; i < tcp.getSesion().size(); i++) {
				Jugador j = tcp.getSesion().get(i).jugador;
				Posicion p = tcp.getSesion().get(i).posicion;

				if (j != null && p != null) {

					// Validamos si escogen a cuphead
					if (j.personaje.equals("cuphead")) {

						image(cuphead, p.getX(), p.getY(), 80, 100);

					}

					// Validamos si esogen a mugman
					if (j.personaje.equals("mugman")) {

						image(mugman, p.getX(), p.getY(), 80, 100);

					}

				}

			}

			// for que recorre el arraylist de disparo
			if (tcp.getSesion().size() >= 2) {
				for (int i = 0; i < disparos.size(); i++) {

					Session J1 = tcp.getSesion().get(0);
					Session J2 = tcp.getSesion().get(1);

					Disparo disparoN = disparos.get(i);

					fill(255);
					ellipse(disparoN.getX(), disparoN.getY(), 20, 20);

					// Hacemos que las balas se muevan hacia arriba
					disparoN.setY(disparoN.getY() - disparoN.getVel());

					// Validamos que si la bala del jugador toca a la reina, esta resulta lastimada
					if (dist(disparoN.getX(), disparoN.getY(), reina.getPosX(), reina.getPosY()) < 80) {
						disparos.remove(i);

						vidaReina = vidaReina - 50;

						System.out.println(vidaReina);

					}
				}
			}

			// for para pintar las vidas
			for (int j = 0; j < vidas.size(); j++) {
				ellipse(100 * j, 50, 20, 20);
			}

			// metodo que valida que el jugador pierda vida cuando es golpeado por ataque de
			// la reina
			validarAtaqueEnemigo();

			// Validamos cuando el jugador pierda todas sus vidas
			if (vidas.size() == 0) {
				System.out.println("FIN DEL JUEGOOOOOOOOOOOOOOO!");

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
	public void validarAtaqueEnemigo() {

		for (int i = 0; i < reina.enemigos.size(); i++) {

			if (posicion != null) {
				if (dist(reina.enemigos.get(i).getPosX() + 15, reina.enemigos.get(i).getPosY() + 15,
						posicion.getX() + 40, posicion.getY() + 50) < 20) {

					// Eliminamos el enemigo que toque al jugador
					reina.enemigos.remove(reina.enemigos.get(i));

					// quitamos las vida del jugador
					if (vidas.size() > 0) {
						vidas.remove(vidas.size() - 1);
					}

				}
			}

		}

	}

	// OnMessageListener

	public void messageVida(Vida vida) {
		this.vida = vida;

		vidas.add(new Vida(vida.getVidas()));
		vidas.add(new Vida(vida.getVidas()));
		vidas.add(new Vida(vida.getVidas()));
		System.out.println("Hay : " + vidas.size() + "vidas");

	}

	public void messagePosicion(model.Posicion posicion) {
		this.posicion = posicion;

	}

	public void messageDisparo(model.Disparo disparo) {
		disparos.add(disparo);

	}

	@Override
	public void messageJugador(Jugador jugador) {
		// TODO Auto-generated method stub

	}

}
