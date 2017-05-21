package org.vaadin.grid.cellrenderers.demo;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class MyStringToIntegerConverter implements Converter<String, Integer> {
	@Override
	public Integer convertToModel(String s, Class<? extends Integer> targetType, Locale locale)
			throws ConversionException {
    	Integer value = 0;
    	switch (s) {
    		case "Zero": value = 0; break;
    		case "One": value = 1; break;
    		case "Two": value = 2; break;
    		case "Three": value = 3; break;
    		case "Four": value = 4; break;
    		case "Five": value = 5; break;
    		default: throw new ConversionException("Can not convert String to Integer: "+s);
    	}
    	return value;
    }

	@Override
	public String convertToPresentation(Integer i, Class<? extends String> targetType, Locale locale)
			throws ConversionException {
    	String value = "";
    	switch (i) {
    		case 0: value = "Zero"; break;
    		case 1: value = "One"; break;
    		case 2: value = "Two"; break;
    		case 3: value = "Three"; break;
    		case 4: value = "Four"; break;
    		case 5: value = "Five"; break;
    		default: throw new ConversionException("Can not convert Integer to String: "+i);
    	}
    	return value;
    }

	@Override
	public Class<Integer> getModelType() {
        return Integer.class;
    }

	@Override
	public Class<String> getPresentationType() {
        return String.class;
    }

}
