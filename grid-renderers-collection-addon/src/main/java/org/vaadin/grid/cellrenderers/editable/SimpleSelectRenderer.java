package org.vaadin.grid.cellrenderers.editable;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererState;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.UI;

/**
 * Simple selection renderer to be used when the set of values to be selected is small. The renderer
 * has type parameter. The renderer uses ListBox GWT widget, which accepts only String as value.
 * Since there is many times need for select to be used with discrete numbers, enum, etc. other 
 * types than String, this renderer can be used with Converter that takes care of converting values
 * to Strings used in selection popup and real type in the Container. The renderer fires edit
 * event when value has been modified.
 * 
 * @author Tatu Lund
 */
public class SimpleSelectRenderer<T> extends EditableRenderer<T> {

	/**
	 * Constructor for SimpleSelectRenderer. Use when T = String.
	 * 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
	 */
	public SimpleSelectRenderer(List<String> dropDownList) {
        super((Class<T>) Object.class);
 
        getState().dropDownList = dropDownList;        

    	setupSimpleSelectRenderer();
    }
 
	/**
	 * Constructor for SimpleSelectRenderer. Use when T = String.
	 * 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
     * @param title Tooltip text for the select
	 */
	public SimpleSelectRenderer(List<String> dropDownList, String title) {
        super((Class<T>) Object.class);
 
        getState().dropDownList = dropDownList;        
        getState().title = title;

    	setupSimpleSelectRenderer();
    }

    /**
     * Alternative constructor for SimpleSelectRenderer. Use when T != String. Converter from T to String 
     * must be set for the column.
     * 
     * @param dropDownList List of the of the selection options to be shown in drop down menu
     * @param converter Converter for the values to selection captions. Must be the same converter than
     *        used for the column.
     * @param title Tooltip text for the select
     */
    public SimpleSelectRenderer(List<T> dropDownList, Converter<String, T> converter, String title) {
        super((Class<T>) Object.class);

        List<String> convertedDropDownList = new ArrayList<String>();
        for (T entry: dropDownList) {
        	convertedDropDownList.add(converter.convertToPresentation(entry, converter.getPresentationType(), UI.getCurrent().getLocale()));
        }
        getState().title = title;
        getState().dropDownList = convertedDropDownList;        
    	setupSimpleSelectRenderer();
    }
    
	private void setupSimpleSelectRenderer() {
        registerRpc(new SimpleSelectRendererServerRpc() {

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
    protected SimpleSelectRendererState getState()  {
        return (SimpleSelectRendererState) super.getState();
    }

}
