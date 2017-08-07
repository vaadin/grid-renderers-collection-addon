package org.vaadin.grid.cellrenderers.client.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.widgets.Grid;

public class GridClickHandler implements ClickHandler {

	protected final Grid grid;
	protected final GridFocusHandler gridFocusHandler;

	public GridClickHandler(Grid grid, GridFocusHandler gridFocusHandler) {
		this.grid = grid;
		this.gridFocusHandler = gridFocusHandler;
	}

	@Override
	public void onClick(ClickEvent event) {
		this.gridFocusHandler.cancelFocusTimer();
	}

}