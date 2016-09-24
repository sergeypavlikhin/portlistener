package com.sergeypavlikhin.serverapp;

public class Utils {

	public static final ServerMode 	DEFAULT_MODE = ServerMode.TCP;
	public static final int 		DEFAULT_PORT = 3311;
	public static final String 		DEFAULT_HOST = "localhost";
	
	public static void println(String message) {
		System.out.println(message);
	}
	public static void print(String message) {
		System.out.print(message);
	}
}
