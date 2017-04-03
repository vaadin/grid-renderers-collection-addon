package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.renderers.ClickableRenderer;


public class TextFieldRenderer<T> extends EditableRenderer<T>
{
    public TextFieldRenderer()
    {
        super((Class<T>) Object.class);
     
        registerRpc(new TextFieldRendererServerRpc()
        {

            public void onChange(String rowKey, String columnId, String newValue)
            {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<Object> cell = (Property<Object>) row.getItemProperty(columnPropertyId);

                Class<T> targetType = (Class<T>) cell.getType();
                Converter<String, T> converter = (Converter<String, T>) getColumn(columnId).getConverter();
                T value = null;
                if (converter != null) {
                    value = converter.convertToModel(newValue, targetType, getParentGrid().getLocale());
                } else if (targetType == String.class) {
                    value = (T) newValue;
                }

                cell.setValue(value);
                fireItemEditEvent(itemId, row, columnPropertyId, value);
            }

        });
    }


    @Override
    protected TextFieldRendererState getState()
    {
    	return (TextFieldRendererState) super.getState();
    }
    
}
