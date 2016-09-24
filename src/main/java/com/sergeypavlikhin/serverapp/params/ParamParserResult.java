package com.sergeypavlikhin.serverapp.params;

import java.util.ArrayList;
import java.util.List;

import com.sergeypavlikhin.serverapp.ServerMode;

public class ParamParserResult {

	private Integer port = null;
	private ServerMode mode = null;
	private List<String> messages = new ArrayList<>();
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
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
	public boolean isAllOk(){
		return messages.isEmpty();
	}
	public boolean isEmpty(){
		return port == null && mode == null;
	}
}
