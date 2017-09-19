package org.vaadin.grid.cellrenderers.client.editable;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;

import com.vaadin.shared.communication.ClientRpc;

/**
 * Server to client rpc
 *
 * @author Mikael Grankvist - Vaadin Ltd
 */
public interface DateFieldRendererClientRpc extends ClientRpc {

	/**
	 * Send to the client if some settings for it on render
	 * 
	 * @param enabled
	 *            checkbox is enabled
	 * @param id
	 *            Cell identification
	 */
	void setOnRenderSettings(boolean enabled, CellId id);
}
