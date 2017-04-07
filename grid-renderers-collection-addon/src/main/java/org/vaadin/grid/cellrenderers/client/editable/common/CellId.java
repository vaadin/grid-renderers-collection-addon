package org.vaadin.grid.cellrenderers.client.editable.common;

/**
 * Grid cell identification class
 *
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class CellId {
	String rowId;
	String columnId;

	public CellId() {
	}

	public CellId(String rowId, String columnId) {
		this.rowId = rowId;
		this.columnId = columnId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getRowId() {
		return this.rowId;
	}

	public String getColumnId() {
		return this.columnId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CellId) {
			CellId target = (CellId) obj;
			return target.rowId.equals(this.rowId) && target.columnId.equals(this.columnId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.rowId + this.columnId).hashCode();
	}

	@Override
	public String toString() {
		return "Row: " + this.rowId + " Column: " + this.columnId;
	}
}
