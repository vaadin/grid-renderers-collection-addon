package org.vaadin.grid.cellrenderers.editable;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class TimeValidator implements Converter<String, String> {

    @Override
    public String convertToModel(final String value, final Class<? extends String> targetType, final Locale locale) throws ConversionException {
        return returnValueIfMatchTime(value);

    }

    @Override
    public String convertToPresentation(final String value, final Class<? extends String> targetType, final Locale locale) throws ConversionException {
        return returnValueIfMatchTime(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    private String returnValueIfMatchTime(final String value) {
        if (value.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            return value;
        }
        throw new ConversionException();
    }

}
