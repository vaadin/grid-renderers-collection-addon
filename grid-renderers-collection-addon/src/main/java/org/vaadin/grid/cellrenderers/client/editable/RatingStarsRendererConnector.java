package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;
import com.vaadin.client.widgets.Grid; 
import com.google.web.bindery.event.shared.HandlerRegistration; 

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
        private static final String COLUMN_ID_PROPERTY = "columnId";
    	
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
            boolean readOnly = state.readOnly;          
            ratingStars.setReadOnly(readOnly);
            ratingStars.setMaxValue(state.stars);
            
            // Set RPC if we are in editable mode
            if (!readOnly) ratingStars.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                   	RatingStarsWidget ratingStars = (RatingStarsWidget) event.getSource();
                   	Element e = ratingStars.getElement();
                   	rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                   			e.getPropertyString(COLUMN_ID_PROPERTY),
                   			ratingStars.getValue());
                    event.stopPropagation();
                    	
                }
            });

            ratingStars.addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
                }
            });

            
   	 		return ratingStars;
   	 	}

   	 	@Override
   	 	public void render(RendererCellReference cell, Double data,
    	            RatingStarsWidget widget) {

   	 		widget.setReadOnly(getState().readOnly || !(cell.getColumn().isEditable() && cell.getGrid().isEnabled()));
   	 		Element e = widget.getElement();
   	 		
            getState().value = data;
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.

            if(e.getPropertyString(COLUMN_ID_PROPERTY) != getColumnId(getGrid()
                    .getColumn(cell.getColumnIndex()))) {
                e.setPropertyString(COLUMN_ID_PROPERTY, getColumnId(getGrid()
                        .getColumn(cell.getColumnIndex())));
            }
   	 		
   	 		if (data == null) {
   	 			widget.setValue(0.0);
   	 		} else {
   	 			widget.setValue(data);			
   	 		}
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
		return ((GridConnector) getParent()).getWidget();
	}
    	

}
