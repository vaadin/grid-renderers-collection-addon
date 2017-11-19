package org.vaadin.grid.cellrenderers.editable;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;

import com.vaadin.server.Setter;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

import elemental.json.JsonValue;

/**
 * 
 * @author Tatu Lund
 *
 */
public class DateFieldRenderer<T> extends EditableRenderer<T,LocalDate> {

	public DateFieldRenderer(final Setter<T, LocalDate> setter) {

		super(LocalDate.class);
     
        registerRpc(new DateFieldRendererServerRpc() {

            public void onChange(String rowKey, Date newValue) {

            	Grid<T> grid = getParentGrid();
                T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
                Column<T,LocalDate> column = getParent();
                
                LocalDate newLocalDate;
                if (newValue == null) {
                	newLocalDate = null;
                } else {
                	// Client side uses old Date, convert to LocalDate
                	newLocalDate = Instant.ofEpochMilli(newValue.getTime()).atZone(ZoneOffset.UTC).toLocalDate();
                }
                setter.accept(item,newLocalDate);
            	grid.getDataProvider().refreshItem(item);
                
                fireItemEditEvent(item, column, newLocalDate);
            }

        });
    }

    protected Date convertToDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZoneOffset.UTC).toInstant());
    }


    @Override
    public JsonValue encode(LocalDate value) {
        if (value == null) {
            return encode(null, Date.class);
        } else {
            return encode(convertToDate(value), Date.class);
        }
    }
	
    @Override
    protected DateFieldRendererState getState() {
    	return (DateFieldRendererState) super.getState();
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

    /**
     * Set the date resolution of the DateField to be rendered
     *   valid values are DateResolution.DAY, DateResolution.MONTH and DateResolution.YEAR. 
     * 
     * @param dateResolution The date resolution
     */
    public void setDateResolution(DateResolution dateResolution) {
    	if (dateResolution == DateResolution.DAY || dateResolution == DateResolution.MONTH || dateResolution ==  DateResolution.YEAR) {
    		getState().dateResolution = dateResolution;
    	} else {
            assert false : "Unexpected resolution argument " + dateResolution;
    	}
    }

    /**
     * Toggle Renderer to be editable / non-editable (=true). Default is editable. 
     * 
     * @param readOnly Boolean value
     */
    public void setReadOnly(boolean readOnly) {
    	getState().readOnly = readOnly;
    }
    
    /**
     * Returns if Renderer is editable or non-editable at the moment.
     * 
     * @return Boolean value
     */
    public boolean isReadOnly() {
    	return getState().readOnly;        
    }    
}