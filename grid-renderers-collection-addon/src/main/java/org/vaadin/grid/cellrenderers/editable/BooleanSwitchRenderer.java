package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.BooleanSwitchRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.BooleanSwitchRendererState;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.google.gwt.editor.client.Editor;
import com.vaadin.data.HasValue;
import com.vaadin.server.Page;
import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

/**
 * @author Tatu Lund
 */
public class BooleanSwitchRenderer<T> extends EditableRenderer<T,Boolean> {

    private long lastMillis = 0;
    private long DOUBLE_CLICK_DETECTION_MS = 200;

	/**
	 * Default constructor. Header caption is used as Checkbox label when value is false
	 * 
	 * @param setter Method reference to right setter of T 
	 */
   public BooleanSwitchRenderer(Setter<T, Boolean> setter) {

	   super(Boolean.class);
	   
	   setupBooleanSwithcRenderer(setter);
    }
    
    /**
     * Constructor with configuration options for Checkbox label. Can be used for localization etc.
     * 
	 * @param setter Method reference to right setter of T 
     * @param txtFalse Optional text to be shown in Checkbox label when value is false.
     * @param txtTrue Text to be shown in Checkbox label when value is true. If null, header label will be used.
     */
    public BooleanSwitchRenderer(Setter<T, Boolean> setter, String txtFalse, String txtTrue) {
    	super(Boolean.class);

    	getState().txtFalse = txtFalse;
    	getState().txtTrue = txtTrue;
    	
    	setupBooleanSwithcRenderer(setter);
    }
    
    private void setupBooleanSwithcRenderer(final Setter<T, Boolean> setter) {
    	if (Page.getCurrent().getWebBrowser().isIE() || Page.getCurrent().getWebBrowser().isEdge()) {
    		DOUBLE_CLICK_DETECTION_MS = 500;
    	}
        registerRpc(new BooleanSwitchRendererServerRpc() {

			public void onChange(String rowKey, Boolean newValue) {
    			// work around duplicate clicks when using a label as checkbox
    			if (!isDoubleClick()) {
                	Grid<T> grid = getParentGrid();
                	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
                	Column<T, Boolean> column = getParent();
        			setter.accept(item, newValue);
                    grid.getDataProvider().refreshItem(item);
                    fireItemEditEvent(item, column, newValue);
    				
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
