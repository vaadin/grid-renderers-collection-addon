package org.vaadin.grid.cellrenderers.editable;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererClientRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;
import org.vaadin.grid.cellrenderers.client.editable.common.CellId;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererEnabled;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererUtil;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;

public class TextFieldRenderer<T> extends EditableRenderer<T> {

	private final EditableRendererEnabled editableRendererEnabled;
	private int cursorPos;

	public TextFieldRenderer() {
		this(-1, null);
	}

	public TextFieldRenderer(int maxLength) {
		this(maxLength, null);
	}

	public TextFieldRenderer(EditableRendererEnabled editableRendererEnabled) {
		this(-1, editableRendererEnabled);
	}

	public TextFieldRenderer(int maxLength, EditableRendererEnabled editableRendererEnabled) {
		super((Class<T>) Object.class);

		getState().maxLength = maxLength;
		this.editableRendererEnabled = editableRendererEnabled;
		this.cursorPos = -1;

		registerRpc(new TextFieldRendererServerRpc() {

			@Override
			public void onChange(String rowKey, String columnId, String newValue, int cursorPos) {
				TextFieldRenderer.this.cursorPos = cursorPos;

				Object itemId = getItemId(rowKey);
				Object columnPropertyId = getColumn(columnId).getPropertyId();

				Item row = getParentGrid().getContainerDataSource()
					.getItem(itemId);

				@SuppressWarnings("unchecked")
				Property<Object> cell = (Property<Object>) row.getItemProperty(columnPropertyId);

				Class<T> targetType = (Class<T>) cell.getType();
				Converter<String, T> converter = (Converter<String, T>) getColumn(columnId).getConverter();
				T value = null;
				if (converter != null) {
					value = converter.convertToModel(newValue, targetType, getParentGrid().getLocale());
				} else if (targetType == String.class) {
					value = (T) newValue;
				}

				cell.setValue(value);
				fireItemEditEvent(itemId, row, columnPropertyId, value);
			}

			@Override
			public void onRender(CellId id) {
				getRpcProxy(TextFieldRendererClientRpc.class)
					.setOnRenderSettings(	EditableRendererUtil.isColumnComponentEnabled(getItemId(id.getRowId()),
																						getParentGrid(),
																						TextFieldRenderer.this.editableRendererEnabled),
											TextFieldRenderer.this.cursorPos, id);
			}

		});
	}

	@Override
	protected TextFieldRendererState getState() {
		return (TextFieldRendererState) super.getState();
	}

}
