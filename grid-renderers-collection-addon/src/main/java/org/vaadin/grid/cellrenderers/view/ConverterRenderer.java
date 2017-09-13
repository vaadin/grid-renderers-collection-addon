package org.vaadin.grid.cellrenderers.view;

import org.vaadin.grid.cellrenderers.client.view.ConverterRendererState;

import com.vaadin.data.Converter;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.renderers.AbstractRenderer;

import elemental.json.JsonValue;

/**
 * @author Tatu Lund - Vaadin
 */
public class ConverterRenderer<T,A> extends AbstractRenderer<T,A> {

	private Converter<String,A> converter;

    /**
     * Set converter to be used with the renderer.
     * 
     * @param converter A custom String to something converter to be used with renderer. HTML allowed.
     * 
     */	
	public void setConverter(Converter<String,A> converter) {
		this.converter = converter;
	}
	
    /**
     * Set converter to be used with the renderer.
     * 
     * @param nullRepresentation String to be shown if result of conversion is null. HTML allowed.
     * 
     */
	public void setNullRepresentation(String nullRepresentation) {
		getState().nullRepresentation = nullRepresentation;
	}
	
    /**
     * Constructor for a new ConverterRenderer 
     * 
     */
    public ConverterRenderer() {
        super((Class<A>) Object.class);
      
    }

    /**
     * Constructor for a new ConverterRenderer with parameter settings
     * 
     * @param converter A custom String to something converter to be used with renderer.
     */
    public ConverterRenderer(Converter<String,A> converter) {
        super((Class<A>) Object.class);

        this.converter = converter;
    }
    
	
    @Override
    public String getNullRepresentation() {
        return getState().nullRepresentation;
    }

    @Override
    public JsonValue encode(A value) {
        if (converter == null) {
            return encode((String) value, String.class);
        } else {
            return encode(converter.convertToPresentation(value, new ValueContext()), String.class);
        }
    }

    @Override
    protected ConverterRendererState getState() {
    	return (ConverterRendererState) super.getState();
    }
  
}
