package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;

public class TextFieldRendererState extends SharedState {
	public String value = null;
	public boolean fitToCell = true;
	public int maxLength = -1;
}
