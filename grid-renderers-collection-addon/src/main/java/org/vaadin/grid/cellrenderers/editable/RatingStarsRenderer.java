package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererClientRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererClientRpc;

import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

/**
 * RatingStarsRenderer is renderer for rating stars selection of {@link EditableRenderer} type.
 * It creates editable RatingStars field column in Grid. 
 * 
 * @see Grid#addColumn(String, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * 
 * @param <T> Bean type of the Grid where this Renderer is being used
 * 
 * @author Tatu Lund
 */
public class RatingStarsRenderer<T> extends EditableRenderer<T,Double> {

	/**
	 * Constructor for RatingStarsRenderer
	 * 
	 * @param setter Setter function from underlying Bean which sets the value
	 * @param stars Number of stars used
	 */
    public RatingStarsRenderer(Setter<T, Double> setter, int stars) {
        super(Double.class);
        setupRatingStarsRenderer(setter, stars, -1, -1);
    }
    
    /**
	 * Constructor for RatingStarsRenderer with more parameters
     * 
	 * @param setter Setter function from underlying Bean which sets the value
	 * @param stars Number of stars used
     * @param width Width of the field in pixels
     * @param height Height of the field in pixels
     */
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

			@Override
			public void applyIsEnabledCheck(String rowKey) {
            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	if (item != null) {
            		Boolean isDisabled = isRowDisabled(rowKey);
        			boolean result = applyIsEnabledProvider(item,rowKey);
            		if (isDisabled == null) {
            			getRPC().setEnabled(result,rowKey);
            		} else if (isDisabled != result || result == false) {
            			getRPC().setEnabled(result,rowKey);
            		}
            	}
			}

        });
    }

    @Override
    protected RatingStarsRendererState getState() {
    	return (RatingStarsRendererState) super.getState();
    }
    
    private RatingStarsRendererClientRpc getRPC() {
        return getRpcProxy(RatingStarsRendererClientRpc.class);
    }
}
