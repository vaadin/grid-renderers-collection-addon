package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Tatu Lund - Vaadin
 */
public interface DateFieldRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String columnId, Date newValue);
}
