package org.vaadin.grid.cellrenderers.editable;

import com.vaadin.server.Setter;
import com.vaadin.ui.renderers.ClickableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.RatingStarsRendererState;

public class RatingStarsRenderer<T> extends ClickableRenderer<T, Double> {


    public RatingStarsRenderer(int stars, Setter<T, Double> setter) {
        super(Double.class);
        getState().stars = stars;
        getState().readOnly = setter != null;

        // Use RPC only if needed
        if (setter != null) registerRpc(new RatingStarsRendererServerRpc() {

            public void onChange(String rowKey, String columnId, Double newValue) {

                T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
                setter.accept(item, newValue);
            }

        });
    }

    @Override
    protected RatingStarsRendererState getState() {
        return (RatingStarsRendererState) super.getState();
    }

}
