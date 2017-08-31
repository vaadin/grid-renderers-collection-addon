package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Tatu Lund - Vaadin
 */
public interface TextFieldRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String newValue);
}
