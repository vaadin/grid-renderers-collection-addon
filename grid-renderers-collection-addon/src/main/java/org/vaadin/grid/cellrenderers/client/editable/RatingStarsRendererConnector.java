package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.grid.ColumnConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;
import com.vaadin.client.widgets.Grid; 

import elemental.json.JsonObject;

import org.vaadin.teemu.ratingstars.gwt.client.RatingStarsWidget;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(org.vaadin.grid.cellrenderers.editable.RatingStarsRenderer.class)
public class RatingStarsRendererConnector extends ClickableRendererConnector<Double> {
    RatingStarsRendererServerRpc rpc = RpcProxy.create(
            RatingStarsRendererServerRpc.class, this);

    public class RatingStarsClientRenderer extends ClickableRenderer<Double, RatingStarsWidget> {

        private static final String ROW_KEY_PROPERTY = "rowKey";
    	private boolean readOnly = false;
        private Double value = null;
    	
    	@Override
   	 	public RatingStarsWidget createWidget() {
   	 		RatingStarsWidget ratingStars = GWT.create(RatingStarsWidget.class);

   	 		// The input element is injected to widget so that keyboard navigation
   	 		// extension recognizes RatingStars as editable. 
   	 		Element input = DOM.createButton();
   	 		input.getStyle().setWidth(1, Unit.PX);
   	 		input.getStyle().setHeight(1, Unit.PX);
   	 		input.getStyle().setBorderStyle(BorderStyle.NONE);
   	 		input.getStyle().setBackgroundColor("transparent");
   	 		ratingStars.getElement().appendChild(input);
   	 		
   	 		RatingStarsRendererState state = getState();
   	 		ratingStars.setWidth("100%");
            if (state.height > -1) ratingStars.setWidth(state.height+"px");
            else ratingStars.setHeight("100%");
            if (state.width > -1) ratingStars.setWidth(state.width+"px");
            else ratingStars.setWidth("100%");

            ratingStars.sinkBitlessEvent(BrowserEvents.CLICK);
            ratingStars.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);
   	 		
            // Set widget configuration
            readOnly = state.readOnly;          
            ratingStars.setReadOnly(readOnly);
            ratingStars.setMaxValue(state.stars);
            
            // Set RPC if we are in editable mode
            if (!readOnly) ratingStars.addClickHandler(event -> {
            	RatingStarsWidget rs = (RatingStarsWidget) event.getSource();
            	Element e = rs.getElement();
           		Double newValue = rs.getValue();
           		if (value != null && !value.equals(newValue)) {
           			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
           					newValue);
           			value = newValue;
           		}
            	event.stopPropagation();
            });

            ratingStars.addMouseDownHandler(event -> {
            	event.stopPropagation();
            });

            ratingStars.addFocusHandler(event -> {
            	RatingStarsWidget field = (RatingStarsWidget) event.getSource();
            	value = field.getValue();
            });

			registerRpc(RatingStarsRendererClientRpc.class,
					new RatingStarsRendererClientRpc() {
						@Override
						public void setEnabled(boolean enabled, String rowKey) {
	                		Element e = ratingStars.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								ratingStars.setReadOnly(getGrid().isEnabled() || !enabled);
							}
						}

						@Override
						public void switchEnabled(String rowKey) {
	                		Element e = ratingStars.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								readOnly = !readOnly;
								ratingStars.setReadOnly(getGrid().isEnabled() || readOnly);
							}
						}
			});
                       
   	 		return ratingStars;
   	 	}

   	 	@Override
   	 	public void render(RendererCellReference cell, Double data,
    	            RatingStarsWidget widget) {

   	 		widget.setReadOnly(!getGrid().isEnabled() || getState().readOnly);
   	 		Element e = widget.getElement();
   	 		
            getState().value = data;

            if (data == null) {
   	 			widget.setValue(0.0);
   	 		} else {
   	 			widget.setValue(data);			
   	 		}
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
			if (getState().hasIsEnabledProvider) rpc.applyIsEnabledCheck(e.getPropertyString(ROW_KEY_PROPERTY));
   	 		
   	 	}

    }
    
    @Override
    protected RatingStarsClientRenderer createRenderer() {
        return new RatingStarsClientRenderer();
    }

    @Override
    public RatingStarsClientRenderer getRenderer() {
        return (RatingStarsClientRenderer) super.getRenderer();
    }

    @Override
    public RatingStarsRendererState getState() {
    	return (RatingStarsRendererState) super.getState();
    }

    @Override
    protected HandlerRegistration addClickHandler(
            RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }
    
    private Grid<JsonObject> getGrid() {
    	ColumnConnector column = (ColumnConnector) getParent();
        return column.getParent().getWidget();
    }
    	

}
