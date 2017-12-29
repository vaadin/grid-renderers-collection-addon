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

    /**
     * Constructor with ordinals / not ordinals.
     * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * 
     * @param ordinalMode True = ordinals used, False = just digits
     */
    public RowIndexRenderer(boolean ordinalMode) {
        super((Class<A>) Object.class);
        getState().ordinalMode = ordinalMode;
    }

    /**
     * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * 
     * @param ordinalMode True = ordinals used, False = just digits
     */
    public void setOridnalMode(boolean ordinalMode) {
     getState().ordinalMode = ordinalMode;    	
     }

    /**
     * Set adjustment for row index, e.g. with offset = -1, row count starts from 0.
     * 
     * @param offset Adjustment to be used.
     */
    public void setOffset(int offset) {
        getState().offset = offset;    	
    }    
    
    @Override
    protected RowIndexRendererState getState() {
    	return (RowIndexRendererState) super.getState();
    }
}
