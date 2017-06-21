package org.vaadin.grid.cellrenderers.navigation;

import com.vaadin.ui.Grid;

/**
 * Extension to grid that allows navigation between text fields in grid.
 */
public class GridNavigationExtension extends Grid.AbstractGridExtension {

    private GridNavigationExtension(final Grid grid) {
        super.extend(grid);
    }

    public static GridNavigationExtension extend(Grid grid) {
        return new GridNavigationExtension(grid);
    }

}