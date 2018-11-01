package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Tatu Lund - Vaadin
 */
public interface RatingStarsRendererServerRpc extends ServerRpc {
    public void onChange(String rowKey, Double newValue);
    public void applyIsEnabledCheck(String rowKey);

}
