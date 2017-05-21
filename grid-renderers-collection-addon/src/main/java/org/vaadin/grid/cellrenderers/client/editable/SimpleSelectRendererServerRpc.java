package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ServerRpc;
/**
 * 
 * @author Tatu Lund
 *
 */
public interface SimpleSelectRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String columnId, String newValue);
}