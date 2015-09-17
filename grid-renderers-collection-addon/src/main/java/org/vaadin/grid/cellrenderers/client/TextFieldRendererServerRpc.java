package org.vaadin.grid.cellrenderers.client;

import com.vaadin.shared.communication.ServerRpc;

public interface TextFieldRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String columnId, String newValue);
}
