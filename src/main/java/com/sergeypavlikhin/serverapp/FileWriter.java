package com.sergeypavlikhin.serverapp;

import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class FileWriter {

	private final String FILENAME		= "messages.xml";
	private final String ROOT_ELEMENT 	= "messages";
	private final String ELEMENT 		= "message";
	private final String ATTR_VALUE 	= "value";
	private final String ATTR_CLIENT 	= "client";
	
	private final Thread thread = new Thread(new Saver());
	
	private final ConcurrentLinkedQueue<ClientData> messages = new ConcurrentLinkedQueue<ClientData>();
	
	private final static Logger log = Logger.getLogger(FileWriter.class);
	
	private Document document;
	private Element root;
		
	public FileWriter(){
		createDocument();
		thread.start();
	}
	
	public void createDocument(){

		try {			
			DocumentBuilder builder = createDocumentBuilder();			
			prepareDocument(builder);
			updateFile();
		} catch (Exception e) {
			throw new RuntimeException("Error while created XML-file. Message: " + e.getMessage());
		}
				
	}

	private void prepareDocument(DocumentBuilder builder) {
		document = builder.newDocument();		
		root = document.createElement(ROOT_ELEMENT);
	}
	
	public void persistClientData(ClientData clientData){
		messages.add(clientData);
	}
	
	private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException{
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
	}
	
	private void updateFile() throws Exception{
		Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        
        FileOutputStream outputSteam = new FileOutputStream(FILENAME);        
		
        tr.transform(new DOMSource(root), new StreamResult(outputSteam));
		
        outputSteam.close();
	}
	
	class Saver implements Runnable{

		public void run() {
			
			while(true){
				
				if(!messages.isEmpty()){
					ClientData clientData = messages.poll();
					
					log.info("Message from [" + clientData.getClientName() + "] : " + clientData.getData());
					
					try {
						Element element = createChildElement(clientData);
						if(element != null){
							appendToRoot(element);
							updateFile();
						}
					} catch (Exception e) {
						log.error("Error while save data in file", e);
					}
				}
			}
		}
		
		private Element createChildElement(ClientData clientData){
			String data = clientData.getData();
			
			if(data.trim().isEmpty()) return null;
			
			Element result = document.createElement(ELEMENT);
			result.setAttribute(ATTR_VALUE, clientData.getData());
			result.setAttribute(ATTR_CLIENT, clientData.getClientName());
			return result;
		}
		
		private void appendToRoot(Element element){
			root.appendChild(element);
		}
		
	}
	
}
