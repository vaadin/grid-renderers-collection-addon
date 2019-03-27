package org.vaadin.grid.cellrenderers.client.editable;

import org.vaadin.grid.cellrenderers.client.shared.EditableRendererState;

/**
 * @author Tatu Lund - Vaadin
 */
public class TextFieldRendererState extends EditableRendererState {
	public String value = null;
	public boolean fitToCell = true;
	public boolean eagerChangeMode = false;
	public boolean blurChangeMode = false;
}
