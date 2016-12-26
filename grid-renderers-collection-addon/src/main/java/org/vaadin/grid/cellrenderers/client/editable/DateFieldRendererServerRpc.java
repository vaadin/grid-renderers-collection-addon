package org.vaadin.grid.cellrenderers.client.editable;

import java.time.LocalDate;
import java.util.Date;

import com.vaadin.shared.communication.ServerRpc;

public interface DateFieldRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String columnId, Date newValue);
}
