package com.mryensei.core;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.mryensei.view.ViewChat;

public class Servidor implements Runnable {
	private Socket socket;
	private ServerSocket serverSocket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private int puerto;
	
	private ViewChat viewChat;
	
	public Servidor(int port, ViewChat view){
		this.puerto = port;
		this.viewChat= view;
	}

	@Override
	public void run() {
		try {			
			serverSocket = new ServerSocket(puerto);
			socket = serverSocket.accept();
			output = new ObjectOutputStream(socket.getOutputStream());
			//output.writeObject("Bienvenido Cliente");
			
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println("Servidor iniciado exitosamente en el PORT: "+puerto);
			viewChat.getTextAreaMensajes().append("\nServidor iniciado exitosamente en el PORT: "+puerto);
			this.readLine();
			
			input.close();
			output.close();	
			
		} catch (IOException e) {		
			this.closeServer();
			e.printStackTrace();
		}
		
	}
	
	
	public void writeLine(String linea){
		try {
			output.writeObject(linea);
			
		} catch (IOException e) {
			
		}
	}
	
	public void readLine(){
		try {
			//Ciclo infinito
			//lectura permanente
			//while(true){				
				Object obj = input.readObject();
				if(obj!=null && obj instanceof String ){
					viewChat.getTextAreaMensajes().append("\nCliente dice: "+(String)obj);
				}
				Thread.sleep(500);	//milisegundos
			//}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeServer(){
		try {
			//Cierre de conexiones					
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
