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
	PImage cuphead, mugman, balaJugador;

	// pantallas
	PImage pLogo, pIntro, pExpli, pGameOver, fondoJuego, pGanoJ1, pGanoJ2, pGananTodos, pInstrucciones;

	// iconos y botone
	PImage vidaR, vidaA, puntajeA, puntajeR, bEmpezar, bEmpezarP, bJugar, bJugarP, bInstrucciones, bFin, bFinP, bInstP,
			bVolver, bVolverP;

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

	Jugador j, idCambio;

	boolean perdio1, perdio2;

	boolean puedePasar1, puedePasar2;

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

		// Cargamos todas las imagenes del juego
		cuphead = loadImage("img/avionCupHead.png");
		mugman = loadImage("img/avionMugman.png");
		vidaR = loadImage("img/vidaR.png");
		vidaA = loadImage("img/vidaA.png");
		puntajeR = loadImage("img/puntajeR.png");
		puntajeA = loadImage("img/puntajeA.png");
		puntajeA = loadImage("img/puntajeA.png");
		pLogo = loadImage("img/pLogo.png");
		bEmpezar = loadImage("img/bEmpezar.png");
		bEmpezarP = loadImage("img/bEmpezarPrendido.png");
		bJugar = loadImage("img/bJugar.png");
		bJugarP = loadImage("img/bJugarPrendido.png");
		pIntro = loadImage("img/pIntro.png");
		pExpli = loadImage("img/pExpli.png");
		bInstrucciones = loadImage("img/bInstrucciones.png");
		bInstP = loadImage("img/bInstPrendido.png");
		pGameOver = loadImage("img/pGameOver.png");
		fondoJuego = loadImage("img/fondoJuego.png");
		pGanoJ1 = loadImage("img/pGanoJ1.png");
		pGanoJ2 = loadImage("img/pGanoJ2.png");
		pGananTodos = loadImage("img/pTodosGanan.png");
		bFin = loadImage("img/bFin.png");
		bFinP = loadImage("img/bFinP.png");
		balaJugador = loadImage("img/disparoQueen.png");
		pInstrucciones = loadImage("img/pInstrucciones.png");
		bVolver = loadImage("img/bVolver.png");
		bVolverP = loadImage("img/bVolverP.png");

		// variable que controla las pantallas
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

		perdio1 = false;
		perdio2 = false;

		puedePasar1 = false;
		puedePasar2 = false;

	}

	public void draw() {
		background(0);
		
		//No pasamos a la pentalla de juego hasta que elijan un jugador
		for (int x = 0; x < tcp.getSesion().size(); x++) {	
			
			idCambio = tcp.getSesion().get(x).jugador;
			
			if(idCambio != null) {
				
				pantalla = 3;
				
			}
		}


		// Validamos cuando el jugador 1 gana
		if (puntajeJ1 == 1000) {
			pantalla = 4;
			System.out.println("GANO EL J1");
		}

		// Validamos cuando el jugador 1 gana
		if (puntajeJ2 == 1000) {
			pantalla = 5;
			System.out.println("GANO EL J2");
		}

		// validamos cuando todos los jugadores pierden
		if (perdio1 == true && perdio2 == true) {
			pantalla = 6;
			System.out.println("perdieron todos");
		}

		switch (pantalla) {
		case 0:

			image(pLogo, 0, 0);
			image(bEmpezar, 325, 358);
			image(bInstrucciones, 280, 419);

			// Efecto del boton
			if ((mouseX > 325 && mouseX < 467) && (mouseY > 358 && mouseY < 400)) {
				image(bEmpezarP, 325, 358);
			}

			// Efecto del boton instrucciones
			if ((mouseX > 280 && mouseX < 517) && (mouseY > 419 && mouseY < 459)) {
				image(bInstP, 280, 419);
			}

			break;

		case 1:

			image(pIntro, 0, 0);
			image(bJugar, 325, 387);

			// Efecto del boton
			if ((mouseX > 325 && mouseX < 467) && (mouseY > 387 && mouseY < 430)) {
				image(bJugarP, 325, 387);
			}

			break;

		case 2:
			image(pExpli, 0, 0);
			break;

		// pantalla juego
		case 3:

			image(fondoJuego, 0, 0);

			// Enemigos
			reina.pintar();
			reina.movimiento();

			for (int i = 0; i < tcp.getSesion().size(); i++) {

				j = tcp.getSesion().get(i).jugador;
				Posicion p = tcp.getSesion().get(i).posicion;

				// Validar el ataque del enemigo a los personajes
				validarAtaqueEnemigo(j, p);

				if (j != null) {
					puedePasar1 = true;
					puedePasar2 = true;
				}

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
							text("El jugador 1 ha perdido, su puntaje es anulado", 255, 80);
							perdio1 = true;
							puntajeJ1 = 0;
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
							text("El jugador 2 ha perdido, su puntaje es anulado", 255, 80);
							puntajeJ2 = 0;
							perdio2 = true;
							System.out.println("Perdio el jugador 2");
						}
					}

				}

			}

			// Aqui validamos todo lo del disparo

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
				image(balaJugador, disparoN.getX(), disparoN.getY(), 20, 20);

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

			break;

		case 4:
			image(pGanoJ1, 0, 0);

			image(bFin, 258, 358);

			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				image(bFinP, 258, 358);
			}

			break;

		case 5:
			image(pGanoJ2, 0, 0);
			image(bFin, 258, 358);

			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				image(bFinP, 258, 358);
			}

			break;

		case 6:
			image(pGameOver, 0, 0);
			image(bFin, 258, 358);

			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				image(bFinP, 258, 358);
			}

			break;
		case 7:
			image(pGananTodos, 0, 0);
			break;

		case 8:
			image(pInstrucciones, 0, 0);

			image(bVolver, 28, 432);

			if ((mouseX > 28 && mouseX < 108) && (mouseY > 432 && mouseY < 464)) {
				image(bVolverP, 28, 432);
			}

			break;
		}

		text("x=" + mouseX + "y=" + mouseY, mouseX, mouseY);

	}

	// validamos cuando alguno de los ataques de la reina toque a los jugadores,
	// estos pierden vida
	public void validarAtaqueEnemigo(Jugador j, Posicion posicion) {

		for (int i = 0; i < reina.enemigos.size(); i++) {

			if (posicion != null) {

				if ((reina.enemigos.get(i).getPosX() + 15 < posicion.getX() + 80
						&& reina.enemigos.get(i).getPosX() + 15 > posicion.getX())
						&& (reina.enemigos.get(i).getPosY() + 30 > posicion.getY()
								&& reina.enemigos.get(i).getPosY() + 30 < posicion.getY() + 50)) {

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

	public void mousePressed() {
		switch (pantalla) {
		case 0:
			// Pasamos a la pantalla de intro
			if ((mouseX > 325 && mouseX < 467) && (mouseY > 358 && mouseY < 400)) {
				pantalla = 1;
			}

			// Pasamos a la pantalla de instrucciones
			if ((mouseX > 280 && mouseX < 517) && (mouseY > 419 && mouseY < 459)) {
				pantalla = 8;
			}
			break;

		case 1:

			// Pasamos a la pantalla de instrucciones
			if ((mouseX > 325 && mouseX < 467) && (mouseY > 387 && mouseY < 430)) {
				pantalla = 2;
			}

			break;

		case 4:
			// salimos del juego
			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				exit();
			}

			break;

		case 5:
			// salimos del juego
			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				exit();
			}

			break;

		case 6:
			// salimos del juego
			if ((mouseX > 258 && mouseX < 564) && (mouseY > 358 && mouseY < 409)) {
				exit();
			}

			break;

		case 8:
			// nos devolvemos a la pantalla de inicio
			if ((mouseX > 28 && mouseX < 108) && (mouseY > 432 && mouseY < 464)) {
				pantalla = 0;
			}

			break;

		}

	}

}
