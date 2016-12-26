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

import com.vaadin.data.HasValue;
import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.Editor;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class CheckboxRenderer<T> extends ClickableRenderer<T, Boolean> {

    public CheckboxRenderer(Setter<T, Boolean> setter) {
        super(Boolean.class, null);
        addClickListener(new RendererClickListener<T>() {
            @Override
            public void click(RendererClickEvent<T> event) {
                Grid<T> grid = getParentGrid();
                Editor<T> editor = grid.getEditor();
                Grid.Column<T, ?> column = event.getColumn();
                if (column.isEditable() && editor.isEnabled()) {
                    T item = event.getItem();

                    if (editor.isOpen() && !item.equals(editor.getBinder().getBean())) {
                        editor.save();
                        editor.cancel();
                    }
                    setter.accept(item, ((HasValue<Boolean>) event.getSource()).getValue());

                    // Todo run editor over a row?
                    // grid.getEditor().editItem(itemId);
                }
            }
        });
    }

}
