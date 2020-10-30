package main;

import processing.core.PApplet;
import processing.core.PImage;

public class Enemigo {

	int posX, posY, vel, dirX, dirY;
	PApplet app;
	PImage enemigo;

	public Enemigo(PApplet app, int posX, int posY) {

		// datos enemigo
		this.app = app;
		this.posX = posX;
		this.posY = posY;
		this.vel = 6;
		this.dirX = 1;
		this.dirY = 1;

		// imagen
		enemigo = app.loadImage("img/enemigoUno.png");
	}

	public void pintar() {
		app.fill(255, 0, 0);

		app.image(enemigo, this.posX, this.posY,30,30);

	}

	public void movimiento() {
		// Movemos los enemigos lanzados por la reina aleatoriamente por toda la
		// pantalla

		this.posY += vel * this.dirY;

	}

	// Getters and setters
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public int getDirX() {
		return dirX;
	}

	public void setDirX(int dirX) {
		this.dirX = dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public void setDirY(int dirY) {
		this.dirY = dirY;
	}

	public PApplet getApp() {
		return app;
	}

	public void setApp(PApplet app) {
		this.app = app;
	}

}
