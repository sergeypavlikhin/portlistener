package com.sergeypavlikhin.serverapp.params;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.sergeypavlikhin.serverapp.ServerMode;
import com.sergeypavlikhin.serverapp.exceptions.NotImplementedTypeException;
import com.sergeypavlikhin.serverapp.params.types.AbstractParamType;

public class ParamsParser {

	private String[] args;
	
//	private ServerMode defaultMode = ServerMode.TCP;
	
	public ParamsParser (String[] args){
		this.args = args;
	}
	public ParamParserResult getResult(){
		ParamParserResult result = new ParamParserResult();		
		fillResult(result);		
		return result;
	}
	private void fillResult(ParamParserResult result) {
		
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
					throw new RuntimeException("Something wrong");
				}
				
			}
			
		}
		
		
	}
	private boolean isParamIden(String arg){
		if(!arg.isEmpty()){
			return arg.startsWith("-");
		}else{
			return false;
		}
	}
	
	
}
