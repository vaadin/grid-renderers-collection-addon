package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.ui.VCheckBox;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import org.vaadin.grid.cellrenderers.editable.BooleanSwitchRenderer;

import elemental.json.JsonObject;

@Connect(BooleanSwitchRenderer.class)
public class BooleanSwitchRendererConnector extends ClickableRendererConnector<Boolean> {
	BooleanSwitchRendererServerRpc rpc = RpcProxy.create(
			BooleanSwitchRendererServerRpc.class, this);

	public class BooleanSwitchClientRenderer extends ClickableRenderer<Boolean, VCheckBox> {

        private static final String ROW_KEY_PROPERTY = "rowKey";

		@Override
		public void render(RendererCellReference rendererCellReference, Boolean value, VCheckBox checkBox) {
			checkBox.setValue(value);		

			checkBox.setEnabled(!getState().readOnly);
			
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

        	Element e = checkBox.getElement();
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) rendererCellReference.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) rendererCellReference.getRow()));
            }

		}

		@Override
		public VCheckBox createWidget() {
			VCheckBox checkBox = new VCheckBox();
			checkBox.addClickHandler(this);
			checkBox.getElement().removeAttribute("tabindex");

			checkBox.sinkBitlessEvent(BrowserEvents.CLICK);
			checkBox.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);

			checkBox.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                	if (!getState().readOnly) {
                		VCheckBox checkBox = (VCheckBox) event.getSource();
                		Element e = checkBox.getElement();
                		checkBox.setValue(!checkBox.getValue());
                		rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
                				checkBox.getValue());
                	}
                }
            });
			checkBox.addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
                }
            });			
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
