package org.vaadin.grid.cellrenderers.client.action;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonObject;

@Connect(org.vaadin.grid.cellrenderers.action.HtmlButtonRenderer.class)
public class HtmlButtonRendererConnector extends ClickableRendererConnector<String> {
	HtmlButtonRendererServerRpc rpc = RpcProxy.create(HtmlButtonRendererServerRpc.class, this);

    public class HtmlButtonClientRenderer extends ClickableRenderer<String, Button> {

        private boolean htmlContentAllowed = false;
        private static final String ROW_KEY_PROPERTY = "rowKey";
        private static final String COLUMN_ID_PROPERTY = "columnId";

        @Override
        public Button createWidget() {
            final Button b = GWT.create(Button.class);
            
            b.addClickHandler(new ClickHandler() {
            	@Override
            	public void onClick(ClickEvent event) {
        			MouseEventDetails mouseEventDetails = MouseEventDetailsBuilder
        	                .buildMouseEventDetails(event.getNativeEvent(),
        	                        b.getElement());
        			Element e = b.getElement();
        			rpc.onClick(e.getPropertyString(ROW_KEY_PROPERTY),
                            e.getPropertyString(COLUMN_ID_PROPERTY),mouseEventDetails);
            		event.stopPropagation();
            	}
            });
            b.setStylePrimaryName("v-nativebutton");
            return b;
        }
        public void setHtmlContentAllowed(boolean htmlContentAllowed) {
            this.htmlContentAllowed = htmlContentAllowed;
        }

        public boolean isHtmlContentAllowed() {
            return htmlContentAllowed;
        }

        @Override
        public void render(RendererCellReference cell, String text, Button button) {

			Element e = button.getElement();

            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            if(e.getPropertyString(COLUMN_ID_PROPERTY) != getColumnId(getGrid()
                    .getColumn(cell.getColumnIndex()))) {
                e.setPropertyString(COLUMN_ID_PROPERTY, getColumnId(getGrid()
                        .getColumn(cell.getColumnIndex())));
            }

            if (htmlContentAllowed) {
                button.setHTML(text);
        	} else {
        		button.setText(text);    		
            }
        	button.setEnabled(true);
        }
   	}

    @Override
    public HtmlButtonClientRenderer getRenderer() {
        return (HtmlButtonClientRenderer) super.getRenderer();
    }

    @Override
    protected HtmlButtonClientRenderer createRenderer() {
    	return new HtmlButtonClientRenderer();
    }

    @Override
    protected HandlerRegistration addClickHandler(
            RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }


    @Override
    public HtmlButtonRendererState getState() {
        return (HtmlButtonRendererState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getRenderer().setHtmlContentAllowed(getState().htmlContentAllowed);
    }

    private Grid<JsonObject> getGrid() {
        return ((GridConnector) getParent()).getWidget();
    }

}