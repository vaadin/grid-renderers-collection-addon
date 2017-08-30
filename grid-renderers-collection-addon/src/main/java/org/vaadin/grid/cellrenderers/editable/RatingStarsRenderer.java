package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid.Column;

/**
 * 
 * @author Tatu Lund
 *
 */
public class RatingStarsRenderer<T> extends EditableRenderer<T,Double> {

    public RatingStarsRenderer(int stars, Setter<T, Double> setter) {
        super(Double.class);
        setupRatingStarsRenderer(stars, setter, false, -1, -1);
    }
    
    public RatingStarsRenderer(int stars, Setter<T, Double> setter, int width, int height) {
        super(Double.class);
        setupRatingStarsRenderer(stars, setter, false, width, height);
    }
    
    public void setupRatingStarsRenderer(int stars, final Setter<T, Double> setter, boolean readOnly, int width, int height) {

        getState().stars = stars;
        getState().readOnly = readOnly;

    	// Use RPC only if needed
        if (!readOnly) registerRpc(new RatingStarsRendererServerRpc() {

            public void onChange(String rowKey, String columnId, Double newValue) {

            	T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T,Double> column = getParent();
            	
//                Object columnPropertyId = getColumn(columnId).getPropertyId();
//
//                Item row = getParentGrid().getContainerDataSource().getItem(itemId);
//
//                @SuppressWarnings("unchecked")
//                Property<Double> cell = (Property<Double>) row.getItemProperty(columnPropertyId);

                setter.accept(item, newValue);

                fireItemEditEvent(item, column, newValue);
            }

        });
    }

    @Override
    protected RatingStarsRendererState getState() {
    	return (RatingStarsRendererState) super.getState();
    }
    	
}
