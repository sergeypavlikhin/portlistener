package com.sergeypavlikhin.serverapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.sergeypavlikhin.serverapp.params.ParamParserResult;
import com.sergeypavlikhin.serverapp.params.ParamsParser;

public class PortListenerServer {

	public static FileWriter writer;
	public static ServerSocket server;
	private static ParamParserResult parsedParams;
	
	private final static Logger LOG = Logger.getLogger(PortListenerServer.class);
	
	public static void main(String[] args) {
		
		try {
			prepareFilewriter();
			prepareServer();
			parseParams(args);			
			startServer();
			
		} catch (Exception e) {
			LOG.error("Error while starting server", e);
		}
	}


	private static void startServer() throws IOException {
		LOG.info(String.format("Server just started on %s:%s...", Utils.DEFAULT_HOST, parsedParams.getPort()));
		final ExecutorService pool = Executors.newCachedThreadPool();
		
		while(true){
			Socket connectedSocket = server.accept();
			
			String connectedAdress = connectedSocket.getRemoteSocketAddress().toString();
			
			ClientCallable clientCallable 	= new ClientCallable(connectedAdress, connectedSocket);				
			ClientThread clientThread 		= new ClientThread(writer, clientCallable);

			pool.submit(clientThread);
		}
	}


	private static void parseParams(String[] args) {
		parsedParams = ParamsParser.parseArguments(args);
		if(parsedParams.isEmpty()){
			LOG.info("Server will be start with default params");
			parsedParams.setMode(Utils.DEFAULT_MODE);
			parsedParams.setPort(Utils.DEFAULT_PORT);
		}else{ 
			if(!parsedParams.isAllOk()){
				for (String string : parsedParams.getMessages()) {
					System.out.println("Error while parsing params: " + string);
				}
			}	
		}
	}


	private static void prepareServer() throws IOException, UnknownHostException {
		server = new ServerSocket(Utils.DEFAULT_PORT, 1024, InetAddress.getByName(Utils.DEFAULT_HOST));
	}

	
	private static void prepareFilewriter() {
		writer = new FileWriter();
	}

}
