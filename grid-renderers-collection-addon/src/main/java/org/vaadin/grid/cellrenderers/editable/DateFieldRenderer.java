package org.vaadin.grid.cellrenderers.editable;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid.Column;

/**
 * 
 * @author Tatu Lund
 *
 */
public class DateFieldRenderer<T> extends EditableRenderer<T,LocalDate> {
    public DateFieldRenderer(final Setter<T, LocalDate> setter) {
        super(LocalDate.class);
     
        registerRpc(new DateFieldRendererServerRpc() {

            public void onChange(String rowKey, String columnId, Date newValue) {

                T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
                Column<T,LocalDate> column = getParent();
                
                LocalDate newLocalDate;
                if (newValue == null) {
                	newLocalDate = null;
                } else {
                	// Client side uses old Date, convert to LocalDate
                	newLocalDate = Instant.ofEpochMilli(newValue.getTime()).atZone(ZoneOffset.UTC).toLocalDate();
                }
                setter.accept(item,newLocalDate);
                
                fireItemEditEvent(item, column, newLocalDate);
            }

        });
    }

    @Override
    protected DateFieldRendererState getState() {
    	return (DateFieldRendererState) super.getState();
    }
    
}