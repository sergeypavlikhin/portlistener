package com.sergeypavlikhin.serverapp.params;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.sergeypavlikhin.serverapp.exceptions.NotImplementedTypeException;
import com.sergeypavlikhin.serverapp.params.types.AbstractParamType;

public class ParamsParser {

	public static ParamParserResult parseArguments(String[] args){
		ParamParserResult result = new ParamParserResult();		
		fillResult(args, result);		
		return result;
	}
	private static void fillResult(String[] args, ParamParserResult result) {
		
		if (args.length > 0) {

			List<String> params = Arrays.asList(args);

			Iterator<String> iterator = params.iterator();

			while (iterator.hasNext()) {

				try {
					String arg = iterator.next().trim();

					if (isParamIden(arg)) {
						Param param = null;

						if (iterator.hasNext()) { // Param with value: -key value
							String argValue = iterator.next().trim();
							param = new Param(arg, argValue);
						} else {
							param = new Param(arg, null);
						}

						AbstractParamType paramType = ParamTypeFactory.getType(arg);
						paramType.parse(result, param);
					}
				}
				catch (IllegalArgumentException | NotImplementedTypeException e) {
					result.getMessages().add(e.getMessage());
				}catch (Exception e) {
					throw new RuntimeException("Something wrong", e);
				}
				
			}
			
		}
		
		
	}
	private static boolean isParamIden(String arg){
		if(!arg.isEmpty()){
			return arg.startsWith("-");
		}else{
			return false;
		}
	}
	
	
}
