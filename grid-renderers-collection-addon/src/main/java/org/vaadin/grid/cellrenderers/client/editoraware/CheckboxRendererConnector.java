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
package org.vaadin.grid.cellrenderers.client.editoraware;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.ui.VCheckBox;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonObject;

import org.vaadin.grid.cellrenderers.client.editable.BooleanSwitchRendererConnector.BooleanSwitchClientRenderer;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
@Connect(CheckboxRenderer.class)
public class CheckboxRendererConnector extends ClickableRendererConnector<Boolean> {

	public class CheckboxClientRenderer extends ClickableRenderer<Boolean, VCheckBox> {

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
			if (getState().txtTrue != null) {
				String text = null;
				if (value) text = getState().txtTrue;
				else text = getState().txtFalse;					
				if (text != null) checkBox.setText(text);
			} else {
		        Grid.HeaderRow headerRow = cell.getGrid().getDefaultHeaderRow();
		        String text = "";
		        if (headerRow != null) {
		            Grid.HeaderCell headerCell = headerRow.getCell(cell.getColumn());
		            if (headerCell != null && headerCell.getText() != null) text = headerCell.getText();
		            checkBox.setText(text);
		        }				
			}
	    }
	}

	
	@Override
    public CheckboxClientRenderer getRenderer() {
        return (CheckboxClientRenderer) super.getRenderer();
    }

    @Override
    protected CheckboxClientRenderer createRenderer() {
        return new CheckboxClientRenderer();
    }

    @Override
    protected HandlerRegistration addClickHandler(ClickableRenderer.RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }

    @Override
    public CheckboxRendererState getState() {
    	return (CheckboxRendererState) super.getState();
    }
    
}
