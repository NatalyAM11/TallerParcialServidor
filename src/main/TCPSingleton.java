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
import java.util.ArrayList;

import model.Disparo;
import model.Jugador;
import model.Posicion;
import model.Vida;

import com.google.gson.Gson;

public class TCPSingleton extends Thread {

	private static TCPSingleton unicaInstancia;
	
	private int cantidad;

	// Socket
	private ServerSocket server;
	private ArrayList <Session> sesion;

	private TCPSingleton() {
		
		

	}

	// Instance
	public static TCPSingleton getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new TCPSingleton();
			unicaInstancia.start();
		}
		return unicaInstancia;
	}

	// Observer
	private OnMessageListener observer;

	public void setObserver(OnMessageListener observer) {
		this.observer = observer;
	}

	// Hilo conexion
	public void run() {

		try {

			// Conexión
			server = new ServerSocket(5000);

			
			cantidad = sesion.size();
			
			while (true) {

				System.out.println("Esperando conexión");
				Socket socket = server.accept();
				Session session = new Session(socket);
				session.setObserver(observer);
				session.start();
				sesion.add(session);
				System.out.println("Conectado a el usuario dentro de sesion");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Session> getSesion() {
		return sesion;
	}
	
}