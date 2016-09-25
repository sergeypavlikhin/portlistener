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

	public static volatile FileWriter writer;
	public static ServerSocket server;
	private static ParamParserResult parsedParams;												//Contains entered params (like -p)
	
	private final static Logger log = Logger.getLogger(PortListenerServer.class);
	
	public static void main(String[] args) {
		try{
			tryPrepareServer(args);
			startServer();
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	private static void tryPrepareServer(String[] args) {
		try {
			prepareFilewriter();
			prepareServer();
			parseParams(args);			
		} catch (Exception e) {
			throw new RuntimeException("Error while starting server", e);
		}
	}

	private static void startServer() {		
		log.info(String.format("Server just started on %s:%s...", Utils.DEFAULT_HOST, parsedParams.getPort()));
		final ExecutorService pool = Executors.newCachedThreadPool();
		while(true){
			try{
				Socket connectedSocket = server.accept();
				ClientThread clientThread = new ClientThread(writer, connectedSocket);			//For every client create ClientThread which implements Runnable			
				pool.submit(clientThread);
			}catch (IOException e) {
				log.error("Error while starting server", e);
			}			
		}
	}

	private static void parseParams(String[] args) {
		parsedParams = ParamsParser.parseArguments(args);
		if(parsedParams.isEmpty()){															//If the user hadn't entered params
			System.out.println("Server will be start with default params. ");
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
