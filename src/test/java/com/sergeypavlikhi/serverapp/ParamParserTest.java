package com.sergeypavlikhi.serverapp;

import org.junit.Test;

import com.sergeypavlikhin.serverapp.params.ParamParserResult;
import com.sergeypavlikhin.serverapp.params.ParamsParser;

import junit.framework.Assert;

public class ParamParserTest {
	
	@Test
	public void simpleInit(){
		
		String[] args = new String[]{
				"-p", "3311"
		};
		
		ParamsParser parser = new ParamsParser(args);		
		Assert.assertNotNull(parser);
		
	}
	@Test
	public void parseCorrectLine(){
		
		Integer port = 3311;
		
		String[] args = new String[]{
				"-p", String.valueOf(port)
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertEquals(port, result.getPort());
		
	}
	@Test
	public void parseIncorrectLineWithBadParam(){
		
		int port = 3311;
		
		String[] args = new String[]{
				"-u", String.valueOf(port)
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertFalse(result.isAllOk());
		
	}
	@Test
	public void parseIncorrectLineWithStringPort(){
		
		String[] args = new String[]{
				"-p", "someport"
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertFalse(result.isAllOk());
		
	}
	
	/**
	 * Warning! Hardcoded error message. Need to load error message from file or something else.
	 */
	@Test
	public void parseIncorrectLineWithStringPortMessage(){
		
		String[] args = new String[]{
				"-p", "someport"
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertTrue(result.getMessages().contains("The port must be a number"));
		
	}
	@Test
	public void emptyArgs(){
		
		String[] args = new String[]{};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertTrue(result.isAllOk());
		
	}
	@Test
	public void oneRecodnizedArg(){
		
		String[] args = new String[]{
				"-p"
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertTrue(result.getMessages().contains("The port must be a number"));
		
	}
	@Test
	public void randomString(){
		
		String[] args = new String[]{
				"dsadsda dsadm 22313 3213m 13o213m12 ml;dmasl;dm"
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertTrue(result.isEmpty());
		
	}
}
