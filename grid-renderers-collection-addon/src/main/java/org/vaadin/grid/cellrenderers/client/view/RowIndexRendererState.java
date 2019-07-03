package org.vaadin.grid.cellrenderers.client.view;

import com.vaadin.shared.ui.grid.renderers.AbstractRendererState;

/**
 * @author Tatu Lund - Vaadin
 */
public final class RowIndexRendererState extends AbstractRendererState {
	public int offset = 0;
	public RowIndexMode rowIndexMode = RowIndexMode.NORMAL;
}
