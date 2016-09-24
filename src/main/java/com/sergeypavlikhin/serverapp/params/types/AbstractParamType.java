package com.sergeypavlikhin.serverapp.params.types;

import com.sergeypavlikhin.serverapp.params.Param;
import com.sergeypavlikhin.serverapp.params.ParamParserResult;

public abstract class AbstractParamType {

	public abstract void parse(ParamParserResult argParserResult, Param param) throws Exception;
}
