package org.vaadin.grid.cellrenderers.editable;


import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * 
 * @author Tatu Lund
 *
 */
public class DateFieldRenderer extends EditableRenderer<Date>
{
    public DateFieldRenderer()
    {
        super(Date.class);
     
        registerRpc(new DateFieldRendererServerRpc()
        {

            public void onChange(String rowKey, String columnId, Date newValue)
            {

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

    @Override
    protected DateFieldRendererState getState()
    {
    	return (DateFieldRendererState) super.getState();
    }
    
}