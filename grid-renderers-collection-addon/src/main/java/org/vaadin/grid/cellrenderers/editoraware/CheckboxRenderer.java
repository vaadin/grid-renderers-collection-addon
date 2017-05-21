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

import org.vaadin.grid.cellrenderers.client.editoraware.CheckboxRendererState;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Ilya Motorny, Tatu Lund
 */
public class CheckboxRenderer extends ClickableRenderer<Boolean> {

	/**
	 * Default constructor. Header caption is used as Checkbox label when value is false
	 */
    public CheckboxRenderer() {
    	super(Boolean.class);
    	
    	setupCheckboxRenderer();
    }
    
    /**
     * Constructor with configuration options for Checkbox label. Can be used for localization etc.
     * 
     * @param txtFalse Optional text to be shown in Checkbox label when value is false.
     * @param txtTrue Text to be shown in Checkbox label when value is true. If null, header label will be used.
     */
    public CheckboxRenderer(String txtFalse, String txtTrue) {
    	super(Boolean.class);

    	getState().txtFalse = txtFalse;
    	getState().txtTrue = txtTrue;
    	
    	setupCheckboxRenderer();
    }

	
	public void setupCheckboxRenderer() {
        addClickListener(new RendererClickListener() {
            @Override
            public void click(RendererClickEvent event) {
                Grid grid = getParentGrid();
                if (event.getColumn().isEditable() && grid.isEditorEnabled()) {
                    Object itemId = event.getItemId();
                    Object propertyId = event.getPropertyId();

                    try {
                        if (grid.isEditorActive() && !itemId.equals(grid.getEditedItemId())) {
                            grid.saveEditor();
                            grid.cancelEditor();
                        }
                        @SuppressWarnings("unchecked")
						Property<Boolean> itemProperty = grid.getContainerDataSource().getItem(itemId).getItemProperty(propertyId);
                        itemProperty.setValue(!Boolean.TRUE.equals(itemProperty.getValue()));
                        grid.editItem(itemId);
                    } catch (FieldGroup.CommitException e) {
                        Grid.CommitErrorEvent errorEvent = new Grid.CommitErrorEvent(grid, e);
                        grid.getEditorErrorHandler().commitError(errorEvent);
                    }
                }
            }
        });
    }

    @Override
    protected CheckboxRendererState getState() {
    	return (CheckboxRendererState) super.getState();
    }
 
}
