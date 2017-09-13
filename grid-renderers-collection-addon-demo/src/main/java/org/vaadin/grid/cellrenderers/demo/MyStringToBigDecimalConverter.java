package org.vaadin.grid.cellrenderers.demo;

import java.math.BigDecimal;

import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToBigDecimalConverter;

public class MyStringToBigDecimalConverter extends StringToBigDecimalConverter {

	public MyStringToBigDecimalConverter(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public String convertToPresentation(BigDecimal value, ValueContext context) {
		String text = super.convertToPresentation(value, context);
	    if (value.doubleValue() > 80.0) text = "<B>"+text+"</B>";
		if (value.doubleValue() < 20.0) text = "<I>"+text+"</I>";
		return text;
	}

}
