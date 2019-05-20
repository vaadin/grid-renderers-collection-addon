package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.grid.ColumnConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.ui.VCheckBox;
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
        private boolean readOnly;
        
		@Override
		public void render(RendererCellReference rendererCellReference, Boolean value, VCheckBox checkBox) {
			checkBox.setValue(value);		

        	Element e = checkBox.getElement();

            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) rendererCellReference.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) rendererCellReference.getRow()));
            }

			checkBox.setEnabled(getGrid().isEnabled() && !getState().readOnly);
			if (getState().hasIsEnabledProvider) rpc.applyIsEnabledCheck(e.getPropertyString(ROW_KEY_PROPERTY));
			
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
			readOnly = getState().readOnly;
			VCheckBox checkBox = new VCheckBox();
			checkBox.addClickHandler(this);
			checkBox.getElement().removeAttribute("tabindex");

			checkBox.sinkBitlessEvent(BrowserEvents.CLICK);
			checkBox.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);

			checkBox.addClickHandler(event -> {
               	VCheckBox cb = (VCheckBox) event.getSource();
               	if (cb.isEnabled()) {
               		Element e = cb.getElement();
               		cb.setValue(!cb.getValue());
               		rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
               				cb.getValue());
                }
            });
			checkBox.addMouseDownHandler(event -> {
                event.stopPropagation();
            });			

			registerRpc(BooleanSwitchRendererClientRpc.class,
					new BooleanSwitchRendererClientRpc() {
						@Override
						public void setEnabled(boolean enabled, String rowKey) {
	                		Element e = checkBox.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								checkBox.setEnabled(getGrid().isEnabled() && enabled);
								readOnly = !enabled;								
							}
						}

						@Override
						public void switchEnabled(String rowKey) {
	                		Element e = checkBox.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								readOnly = !readOnly;
								checkBox.setEnabled(getGrid().isEnabled() && !readOnly);
							}
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
    
    private Grid<JsonObject> getGrid() {
    	ColumnConnector column = (ColumnConnector) getParent();
        return column.getParent().getWidget();
    }
}
