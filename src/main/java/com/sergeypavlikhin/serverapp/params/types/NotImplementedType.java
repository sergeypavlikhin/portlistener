package com.sergeypavlikhin.serverapp.params.types;

import com.sergeypavlikhin.serverapp.exceptions.NotImplementedTypeException;
import com.sergeypavlikhin.serverapp.params.Param;
import com.sergeypavlikhin.serverapp.params.ParamParserResult;

public class NotImplementedType extends AbstractParamType {

	@Override
	public void parse(ParamParserResult argParserResult, Param param) throws Exception {
		throw new NotImplementedTypeException();
	}

}
