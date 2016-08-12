package org.vaadin.grid.cellrenderers.editable;

import com.vaadin.ui.renderers.ClickableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;
import com.vaadin.data.Property;
import com.vaadin.data.Item;

public class RatingStarsRenderer extends ClickableRenderer<Double> {

    public RatingStarsRenderer(int stars, boolean readOnly) {
        super(Double.class);
        getState().stars = stars;
        getState().readOnly = readOnly;

    	// Use RPC only if needed
        if (!readOnly) registerRpc(new RatingStarsRendererServerRpc()
        {

            public void onChange(String rowKey, String columnId, Double newValue)
            {

                Object itemId = getItemId(rowKey);
                Object columnPropertyId = getColumn(columnId).getPropertyId();

                Item row = getParentGrid().getContainerDataSource().getItem(itemId);

                @SuppressWarnings("unchecked")
                Property<Double> cell = (Property<Double>) row.getItemProperty(columnPropertyId);

                cell.setValue(newValue);
            }

        });
    }

    @Override
    protected RatingStarsRendererState getState()
    {
    	return (RatingStarsRendererState) super.getState();
    }
    	
}
