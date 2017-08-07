package org.vaadin.grid.cellrenderers.client.editable;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.ui.VCheckBox;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererConnector.TextFieldClientRenderer;
import org.vaadin.grid.cellrenderers.editable.BooleanSwitchRenderer;

import elemental.json.JsonObject;

@Connect(BooleanSwitchRenderer.class)
public class BooleanSwitchRendererConnector extends ClickableRendererConnector<Boolean> {
	
	public class BooleanSwitchClientRenderer extends ClickableRenderer<Boolean, VCheckBox> {

		@Override
		public void render(RendererCellReference rendererCellReference, Boolean value, VCheckBox checkBox) {
			if(checkBox.isEnabled() != rendererCellReference.getColumn().isEditable()) {
				checkBox.setEnabled(rendererCellReference.getColumn().isEditable() && rendererCellReference.getGrid().isEnabled());
			}
			
			checkBox.setValue(value);
			
			rendererCellReference.getElement().addClassName("unselectable");
			if (getState().txtTrue != null) {
				String text = null;
				if (value) text = getState().txtTrue;
				else text = getState().txtFalse;					
				if (text != null) checkBox.setText(text);
			} else {
		        Grid.HeaderRow headerRow = rendererCellReference.getGrid().getDefaultHeaderRow();
		        String text = "";
		        if (headerRow != null) {
		            Grid.HeaderCell headerCell = headerRow.getCell(rendererCellReference.getColumn());
		            if (headerCell != null && headerCell.getText() != null) text = headerCell.getText();
		            checkBox.setText(text);
		        }				
			}
		}

		@Override
		public VCheckBox createWidget() {
			VCheckBox checkBox = new VCheckBox();
			checkBox.addClickHandler(this);
			checkBox.getElement().removeAttribute("tabindex");
			return checkBox;
		}
	}

	@Override
	protected HandlerRegistration addClickHandler(ClickableRenderer.RendererClickHandler<JsonObject> jsonObjectRendererClickHandler) {
		return getRenderer().addClickHandler(jsonObjectRendererClickHandler);
	}

    @Override
	public BooleanSwitchClientRenderer getRenderer() {
		return (BooleanSwitchClientRenderer) super.getRenderer();
	}

    @Override
    protected BooleanSwitchClientRenderer createRenderer() {
        return new BooleanSwitchClientRenderer();
    }

    @Override
    public BooleanSwitchRendererState getState() {
    	return (BooleanSwitchRendererState) super.getState();
    }
    
}
