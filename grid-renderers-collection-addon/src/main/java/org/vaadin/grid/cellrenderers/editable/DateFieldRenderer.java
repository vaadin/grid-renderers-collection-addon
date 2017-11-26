package org.vaadin.grid.cellrenderers.editable;


import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * 
 * @author Tatu Lund
 *
 */
public class DateFieldRenderer extends EditableRenderer<Date> {

    /**
     * Constructor for new DateFieldRenderer 
     * 
     */
	public DateFieldRenderer() {
        super(Date.class);
     
        registerRpc(new DateFieldRendererServerRpc() {

            public void onChange(String rowKey, String columnId, Date newValue) {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<Date> cell = (Property<Date>) row.getItemProperty(columnPropertyId);
                
                cell.setValue(newValue);

                fireItemEditEvent(itemId, row, columnPropertyId, newValue);
            }

        });
    }

    /**
     * Set the date resolution of the DateField to be rendered
     *   valid values are Resolution.DAY, Resolution.MONTH and Resolution.YEAR. 
     * 
     * @param dateResolution The date resolution
     */
    public void setDateResolution(Resolution dateResolution) {
    	if (dateResolution == Resolution.DAY || dateResolution == Resolution.MONTH || dateResolution ==  Resolution.YEAR) {
    		getState().dateResolution = dateResolution;
    	} else {
            assert false : "Unexpected resolution argument " + dateResolution;
    	}
    }  
    /**
     * When blurChangeMode is set to true the text field emits value
     *  change on blur event. Default is false. 
     * 
     * @param blurChangeMode Boolean value
     */
    public void setBlurChangeMode(boolean blurChangeMode) {
    	getState().blurChangeMode = blurChangeMode;
    }

    @Override
    protected DateFieldRendererState getState() {
    	return (DateFieldRendererState) super.getState();
    }
    
}