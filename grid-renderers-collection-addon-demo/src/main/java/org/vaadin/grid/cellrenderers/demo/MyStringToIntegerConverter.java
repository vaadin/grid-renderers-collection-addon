package org.vaadin.grid.cellrenderers.demo;

import java.util.Locale;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

public class MyStringToIntegerConverter implements Converter<String, Integer> {

	private String errorMessage = "Out of bounds 0..5";
	
	@Override
	public Result<Integer> convertToModel(String value, ValueContext context) {
		
    	Integer newValue = 0;
    	switch (value) {
    		case "Zero": newValue = 0; break;
    		case "One": newValue = 1; break;
    		case "Two": newValue = 2; break;
    		case "Three": newValue = 3; break;
    		case "Four": newValue = 4; break;
    		case "Five": newValue = 5; break;
    		default: Result.error(errorMessage);
    	}
    	return Result.ok(newValue);
	}

	@Override
	public String convertToPresentation(Integer value, ValueContext context) {
		// TODO Auto-generated method stub
    	String newValue = "";
    	switch (value) {
    		case 0: newValue = "Zero"; break;
    		case 1: newValue = "One"; break;
    		case 2: newValue = "Two"; break;
    		case 3: newValue = "Three"; break;
    		case 4: newValue = "Four"; break;
    		case 5: newValue = "Five"; break;
    		default: newValue = null;
    	}
    	return newValue;
	}

}
