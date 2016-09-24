package com.sergeypavlikhi.serverapp;

import org.junit.Test;

import com.sergeypavlikhin.serverapp.params.ParamParserResult;
import com.sergeypavlikhin.serverapp.params.ParamsParser;

import junit.framework.Assert;

public class ParamParserTest {
	
	
	
	@Test
	public void test_testInit(){
		
		String[] args = new String[]{
				"-p", "3311"
		};
		
		ParamsParser parser = new ParamsParser(args);		
		Assert.assertNotNull(parser);
		
	}
	@Test
	public void test_testParse(){
		
		int port = 3311;
		
		String[] args = new String[]{
				"-u", String.valueOf(port)
		};
		
		ParamsParser parser 		= new ParamsParser(args);	
		ParamParserResult result 	= parser.getResult();
		
		Assert.assertEquals(result.getPort(), port);
		
	}
}
