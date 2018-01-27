package org.vaadin.grid.cellrenderers.client.view;

import com.vaadin.shared.communication.SharedState;

/**
 * @author Tatu Lund - Vaadin
 */

public final class RowIndexRendererState extends SharedState {

	public boolean ordinalMode = false;
	public int offset = 0;
}
