package com.sergeypavlikhin.serverapp;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;


public class ClientCallable implements Callable<String>{

	private String name;
	private Socket socket;
	private final static Logger log = Logger.getLogger(ClientCallable.class);
	
	public ClientCallable (String name, Socket socket){
		this.setName(name);
		this.socket = socket;
		log.info(String.format("The client [%s] is connected", name));
	}
	

	public String call() throws Exception {
		DataInputStream input = new DataInputStream(socket.getInputStream());
		if(input.available() > 0){
			return input.readUTF();
		}else{
			return "";	
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}


	public boolean isNotClosed() {
		return !socket.isClosed();
	}
}
