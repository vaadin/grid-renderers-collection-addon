package org.vaadin.grid.cellrenderers.view;

import org.vaadin.grid.cellrenderers.client.view.ConverterRendererState;
import org.vaadin.grid.cellrenderers.client.view.RowIndexMode;
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
     * @param mode The mode,  @see org.vaadin.grid.cellrenderers.client.view#RowIndexMode 
     * RowIndexMode.ORDINAL Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * RowIndexMode.ROMAN Set indeces to be roman literals.
     */
    public RowIndexRenderer(RowIndexMode mode) {
        super((Class<A>) Object.class);
        getState().rowIndexMode = mode;
    }

    
    /**
     * Constructor with ordinals / not ordinals.
     * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * 
     * @deprecated Use {@link RowIndexRenderer#RowIndexRenderer(RowIndexMode)} instead
     * @param ordinalMode True = ordinals used, False = just digits
     */
    @Deprecated
    public RowIndexRenderer(boolean ordinalMode) {
        super((Class<A>) Object.class);
        if (ordinalMode) getState().rowIndexMode = RowIndexMode.ORDINAL;
        else getState().rowIndexMode = RowIndexMode.NORMAL;
    }

    /**
     * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * 
     * @deprecated Use {@link RowIndexRenderer#setRowIndexMode(RowIndexMode)}  instead
     * @param ordinalMode True = ordinals used, False = just digits
     */
    @Deprecated
    public void setOridnalMode(boolean ordinalMode) {
        if (ordinalMode) getState().rowIndexMode = RowIndexMode.ORDINAL;
        else getState().rowIndexMode = RowIndexMode.NORMAL;
    }

    /**
     * Set the mode of the the RowIndexRenderer
     * 
     * @param mode The mode 
     * RowIndexMode.ORDINAL Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * RowIndexMode.ROMAN Set indeces to be roman literals.
     * 
     */ 
    public void setRowIndexMode(RowIndexMode mode) {
        getState().rowIndexMode = mode;
    }

    /**
     * Get the currently set mode of the the RowIndexRenderer
     * 
     * @see RowIndexRenderer#setRowIndexMode(RowIndexMode)
     * 
     * @return The current mode
     */
    public RowIndexMode getRowIndexMode() {
    	return getState().rowIndexMode;
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
