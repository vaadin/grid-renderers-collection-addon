package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.BooleanSwitchRendererState;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.Page;

/**
 * @author Tatu Lund
 */
public class BooleanSwitchRenderer extends EditableRenderer<Boolean> {

    private long lastMillis = 0;
    private long DOUBLE_CLICK_DETECTION_MS = 200;

	/**
	 * Default constructor. Header caption is used as Checkbox label when value is false
	 */
   public BooleanSwitchRenderer() {
    	super(Boolean.class);
    	
    	setupBooleanSwithcRenderer();
    }
    
    /**
     * Constructor with configuration options for Checkbox label. Can be used for localization etc.
     * 
     * @param txtFalse Optional text to be shown in Checkbox label when value is false.
     * @param txtTrue Text to be shown in Checkbox label when value is true. If null, header label will be used.
     */
    public BooleanSwitchRenderer(String txtFalse, String txtTrue) {
    	super(Boolean.class);

    	getState().txtFalse = txtFalse;
    	getState().txtTrue = txtTrue;
    	
    	setupBooleanSwithcRenderer();
    }
    
    private void setupBooleanSwithcRenderer() {
    	if (Page.getCurrent().getWebBrowser().isIE() || Page.getCurrent().getWebBrowser().isEdge()) {
    		DOUBLE_CLICK_DETECTION_MS = 500;
    	}
    	
    	addClickListener(new RendererClickListener() {
    		@Override
    		public void click(RendererClickEvent event) {
    			// work around duplicate clicks when using a label as checkbox
    			if (!isDoubleClick()) {
    				Object itemId = event.getItemId();
    				Item row = getParentGrid().getContainerDataSource().getItem(itemId);
    				Object columnId = event.getPropertyId();

    				Object columnPropertyId = row.getItemProperty(columnId);
    				@SuppressWarnings("unchecked")
    				Property<Boolean> cell = (Property<Boolean>) columnPropertyId;

    				boolean checked = cell.getValue();

    				cell.setValue(!checked);
                    fireItemEditEvent(itemId, row, columnPropertyId, !checked);
    			}

    		}
    	});

    }

    private boolean isDoubleClick() {
        long diff = System.currentTimeMillis() - lastMillis;
        lastMillis = System.currentTimeMillis();
        
        if(diff > DOUBLE_CLICK_DETECTION_MS) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected BooleanSwitchRendererState getState() {
    	return (BooleanSwitchRendererState) super.getState();
    }
    

}
