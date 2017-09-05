/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.grid.cellrenderers.editoraware;

import org.vaadin.grid.cellrenderers.client.editable.BooleanSwitchRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editoraware.CheckboxRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editoraware.CheckboxRendererState;

import com.vaadin.data.HasValue;
import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.components.grid.Editor;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Ilya Motorny, Tatu Lund
 */
public class CheckboxRenderer<T> extends ClickableRenderer<T,Boolean> {

	/**
	 * Default constructor. Header caption is used as Checkbox label when value is false
	 * 
	 * @param setter Method reference to right setter of T 
	 */
    public CheckboxRenderer(Setter<T, Boolean> setter) {
    	super(Boolean.class);
    	
    	setupCheckboxRenderer(setter);
    }
    
    /**
     * Constructor with configuration options for Checkbox label. Can be used for localization etc.
     * 
	 * @param setter Method reference to right setter of T 
     * @param txtFalse Optional text to be shown in Checkbox label when value is false.
     * @param txtTrue Text to be shown in Checkbox label when value is true. If null, header label will be used.
     */
    public CheckboxRenderer(Setter<T, Boolean> setter, String txtFalse, String txtTrue) {
    	super(Boolean.class);

    	getState().txtFalse = txtFalse;
    	getState().txtTrue = txtTrue;
    	
    	setupCheckboxRenderer(setter);
    }

	
	public void setupCheckboxRenderer(final Setter<T, Boolean> setter) {
   
		registerRpc(new CheckboxRendererServerRpc() {
        
 			public void onChange(String rowKey, Boolean newValue) {
                Grid<T> grid = getParentGrid();
            	Editor<T> editor = grid.getEditor();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T, Boolean> column = getParent();
                // Do nothing if we are not in unbuffered mode
                if (editor.isBuffered()) {
                	return;
                }
               	if (column.isEditable() && editor.isEnabled()) {
               		if (editor.isOpen() && !item.equals(editor.getBinder().getBean())) {
               			editor.save();
               			editor.cancel();
               		}
             		setter.accept(item, newValue);
                	grid.getDataProvider().refreshItem(item);
               	}
            }
        });
    }

    @Override
    protected CheckboxRendererState getState() {
    	return (CheckboxRendererState) super.getState();
    }
 
}
