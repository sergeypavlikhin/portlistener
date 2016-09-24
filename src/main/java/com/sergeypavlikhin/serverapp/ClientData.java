package com.sergeypavlikhin.serverapp;

import org.apache.commons.lang3.StringEscapeUtils;

public class ClientData {

	private String data;
	private String clientName;
	
	public ClientData(String data, String clientName) {
		this.data = StringEscapeUtils.escapeJava(data);
		this.clientName = clientName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	
}
