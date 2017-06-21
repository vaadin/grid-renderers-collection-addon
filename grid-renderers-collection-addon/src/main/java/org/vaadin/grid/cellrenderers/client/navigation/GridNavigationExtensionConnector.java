package org.vaadin.grid.cellrenderers.client.navigation;

import org.vaadin.grid.cellrenderers.navigation.GridNavigationExtension;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

/**
 * @author Mikael Grankvist - Vaadin
 */
@Connect(GridNavigationExtension.class)
public class GridNavigationExtensionConnector extends AbstractExtensionConnector {

	@Override
	protected void extend(ServerConnector target) {

		Grid grid = ((GridConnector) target).getWidget();
		GridFocusHandler gridFocusHandler = new GridFocusHandler(grid);
		grid.addDomHandler(gridFocusHandler, FocusEvent.getType());
		grid.addDomHandler(new GridClickHandler(grid, gridFocusHandler), ClickEvent.getType());
		grid.addDomHandler(new NavigationHandler(grid, gridFocusHandler), KeyDownEvent.getType());
	}
}