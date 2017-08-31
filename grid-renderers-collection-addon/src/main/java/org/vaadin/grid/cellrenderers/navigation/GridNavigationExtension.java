package org.vaadin.grid.cellrenderers.navigation;

import com.vaadin.ui.Grid;

import elemental.json.JsonObject;

/**
 * Extension to grid that allows navigation between text fields in grid.
 */
public class GridNavigationExtension<T> extends Grid.AbstractGridExtension<T> {

    private GridNavigationExtension(final Grid<T> grid) {
        super.extend(grid);
    }

    public static GridNavigationExtension extend(Grid<?> grid) {
        return new GridNavigationExtension(grid);
    }

	@Override
	public void generateData(T item, JsonObject jsonObject) {
		// TODO Auto-generated method stub
	}

}