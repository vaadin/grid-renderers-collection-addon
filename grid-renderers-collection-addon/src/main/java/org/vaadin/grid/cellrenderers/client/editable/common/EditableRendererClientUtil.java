package org.vaadin.grid.cellrenderers.client.editable.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.UIObject;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.widgets.Grid;

import elemental.json.JsonObject;

public class EditableRendererClientUtil {

	public static final String ROW_KEY_PROPERTY = "rowKey";
	public static final String COLUMN_ID_PROPERTY = "columnId";

	public static void setElementProperties(Element e, String rowKey, Grid<JsonObject> grid, String columnId) {
		if (e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY) != rowKey) {
			e.setPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY, rowKey);
		}

		if (e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY) != columnId) {
			e.setPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY, columnId);
		}
	}

	/**
	 * Create cell identification for current ComboBox on row and column.
	 *
	 * @param uiObject
	 *            uiObject to get cell identification for
	 * @return CellId for ComboBox
	 */
	public static CellId getCellId(UIObject uiObject) {
		Element e = uiObject.getElement();
		return new CellId(e.getPropertyString(ROW_KEY_PROPERTY), e.getPropertyString(COLUMN_ID_PROPERTY));
	}

	public static Grid<JsonObject> getGridFromParent(ServerConnector parent) {
		return ((GridConnector) parent).getWidget();
	}

}
