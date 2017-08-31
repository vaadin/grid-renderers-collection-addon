package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

/**
 * 
 * @author Tatu Lund
 *
 */
public class TextFieldRenderer<T> extends EditableRenderer<T,String> {

	public TextFieldRenderer(final Setter<T,String> setter) {
    
		super(String.class);
     
        registerRpc(new TextFieldRendererServerRpc() {

			public void onChange(String rowKey, String newValue) {
            	
            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T, String> column = getParent();
            	setter.accept(item,newValue);
            	grid.getDataProvider().refreshItem(item);
            	
                fireItemEditEvent(item, column, newValue);
            }

        });
    }


    @Override
    protected TextFieldRendererState getState() {
    	return (TextFieldRendererState) super.getState();
    }
    
}
