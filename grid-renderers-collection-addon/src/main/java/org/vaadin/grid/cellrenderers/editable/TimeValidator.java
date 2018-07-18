package org.vaadin.grid.cellrenderers.editable;

import java.util.Locale;
import java.util.regex.Pattern;

import com.vaadin.data.util.converter.Converter;

public class TimeValidator implements Converter<String, String> {

    static Pattern TIME_PATTERN = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    @Override
    public String convertToModel(final String value, final Class<? extends String> targetType, final Locale locale) throws ConversionException {
        if(returnValueIfMatchTime(value)) {
            return value;
        }
        throw new ConversionException();
    }

    @Override
    public String convertToPresentation(final String value, final Class<? extends String> targetType, final Locale locale) throws ConversionException {
        if(returnValueIfMatchTime(value)) {
            return value;
        }
        throw new ConversionException();
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    private boolean returnValueIfMatchTime(final String value) {
        return TimeValidator.TIME_PATTERN.matcher(value.trim()).matches();
    }

}
