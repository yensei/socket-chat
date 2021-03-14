package com.mryensei.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mryensei.view.ViewChat;

public class Cliente implements Runnable {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private ViewChat viewChat;
	
	private String host;
	private int port;

	
	public Cliente(String host, int port, ViewChat view) {
		super();

		this.viewChat = view;
		this.host = host;
		this.port = port;
	}


	
	@Override
	public void run() {
		try {
			socket = new Socket(host, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println("Cliente conectado exitosamente al HOST: "+host+" -- PORT: "+port);
			
			viewChat.getTextAreaMensajes().append("\nCliente conectado exitosamente al HOST: "+host+" -- PORT: "+port);
			this.readLine();
			//System.out.println("Recibido desde el Servidor: "+ (String) input.readObject());				
			
			
	/*	}catch (ClassNotFoundException e) {				
			e.printStackTrace();*/
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			this.closeConnection();
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
		Object obj;
		try {
			//Ciclo infinito
			//lectura permanente
			//while(true){				
				obj = input.readObject();
				if(obj!=null && obj instanceof String ){
					viewChat.getTextAreaMensajes().append("\nServidor dice: "+(String)obj);
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


	public void closeConnection(){
		try{
		//Cierre de Conexiones
		socket.close();
		input.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	
}
