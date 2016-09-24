package com.sergeypavlikhin.serverapp.params;

import java.util.ArrayList;
import java.util.List;

import com.sergeypavlikhin.serverapp.ServerMode;

public class ParamParserResult {

	private int port;
	private ServerMode mode;
	private List<String> messages = new ArrayList<>();
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public ServerMode getMode() {
		return mode;
	}
	public void setMode(ServerMode mode) {
		this.mode = mode;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
