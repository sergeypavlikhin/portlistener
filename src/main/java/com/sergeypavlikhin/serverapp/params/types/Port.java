package com.sergeypavlikhin.serverapp.params.types;

import com.sergeypavlikhin.serverapp.params.Param;
import com.sergeypavlikhin.serverapp.params.ParamParserResult;

public class Port extends AbstractParamType{

	@Override
	public void parse(ParamParserResult argParserResult, Param param) {
		try{
			int port = Integer.parseInt(param.getValue());
			argParserResult.setPort(port);
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("The port must be a number");
		}
	}

}
