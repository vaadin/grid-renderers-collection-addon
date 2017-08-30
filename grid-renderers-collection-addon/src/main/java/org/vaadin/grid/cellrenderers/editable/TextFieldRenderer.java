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
public class TextFieldRenderer<A> extends EditableRenderer<A,String> {

	public TextFieldRenderer(final Setter<A,String> setter) {
    
		super(String.class);
     
        registerRpc(new TextFieldRendererServerRpc() {

			public void onChange(String rowKey, String columnId, String newValue) {
            	
            	Grid<A> grid = getParentGrid();
            	A item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<A, String> column = getParent();
            	setter.accept(item,newValue);
            	
                fireItemEditEvent(item, column, newValue);
            }

        });
    }


    @Override
    protected TextFieldRendererState getState() {
    	return (TextFieldRendererState) super.getState();
    }
    
}
