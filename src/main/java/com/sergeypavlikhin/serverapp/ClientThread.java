package com.sergeypavlikhin.serverapp;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;


public class ClientThread implements Runnable{

	private FileWriter writer;
	private ClientCallable callable;	

	private final static Logger log = Logger.getLogger(ClientThread.class);
	
	public ClientThread (FileWriter writer, ClientCallable callable){
		this.writer = writer;
		this.callable = callable;
	}
	
	public void run() {
		boolean isRunning = true;
		while(isRunning){
			try {				
				Future<String> task = getTask();
				writer.persistClientData(new ClientData(task.get(), callable.getName()));								
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
