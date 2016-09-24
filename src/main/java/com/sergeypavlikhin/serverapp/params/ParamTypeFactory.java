package com.sergeypavlikhin.serverapp.params;

import com.sergeypavlikhin.serverapp.params.types.AbstractParamType;
import com.sergeypavlikhin.serverapp.params.types.NotImplementedType;
import com.sergeypavlikhin.serverapp.params.types.Port;

public class ParamTypeFactory {

	public static AbstractParamType getType(String key){
		switch (key) {
		case "-p":
			return new Port();
		default:
			return new NotImplementedType();
		}
	}
}
