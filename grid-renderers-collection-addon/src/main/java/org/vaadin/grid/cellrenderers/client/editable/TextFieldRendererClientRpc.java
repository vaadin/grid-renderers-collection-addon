package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ClientRpc;

public interface TextFieldRendererClientRpc extends ClientRpc {

	void setEnabled(boolean enabled, String rowKey);
}
