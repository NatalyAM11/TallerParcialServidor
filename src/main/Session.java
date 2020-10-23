package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.Gson;

import model.Disparo;
import model.Jugador;
import model.Posicion;
import model.Vida;

public class Session extends Thread {

	private String id;
	private BufferedReader reader;
	private Socket socket;
	private OnMessageListener observer;
	
	//jugador
		Jugador jugador;
		
		//posicion
		Posicion posicion;
		
		//disparo
		Disparo disparo;
		
		//vida
		Vida vida;
		
		
		Gson gson;

	public Session(Socket socket) {
		super();
		this.socket = socket;
		this.id = UUID.randomUUID().toString();
		
	}

	@Override
	public void run() {
		
		try {
			
			//Input Output
			OutputStream os= socket.getOutputStream();
			InputStream is=socket.getInputStream();
			
			
			//Writer reader
			reader= new BufferedReader(new InputStreamReader(is));
			
			
			//Recepcion de datos
			while(true) {
		    	
		    	//System.out.println("Esperando mensaje");
		
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
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setObserver(OnMessageListener observer) {
		// TODO Auto-generated method stub
		
		this.observer = observer;
		
	}
	
	public String getID() {
		return this.id;
	}

}