package com.sergeypavlikhin.serverapp.params.types;

import com.sergeypavlikhin.serverapp.ServerMode;
import com.sergeypavlikhin.serverapp.params.Param;
import com.sergeypavlikhin.serverapp.params.ParamParserResult;

public class Mode extends AbstractParamType{

	@Override
	public void parse(ParamParserResult argParserResult, Param param) {
		
		String enteredMode = param.getValue();
		ServerMode mode = ServerMode.valueOf(enteredMode.toUpperCase().trim());
		if(mode != null){
			argParserResult.setMode(mode);
		}else{
			throw new IllegalArgumentException("The entered mode is not supported");
		}
		
	}

}
