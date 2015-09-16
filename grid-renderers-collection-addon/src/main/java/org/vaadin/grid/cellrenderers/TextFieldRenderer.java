package org.vaadin.grid.cellrenderers;

import org.vaadin.grid.cellrenderers.client.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.TextFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.renderers.ClickableRenderer;


public class TextFieldRenderer extends ClickableRenderer<String>
{
    public TextFieldRenderer()
    {
        super(String.class);
     
        registerRpc(new TextFieldRendererServerRpc()
        {

            public void onChange(String rowKey, String columnId, String newValue)
            {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<String> cell = (Property<String>) row.getItemProperty(columnPropertyId);

                cell.setValue(newValue);
            }

        });
    }

    @Override
    protected TextFieldRendererState getState()
    {
    	return (TextFieldRendererState) super.getState();
    }
    
}
