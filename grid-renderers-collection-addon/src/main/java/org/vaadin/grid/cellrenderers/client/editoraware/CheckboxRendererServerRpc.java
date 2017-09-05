package org.vaadin.grid.cellrenderers.client.editoraware;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Tatu Lund - Vaadin
 */
public interface CheckboxRendererServerRpc extends ServerRpc {
    public void onChange(String rowKey, Boolean newValue);

}
