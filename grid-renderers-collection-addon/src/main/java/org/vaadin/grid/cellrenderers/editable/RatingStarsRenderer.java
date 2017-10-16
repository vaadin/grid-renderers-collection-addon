package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

/**
 * 
 * @author Tatu Lund
 *
 */
public class RatingStarsRenderer<T> extends EditableRenderer<T,Double> {

    public RatingStarsRenderer(Setter<T, Double> setter, int stars) {
        super(Double.class);
        setupRatingStarsRenderer(setter, stars, -1, -1);
    }
    
    public RatingStarsRenderer(Setter<T, Double> setter, int stars, int width, int height) {
        super(Double.class);
        setupRatingStarsRenderer(setter, stars, width, height);
    }
    
    private void setupRatingStarsRenderer(final Setter<T, Double> setter, int stars, int width, int height) {

        getState().stars = stars;

    	// Use RPC only if needed
        registerRpc(new RatingStarsRendererServerRpc() {

            public void onChange(String rowKey, Double newValue) {

            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T,Double> column = getParent();
                setter.accept(item, newValue);
            	grid.getDataProvider().refreshItem(item);

                fireItemEditEvent(item, column, newValue);
            }

        });
    }

    @Override
    protected RatingStarsRendererState getState() {
    	return (RatingStarsRendererState) super.getState();
    }

    /**
     * Toggle Renderer to be editable / non-editable (=true). Default is editable. 
     * 
     * @param readOnly Boolean value
     */
    public void setReadOnly(boolean readOnly) {
    	getState().readOnly = readOnly;
    }
    
    /**
     * Returns if Renderer is editable or non-editable at the moment.
     * 
     * @return Boolean value
     */
    public boolean isReadOnly() {
    	return getState().readOnly;
    }
    
}
