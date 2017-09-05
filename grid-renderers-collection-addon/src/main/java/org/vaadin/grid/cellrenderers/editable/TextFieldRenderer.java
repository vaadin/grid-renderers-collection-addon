package org.vaadin.grid.cellrenderers.editable;

import java.time.LocalDate;
import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

import elemental.json.JsonValue;

/**
 * 
 * @author Tatu Lund
 *
 */
public class TextFieldRenderer<T,A> extends EditableRenderer<T,A> {
	private Converter<String,A> converter;

	public void setConverter(Converter<String,A> converter) {
		this.converter = converter;
	}
	
	
	public TextFieldRenderer(final Setter<T,A> setter) {
    
        super((Class<A>) Object.class);
     
        registerRpc(new TextFieldRendererServerRpc() {

			public void onChange(String rowKey, String value) {
            	
            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T, A> column = getParent();
            	A newValue = null;
            	Boolean validated = false;
            	if (converter != null) {
            		Result<A> result = converter.convertToModel(value, new ValueContext());
            		if (!result.isError()) {
            			newValue = result.getOrThrow(errorMessage -> new IllegalArgumentException(errorMessage));
            			validated = true;
            		}
            	} else  {
            		newValue = (A) value;
            		validated = true;
            	}
            	if (validated) {
            		setter.accept(item,newValue);
            		grid.getDataProvider().refreshItem(item);
                    fireItemEditEvent(item, column, newValue);
            	}
            	
            }

        });
    }

    @Override
    public JsonValue encode(A value) {
        if (converter == null) {
            return encode((String) value, String.class);
        } else {
            return encode(converter.convertToPresentation(value, new ValueContext()), String.class);
        }
    }	

    @Override
    protected TextFieldRendererState getState() {
    	return (TextFieldRendererState) super.getState();
    }
    
}
