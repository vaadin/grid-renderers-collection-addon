package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;

import com.vaadin.shared.communication.ServerRpc;

public interface DateFieldRendererServerRpc extends ServerRpc {

	public void onChange(String rowKey, String columnId, Date newValue);

	/**
	 * workaround to set enabled of component correctly
	 * 
	 * @param id
	 *            Cell identification
	 */
	void onRender(CellId id);
}
