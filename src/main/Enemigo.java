package main;

import processing.core.PApplet;

public class Enemigo {

	int posX,posY,vel,dirX,dirY;
	PApplet app;
	
	public Enemigo(PApplet app,int posX,int posY) {
	
	this.app=app;
	this.posX=posX;
	this.posY=posY;
	this.vel=6;
	this.dirX=1;
	this.dirY=1;
}	
	
	public void pintar () {
		app.fill(255,0,0);
		app.ellipse(this.posX, this.posY,30,30);
	}
	
	
	public void movimiento() {
	//Movemos los enemigos lanzados por la reina aleatoriamente por toda la pantalla
	
	//this.posX -= vel * this.dirX;
	//this.dirX *= -1;
	this.posY += vel * this.dirY;



	//Validamos que los enemigos lanzados por la reina no se salgan de la pantalla
		    
	/*if (this.posX >= 775 || this.posX <= 0) {
		 this.dirX *= -1;
	}
	 
	 
	if (this.posY >= 475|| this.posY <= 0) {
	 this.dirY *= -1;
	}*/
	
	
	}
	
	
	//Getters and setters
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
