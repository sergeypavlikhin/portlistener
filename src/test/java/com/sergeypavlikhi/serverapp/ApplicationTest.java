package com.sergeypavlikhi.serverapp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sergeypavlikhin.serverapp.PortListenerServer;

public class ApplicationTest {
	
	private static final Integer port = 3311;
	private static Thread mainThread = null;
	
	@BeforeClass
	public static void prepare() throws InterruptedException{
		// Create thread for safe-run application
		 mainThread = new Thread(new Runnable() {
			@Override
			public void run() {
				PortListenerServer.main(new String[] { "-p", String.valueOf(port)});
			}
		});

		mainThread.setDaemon(false);
		mainThread.start();

		// Sleep for setting up application
		Thread.currentThread().sleep(1000);

	}
	
	
	@Test
	public void checkConnection() throws UnknownHostException, IOException, InterruptedException, ExecutionException{
		
		final Integer port 		= this.port;
		final String address 	= "localhost";
		final String paramKey 	= "-p";
		
				
		//Establish connection
		Socket socket = new Socket(address, port);
		boolean isConnected = socket.isConnected();
				
		Assert.assertTrue(isConnected);
		
	}
	
	@Test
	public void checkConnectionClosed() throws UnknownHostException, IOException, InterruptedException, ExecutionException{
		
		final Integer port 		= this.port;
		final String address 	= "localhost";
		final String paramKey 	= "-p";
						
		//Establish connection
		Socket socket = new Socket(address, port);
		socket.close();		
		
		Assert.assertFalse(socket.isConnected());
		
	}
	
	@AfterClass
	public static void destroy(){
		mainThread.interrupt();
	}
}
