package org.vaadin.grid.cellrenderers.editable;

import java.util.List;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererState;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid.Column;

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
public class SimpleSelectRenderer<T> extends EditableRenderer<T,String> {

	/**
	 * Constructor for SimpleSelectRenderer. Use when T = String.
	 * 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
	 */
	public SimpleSelectRenderer(List<String> dropDownList, Setter<T,String> setter) {
        super(String.class);
 
        getState().dropDownList = dropDownList;        

    	setupSimpleSelectRenderer(setter);
    }
 
	/**
	 * Constructor for SimpleSelectRenderer. Use when T = String.
	 * 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
     * @param title Tooltip text for the select
	 */
	public SimpleSelectRenderer(List<String> dropDownList, String title, Setter<T,String> setter) {
        super(String.class);
 
        getState().dropDownList = dropDownList;        
        getState().title = title;

    	setupSimpleSelectRenderer(setter);
    }

	private void setupSimpleSelectRenderer(final Setter<T,String> setter) {
        registerRpc(new SimpleSelectRendererServerRpc() {

            public void onChange(String rowKey, String columnId, String newValue) {

            	T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T,String> column = getParent();
             	setter.accept(item,newValue);
                fireItemEditEvent(item, column, newValue);
             }
        });
    }

    @Override
    protected SimpleSelectRendererState getState()  {
        return (SimpleSelectRendererState) super.getState();
    }

}
