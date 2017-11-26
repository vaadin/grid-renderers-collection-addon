package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * 
 * @author Tatu Lund
 *
 */
public class TextFieldRenderer<T> extends EditableRenderer<T> {

    /**
     * Constructor for new TextFieldRenderer 
     * 
     */
	public TextFieldRenderer() {
        super((Class<T>) Object.class);
     
        registerRpc(new TextFieldRendererServerRpc() {

            @SuppressWarnings("unchecked")
			public void onChange(String rowKey, String columnId, String newValue) {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<Object> cell = (Property<Object>) row.getItemProperty(columnPropertyId);

                @SuppressWarnings("unchecked")
				Class<T> targetType = (Class<T>) cell.getType();
                @SuppressWarnings("unchecked")
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
    protected TextFieldRendererState getState() {
    	return (TextFieldRendererState) super.getState();
    }

    /**
     * When eagerChangeMode is set to true the text field emits value
     *  change after each key press. Default is false. 
     * 
     * @param eagerChangeMode Boolean value
     */
    public void setEagerChangeMode(boolean eagerChangeMode) {
    	getState().eagerChangeMode = eagerChangeMode;
    }
    
    /**
     * Get the current state of eagerChangeMode
     * 
     * @return State of eagerChangeMode
     */
    public boolean isEagerChangeMode() {
    	return getState().eagerChangeMode;
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
     * Get the current state of blurChangeMode
     * 
     * @return State of blurChangeMode
     */
    public boolean isBlurChangeMode() {
    	return getState().blurChangeMode;
    }    
}
