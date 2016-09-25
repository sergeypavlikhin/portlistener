package com.sergeypavlikhin.serverapp;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;


public class ClientCallable implements Callable<List<Byte>>{

	private String name;
	private Socket socket;
	private final static Logger log = Logger.getLogger(ClientCallable.class);
	
	public ClientCallable (String name, Socket socket){
		this.setName(name);
		this.socket = socket;
		log.info(String.format("The client [%s] is connected", name));
	}
	

	public List<Byte> call() throws Exception {
		List<Byte> bytes = new ArrayList<>();
		byte[] buffer = null;
		DataInputStream inputStream = new DataInputStream(socket.getInputStream());
		while(inputStream.available() > 0){
			
			buffer = new byte[1024];
			inputStream.read(buffer);
			for (byte b : buffer) {
				bytes.add(b);
			}
		}
		
		return bytes;
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
