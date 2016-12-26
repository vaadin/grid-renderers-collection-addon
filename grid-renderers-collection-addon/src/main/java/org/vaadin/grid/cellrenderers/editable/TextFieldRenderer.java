package org.vaadin.grid.cellrenderers.editable;

import com.vaadin.server.Setter;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.ui.renderers.ClickableRenderer;


public class TextFieldRenderer<T> extends ClickableRenderer<T,String>
{
    public TextFieldRenderer(Setter<T,String> setter)
    {
        super(String.class);
     
        registerRpc(new TextFieldRendererServerRpc()
        {

            public void onChange(String rowKey, String columnId, String newValue)
            {
                T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
                setter.accept(item,newValue);
            }

        });
    }


    @Override
    protected TextFieldRendererState getState()
    {
    	return (TextFieldRendererState) super.getState();
    }
    
}
