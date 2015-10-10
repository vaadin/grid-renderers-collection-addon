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
package org.vaadin.grid.cellrenderers.client.editoraware.renderers;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.ui.VCheckBox;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class CheckboxRenderer extends ClickableRenderer<Boolean, VCheckBox> {


    @Override
    public VCheckBox createWidget() {
        VCheckBox b = GWT.create(VCheckBox.class);
        b.addClickHandler(this);
        b.setStylePrimaryName("v-checkbox");
        b.setEnabled(false);
        return b;
    }

    @Override
    public void render(RendererCellReference cell, Boolean value, VCheckBox checkBox) {
        checkBox.setValue(value, false);
        Grid.HeaderRow headerRow = cell.getGrid().getDefaultHeaderRow();
        String text ="";
        if (headerRow != null) {
            Grid.HeaderCell headerCell = headerRow.getCell(cell.getColumn());
            if (headerCell != null && headerCell.getText() != null) text = headerCell.getText();
        }
        checkBox.setText(text);
    }
}
