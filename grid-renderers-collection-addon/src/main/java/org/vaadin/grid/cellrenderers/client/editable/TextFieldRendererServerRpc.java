package org.vaadin.grid.cellrenderers.client.editable;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;

import com.vaadin.shared.communication.ServerRpc;

public interface TextFieldRendererServerRpc extends ServerRpc {

    public void onChange(String rowKey, String columnId, String newValue);

    /**
     * workaround to set enabled of component correctly
     *
     * @param id
     *            Cell identification
     */
    void onRender(CellId id);
}
