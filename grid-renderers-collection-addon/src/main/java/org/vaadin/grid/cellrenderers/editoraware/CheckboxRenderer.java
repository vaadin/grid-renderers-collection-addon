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

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class CheckboxRenderer extends ClickableRenderer<Boolean> {
    public CheckboxRenderer() {
        super(Boolean.class, null);
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
                        Container.Indexed containerDataSource = grid.getContainerDataSource();
                        Property itemProperty = containerDataSource.getItem(itemId).getItemProperty(propertyId);
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

}
