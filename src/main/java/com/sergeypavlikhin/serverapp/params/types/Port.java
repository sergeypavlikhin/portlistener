package com.sergeypavlikhin.serverapp.params.types;

import com.sergeypavlikhin.serverapp.params.Param;
import com.sergeypavlikhin.serverapp.params.ParamParserResult;

public class Port extends AbstractParamType{

	private final int MAX_PORT = 65535;
	private final int MIN_PORT = 0;
	
	@Override
	public void parse(ParamParserResult argParserResult, Param param) {
		Integer port;
		String enteredPort = param.getValue();
		
		if(enteredPort!= null){
			if(!enteredPort.trim().matches("^[0-9]+$")){
				throw new IllegalArgumentException("The port must be a number");
			}
			
			port = Integer.parseInt(param.getValue());		
			
			if(port > MAX_PORT){
				throw new IllegalArgumentException("The port must be less then " + MAX_PORT);
			}
			if(port < MIN_PORT){
				throw new IllegalArgumentException("The port must be more then " + MIN_PORT);
			}		
			argParserResult.setPort(port);
		}else{
			throw new IllegalArgumentException("Please, enter the port");
		}
	}


}
