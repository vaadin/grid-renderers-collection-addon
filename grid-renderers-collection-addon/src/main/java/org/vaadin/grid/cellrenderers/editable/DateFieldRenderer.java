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
import com.vaadin.shared.ui.datefield.Resolution;

public class DateFieldRenderer extends EditableRenderer<Date> {

	private final EditableRendererEnabled editableRendererEnabled;

	public DateFieldRenderer() {
		this(null, Resolution.DAY);
	}

	public DateFieldRenderer(final EditableRendererEnabled editableRendererEnabled,
			final Resolution dateFieldResolution) {
		super(Date.class);

		this.editableRendererEnabled = editableRendererEnabled;
		getState().dateFieldResolutionName = dateFieldResolution.name();

		registerRpc(new DateFieldRendererServerRpc() {

			@Override
			public void onChange(final String rowKey, final String columnId, final Date newValue) {

				final Object itemId = getItemId(rowKey);
				final Object columnPropertyId = getColumn(columnId).getPropertyId();

				final Item row = getParentGrid().getContainerDataSource()
					.getItem(itemId);

				@SuppressWarnings("unchecked")
				final Property<Date> cell = row.getItemProperty(columnPropertyId);

				cell.setValue(newValue);

				fireItemEditEvent(itemId, row, columnPropertyId, newValue);
			}

			@Override
			public void onRender(final CellId id) {
				getRpcProxy(DateFieldRendererClientRpc.class).setOnRenderSettings(EditableRendererUtil
					.isColumnComponentEnabled(	getItemId(id.getRowId()), getParentGrid(),
												DateFieldRenderer.this.editableRendererEnabled), id);
			}

		});
	}

	@Override
	protected DateFieldRendererState getState() {
		return (DateFieldRendererState) super.getState();
	}

}