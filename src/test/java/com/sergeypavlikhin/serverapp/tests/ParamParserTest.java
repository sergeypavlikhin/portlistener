package com.sergeypavlikhin.serverapp.tests;

import org.junit.Assert;
import org.junit.Test;

import com.sergeypavlikhin.serverapp.params.ParamParserResult;
import com.sergeypavlikhin.serverapp.params.ParamsParser;


public class ParamParserTest {
	
	
	@Test
	public void parseCorrectLine(){
		
		Integer port = 3311;
		
		String[] args = new String[]{
				"-p", String.valueOf(port)
		};
			
		ParamParserResult result 	= ParamsParser.parseArguments(args);		
		Assert.assertEquals(port, result.getPort());
		
	}
	
	@Test
	public void parseIncorrectLineWithBadParam(){
		
		int port = 3311;
		
		String[] args = new String[]{
				"-u", String.valueOf(port)
		};
			
		ParamParserResult result 	= ParamsParser.parseArguments(args);		
		Assert.assertFalse(result.isAllOk());
		
	}
	
	@Test
	public void parseIncorrectLineWithStringPort(){
		
		String[] args = new String[]{
				"-p", "someport"
		};
		
		ParamParserResult result 	= ParamsParser.parseArguments(args);		
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
			
		ParamParserResult result 	= ParamsParser.parseArguments(args);
		Assert.assertTrue(result.getMessages().contains("The port must be a number"));
		
	}
	@Test
	public void emptyArgs(){
		
		String[] args = new String[]{};
		
		ParamParserResult result 	= ParamsParser.parseArguments(args);
		Assert.assertTrue(result.isAllOk());
		
	}
	@Test
	public void oneRecodnizedArg(){
		
		String[] args = new String[]{
				"-p"
		};
			
		ParamParserResult result 	= ParamsParser.parseArguments(args);
		Assert.assertTrue(result.getMessages().contains("The port must be a number"));
		
	}
	@Test
	public void randomString(){
		
		String[] args = new String[]{
				"dsadsda dsadm 22313 3213m 13o213m12 ml;dmasl;dm"
		};
		
		ParamParserResult result 	= ParamsParser.parseArguments(args);		
		Assert.assertTrue(result.isEmpty());
		
	}
}
