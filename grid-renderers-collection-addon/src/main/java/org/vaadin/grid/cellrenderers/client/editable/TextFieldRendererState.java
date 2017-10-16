package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

/**
 * @author Tatu Lund - Vaadin
 */
public class TextFieldRendererState extends ClickableRendererState {
	public String value = null;
	public boolean fitToCell = true;
	public boolean readOnly = false;
}
