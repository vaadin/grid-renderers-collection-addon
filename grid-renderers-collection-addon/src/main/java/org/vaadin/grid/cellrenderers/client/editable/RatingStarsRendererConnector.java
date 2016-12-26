package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.grid.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;
import elemental.json.JsonObject;
import org.vaadin.teemu.ratingstars.gwt.client.RatingStarsWidget;

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

            ratingStars.sinkBitlessEvent(BrowserEvents.CLICK);
            ratingStars.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);
   	 		
            // Set widget configuration
            boolean readOnly = getState().readOnly;          
            ratingStars.setReadOnly(readOnly);
            ratingStars.setMaxValue(getState().stars);
            
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
