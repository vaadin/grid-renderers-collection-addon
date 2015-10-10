package org.vaadin.grid.cellrenderers;


import java.util.Date;

import org.vaadin.grid.cellrenderers.client.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.DateFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.renderers.ClickableRenderer;

public class DateFieldRenderer extends ClickableRenderer<Date>
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
            }

        });
    }

    @Override
    protected DateFieldRendererState getState()
    {
    	return (DateFieldRendererState) super.getState();
    }
    
}