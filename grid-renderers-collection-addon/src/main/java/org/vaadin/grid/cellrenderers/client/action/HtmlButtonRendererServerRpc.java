package org.vaadin.grid.cellrenderers.client.action;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface HtmlButtonRendererServerRpc extends ServerRpc {
	
	public void onClick(String rowKey, String columnId, MouseEventDetails mouseEventDetails);
}
