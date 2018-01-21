package org.vaadin.grid.cellrenderers.view;

import org.vaadin.grid.cellrenderers.client.view.RowIndexRendererState;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.AbstractRenderer;

import elemental.json.JsonValue;

/**
 * @author Tatu Lund - Vaadin
 */
public class RowIndexRenderer extends AbstractRenderer {

    /**
     * Constructor for a new RowIndexRenderer 
     * 
     */
    public RowIndexRenderer() {
        super(String.class);      
    }

    
    /**
     * Constructor with ordinals / not ordinals.
     * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
     * 
     * @param ordinalMode True = ordinals used, False = just digits
     */
    public RowIndexRenderer(boolean ordinalMode) {
        super(String.class);
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
    
    
    /**
     * Helper method to add suitable dummy generated property for the row index
     * 
     * @param property The property name
     */
    public GeneratedPropertyContainer addGeneratedProperty(String property, Container.Indexed container) {
    	GeneratedPropertyContainer dummy = new GeneratedPropertyContainer(container);
    	dummy.addGeneratedProperty(property,new PropertyValueGenerator<String>() {
    		@Override
    		public String getValue(Item item, Object itemId, Object propertyId) {
    			return "";
    		}
    		@Override
    		public Class<String> getType() {
    			return String.class;
    		}
    	});
    	return dummy;
    }

}