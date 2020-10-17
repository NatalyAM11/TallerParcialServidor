package main;

import java.util.ArrayList;
import main.Enemigo;

import processing.core.PApplet;


public class ReinaDulce {
		
		//posicioines y velocidad
		int posX,posY,vel,vida;
		
		
		PApplet app;
		
		//El ataque de la reina es enviar enemigos que le quiten vida a los jugadores
		//por eso creamos aqui mismo el arraylist de enemigos porque ella es la que tiene el metodo de ataque
		ArrayList <Enemigo> enemigos;
		
		Enemigo ene;
		
		
		
	public ReinaDulce(PApplet app, int posX,int posY, int vel, int vida) {
		
		this.posX=posX;
		this.posY=posY;
		this.vel=vel;
		this.vida=vida;
		this.app=app;
		
		//arraylist de los enemigos del ataque
		enemigos=new ArrayList<Enemigo>();
		
		//metodo de ataque que añade enemigos al arraylist
		ataque();
		
		
	}	
	
	
	public void pintar() {
		app.fill(255);
		app.ellipse(this.posX, this.posY,100,100);
		
		//pintamos y movemos los enemigos del ataque de la reina
		for (int i = 0;i<enemigos.size(); i++) {
			enemigos.get(i).pintar();
			enemigos.get(i).movimiento();
		}
		
		
		
	}
	
	
	
	public void movimiento() {
		
		//sumamos a la posicion para que se mueva
		this.posX += this.vel;
		
		//Validamos el espacio por el que se va a mover la reina
		if(this.posX >= 600) {
			this.vel *= -1;	
		}
		
		if(this.posX<=200) {
			this.vel *= -1;
		}
	}
	
	
	
	
	public void ataque() {
		
		new Thread(
				()->{
					
					while(true) {
						//Añadimos un enemigo al arralist cada 5 seg, es decir cada 5seg la reina atacara
						ene= new Enemigo(app,this.posX,this.posY);
						enemigos.add(ene);
						System.out.println(enemigos.size());
						
						
						  try {
                              Thread.sleep(3000);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
					}
				}
				
				).start();
		
		
	
	}
	
	
	
	
	
	//getters y setters

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


	public int getVida() {
		return vida;
	}


	public void setVida(int vida) {
		this.vida = vida;
	}


	public PApplet getApp() {
		return app;
	}


	public void setApp(PApplet app) {
		this.app = app;
	}


	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}


	public void setEnemigos(ArrayList<Enemigo> enemigos) {
		this.enemigos = enemigos;
	}
	

	
	}


