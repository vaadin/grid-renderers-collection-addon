package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ServerRpc;

public interface RatingStarsRendererServerRpc extends ServerRpc {
    public void onChange(String rowKey, String columnId, Double newValue);

}
