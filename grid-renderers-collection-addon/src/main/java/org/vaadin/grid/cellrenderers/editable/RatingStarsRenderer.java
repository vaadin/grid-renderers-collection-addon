package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;
import com.vaadin.data.Property;
import com.vaadin.data.Item;

/**
 * RatingStarsRenderer is renderer for rating stars selection of {@link EditableRenderer} type.
 * It creates editable RatingStars field column in Grid.
 * 
 * @author Tatu Lund
 *
 */
public class RatingStarsRenderer extends EditableRenderer<Double> {

	@Deprecated
    public RatingStarsRenderer(int stars, boolean readOnly) {
        super(Double.class);
        setReadOnly(readOnly);
        setupRatingStarsRenderer(stars, -1, -1);
    }
    
	
    public RatingStarsRenderer(int stars) {
        super(Double.class);
        setupRatingStarsRenderer(stars, -1, -1);
    }
    
    public RatingStarsRenderer(int stars, int width, int height) {
        super(Double.class);
        setupRatingStarsRenderer(stars, width, height);
    }
    
    private void setupRatingStarsRenderer(int stars, int width, int height) {

        getState().stars = stars;

    	// Use RPC only if needed
        registerRpc(new RatingStarsRendererServerRpc() {

            public void onChange(String rowKey, String columnId, Double newValue) {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<Double> cell = (Property<Double>) row.getItemProperty(columnPropertyId);

                cell.setValue(newValue);

                fireItemEditEvent(itemId, row, columnPropertyId, newValue);
            }

        });
    }

    @Override
    protected RatingStarsRendererState getState()
    {
    	return (RatingStarsRendererState) super.getState();
    }
    	
}
