package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Tatu Lund - Vaadin
 */
public interface BooleanSwitchRendererServerRpc extends ServerRpc {
    public void onChange(String rowKey, Boolean newValue);
    public void applyIsEnabledCheck(String rowKey);

}
