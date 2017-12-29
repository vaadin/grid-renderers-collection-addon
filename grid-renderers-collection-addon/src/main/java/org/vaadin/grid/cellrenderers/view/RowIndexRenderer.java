package org.vaadin.grid.cellrenderers.view;

import org.vaadin.grid.cellrenderers.client.view.ConverterRendererState;
import org.vaadin.grid.cellrenderers.client.view.RowIndexRendererState;

import com.vaadin.data.Converter;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.renderers.AbstractRenderer;

import elemental.json.JsonValue;

/**
 * @author Tatu Lund - Vaadin
 */
public class RowIndexRenderer<T,A> extends AbstractRenderer<T,A> {

    /**
     * Constructor for a new ConverterRenderer 
     * 
     */
    public RowIndexRenderer() {
        super((Class<A>) Object.class);
      
    }

    public RowIndexRenderer(boolean ordinalMode) {
        super((Class<A>) Object.class);
        getState().ordinalMode = ordinalMode;
    }

    @Override
    protected RowIndexRendererState getState() {
    	return (RowIndexRendererState) super.getState();
    }
}
