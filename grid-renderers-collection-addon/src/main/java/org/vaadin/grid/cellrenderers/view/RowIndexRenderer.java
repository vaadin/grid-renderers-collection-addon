package org.vaadin.grid.cellrenderers.view;

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