package org.vaadin.grid.cellrenderers.client.editable;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;

import com.vaadin.shared.communication.ClientRpc;

/**
 * Server to client rpc
 *
 * @author Mikael Grankvist - Vaadin Ltd
 */
public interface TextFieldRendererClientRpc extends ClientRpc {

	/**
	 * Send to the client if some settings for it on render
	 * 
	 * @param enabled
	 *            checkbox is enabled
	 * @param cursorPos
	 *            current cursorPos in field (-1: no position)
	 * @param id
	 *            Cell identification
	 */
	void setOnRenderSettings(boolean enabled, int cursorPos, CellId id);
}
