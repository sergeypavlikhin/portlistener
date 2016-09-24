package com.sergeypavlikhin.serverapp;

import java.net.Socket;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;


public class ClientThread implements Runnable{

	private FileWriter writer;
	private ClientCallable callable;	

	private final static Logger log = Logger.getLogger(ClientThread.class);
	
	public ClientThread (FileWriter writer, Socket connectedSocket){
		String connectedAdress = connectedSocket.getRemoteSocketAddress().toString();
		this.writer = writer;
		this.callable = new ClientCallable(connectedAdress, connectedSocket);				
	}
	
	public void run() {
		boolean isRunning = true;
		while(isRunning){
			try {				
				
				Future<String> task 	= getTask();
				ClientData clientData 	= new ClientData(task.get(), callable.getName());
				
				writer.persistClientData(clientData);								
			
			} catch (Exception e) {
				log.error("Error while process getting client data", e);
				isRunning = false;
			}
		}
	}

	private FutureTask<String> getTask() {
		FutureTask<String> task = new FutureTask<String>(callable);
		Thread thread = new Thread(task);
		thread.start();
		return task;
	}

}
