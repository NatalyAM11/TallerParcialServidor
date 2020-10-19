package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import model.Disparo;
import model.Jugador;
import model.Posicion;
import model.Vida;

import com.google.gson.Gson;

public class TCPSingleton extends Thread {
	
	private static TCPSingleton unicaInstancia;
	
	//Socket
	private ServerSocket server;
	
	//jugador
	Jugador jugador;
	
	//posicion
	Posicion posicion;
	
	//disparo
	Disparo disparo;
	
	//vida
	Vida vida;
	

	
	private TCPSingleton () {
		
	}
	
	
	//Instance
		public static TCPSingleton getInstance() {
			if (unicaInstancia==null) {
				unicaInstancia= new TCPSingleton();
				unicaInstancia.start();
			}
			return unicaInstancia;
		}
		
	
	
	//writer and reader
	BufferedWriter writer;
	BufferedReader reader;
	
	
	Gson gson;
	
	
	
	//Observer
	private OnMessageListener observer;
	
	public void setObserver(OnMessageListener observer) {
		this.observer=observer;
	}
	
	

	
	//Hilo conexion
	public void run () {
		
		try {
			
			//Conexión
			server = new ServerSocket(5000);
			System.out.println("Esperando conexión");
			Socket socket= server.accept();
			System.out.println("Conectado con el usuario");
			
			
			//Input Output
			OutputStream os= socket.getOutputStream();
			InputStream is=socket.getInputStream();
			
			
			//Writer reader
			writer=new BufferedWriter(new OutputStreamWriter(os));
			reader= new BufferedReader(new InputStreamReader(is));
			
			
			//Recibo el mensaje 
		    recibir();
		   		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
	
	
	//Metodo recepcion mensajes
	public void recibir() {
		
		
		new Thread (
				()->{
					
					try {
				    while(true) {
				    	
				    	System.out.println("Esperando mensaje");
				
				    	//Recibimos el mensaje
				    	String mensaje;
						mensaje = reader.readLine();
						
						//gson para deserializar el mensaje
						Gson gson=new Gson();
						Generic generic= gson.fromJson(mensaje, Generic.class);
						
						
						//Se deserializa segun el tipo de dato que se envio
						if(mensaje!=null) {
						switch(generic.type) {
						
						case "Jugador": 
							jugador=gson.fromJson(mensaje, Jugador.class);
							
							observer.messageJugador(jugador);
							
							System.out.println(jugador.personaje);
							break;
							
						case "Posicion": 
							posicion=gson.fromJson(mensaje, Posicion.class);
							
							observer.messagePosicion(posicion);
							
							System.out.println(posicion.getX());
							break;
							
						case "Disparo": 
							disparo=gson.fromJson(mensaje, Disparo.class);
							
							observer.messageDisparo(disparo);
							
							break;
					
						case "Vida":
							vida=gson.fromJson(mensaje, Vida.class);
							observer.messageVida(vida);
							break;
							
						}
				       
				    }
						//System.out.println(mensaje);
				    
				    }
				    
				    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				).start();
	
		
	}

	

	
}