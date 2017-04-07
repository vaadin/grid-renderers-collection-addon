package org.vaadin.grid.cellrenderers.editable;

import java.util.Date;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererClientRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;
import org.vaadin.grid.cellrenderers.client.editable.common.CellId;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererEnabled;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class DateFieldRenderer extends EditableRenderer<Date> {

	private final EditableRendererEnabled editableRendererEnabled;

	public DateFieldRenderer() {
		this(null);
	}

	public DateFieldRenderer(EditableRendererEnabled editableRendererEnabled) {
		super(Date.class);

		this.editableRendererEnabled = editableRendererEnabled;

		registerRpc(new DateFieldRendererServerRpc() {

			@Override
			public void onChange(String rowKey, String columnId, Date newValue) {

				Object itemId = getItemId(rowKey);
				Object columnPropertyId = getColumn(columnId).getPropertyId();

				Item row = getParentGrid().getContainerDataSource()
					.getItem(itemId);

				@SuppressWarnings("unchecked")
				Property<Date> cell = (Property<Date>) row.getItemProperty(columnPropertyId);

				cell.setValue(newValue);

				fireItemEditEvent(itemId, row, columnPropertyId, newValue);
			}

			@Override
			public void onRender(CellId id) {
				getRpcProxy(DateFieldRendererClientRpc.class)
					.setEnabled(EditableRendererUtil.isColumnComponentEnabled(	getItemId(id.getRowId()), getParentGrid(),
																				DateFieldRenderer.this.editableRendererEnabled),
								id);
			}

		});
	}

	@Override
	protected DateFieldRendererState getState() {
		return (DateFieldRendererState) super.getState();
	}

}