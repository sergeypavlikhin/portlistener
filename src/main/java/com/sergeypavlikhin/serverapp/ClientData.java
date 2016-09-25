package com.sergeypavlikhin.serverapp;

import java.util.List;

public class ClientData {

	private List<Byte> data;
	private String clientName;
	
	public ClientData(List<Byte> data, String clientName) {
		this.data = data;
		this.clientName = clientName;
	}

	public List<Byte> getData() {
		return data;
	}

	public void setData(List<Byte> data) {
		this.data = data;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	
}
