package com.sergeypavlikhin.serverapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class FileWriter {

	private final String FILENAME		= "messages.xml";
	private final String ROOT_ELEMENT 	= "messages";
	private final String ELEMENT 		= "message";
	private final String ATTR_VALUE 	= "value";
	private final String ATTR_CLIENT 	= "client";
	
	private final Thread thread = new Thread(new FileSaver());
	
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
			
			createFileIfNessasury();
			
			updateFile();
		} catch (Exception e) {
			throw new RuntimeException("Error while created XML-file. Message: " + e.getMessage());
		}
				
	}

	private String getPath() throws URISyntaxException{		
		return System.getProperty("user.home");
	}
	
	private void createFileIfNessasury() throws IOException, URISyntaxException {
		File existedFile = new File(getPath(), FILENAME);
		if(!existedFile.exists())
			existedFile.createNewFile();
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

        FileOutputStream outputSteam = new FileOutputStream(getPath()  + "/" + FILENAME);
		transformDocument(outputSteam);
        outputSteam.close();
	}

	
	/**
	 * Add indent, set document type
	 * @param outputSteam
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private void transformDocument(FileOutputStream outputSteam)
			throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tr.transform(new DOMSource(root), new StreamResult(outputSteam));
	}
	
	
	/**
	 * FileSaver checks queue. Save in file when queue contains any object
	 * 
	 */
	class FileSaver implements Runnable{

		public void run() {
			
			while(true){
				
				if(!messages.isEmpty()){
					ClientData clientData = messages.poll();
					
					try {
						Element element = createChildElement(clientData);
						if(element != null){
							log.info("Message from [" + clientData.getClientName() + "] : " + clientData.getData());
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
			List<Byte> data = clientData.getData();
			
			if(data.isEmpty()) return null;
			
			Element result = document.createElement(ELEMENT);
			result.setAttribute(ATTR_VALUE, StringUtils.join(data.toArray()));
			result.setAttribute(ATTR_CLIENT, clientData.getClientName());
			return result;
		}
		
		private void appendToRoot(Element element){
			root.appendChild(element);
		}
		
	}
	
}
