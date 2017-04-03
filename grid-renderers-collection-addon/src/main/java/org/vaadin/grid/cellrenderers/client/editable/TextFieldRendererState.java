package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;

/**
 * @author Tatu Lund - Vaadin
 */
public class TextFieldRendererState extends SharedState{
	public String value = null;
	public boolean fitToCell = true;
}
