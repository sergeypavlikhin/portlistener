package com.sergeypavlikhin.serverapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class PortListenerServer {

	private static final int 	DEFAULT_PORT = 3311;
	private static final String DEFAULT_HOST = "localhost";
	
	private static FileWriter writer;
	private static ServerSocket server;
	
	private final static Logger LOG = Logger.getLogger(PortListenerServer.class);
	
	public static void main(String[] args) {
		LOG.debug("Server just started...");
					
		try {
			prepareFilewriter();
			prepareServer();
			
			final ExecutorService pool = Executors.newCachedThreadPool();
			
			while(true){
				Socket connectedSocket = server.accept();
				
				String connectedAdress = connectedSocket.getRemoteSocketAddress().toString();
				
				ClientCallable clientCallable 	= new ClientCallable(connectedAdress, connectedSocket);				
				ClientThread clientThread 		= new ClientThread(writer, clientCallable);

				pool.submit(clientThread);
			}
			
		} catch (Exception e) {
			LOG.error("Error while starting server", e);
		}
	}


	private static void prepareServer() throws IOException, UnknownHostException {
		server = new ServerSocket(DEFAULT_PORT, 1024, InetAddress.getByName(DEFAULT_HOST));
	}

	
	private static void prepareFilewriter() {
		writer = new FileWriter();
	}

}
