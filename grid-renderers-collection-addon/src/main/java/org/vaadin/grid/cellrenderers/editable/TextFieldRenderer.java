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

    public TextFieldRenderer(final Converter<String, T> preconfiguredConverter) {
        this(-1, null, preconfiguredConverter);
    }

    public TextFieldRenderer() {
        this(-1, null, null);
    }

    public TextFieldRenderer(final int maxLength) {
        this(maxLength, null, null);
    }

    public TextFieldRenderer(final EditableRendererEnabled editableRendererEnabled) {
        this(-1, editableRendererEnabled, null);
    }

    public TextFieldRenderer(final int maxLength, final EditableRendererEnabled editableRendererEnabled, final Converter<String, T> preconfiguredConverter) {
        super((Class<T>) Object.class);

        getState().maxLength = maxLength;
        this.editableRendererEnabled = editableRendererEnabled;

        registerRpc(new TextFieldRendererServerRpc() {

            @Override
            public void onChange(final String rowKey, final String columnId, final String newValue) {

                final Object itemId = getItemId(rowKey);
                final Object columnPropertyId = getColumn(columnId).getPropertyId();

                final Item row = getParentGrid().getContainerDataSource()
                    .getItem(itemId);

                @SuppressWarnings("unchecked")
                final Property<Object> cell = row.getItemProperty(columnPropertyId);

                final Class<T> targetType = (Class<T>) cell.getType();
                T value = null;
                final Converter<String, T> converter = getConverter(columnId, preconfiguredConverter);
                if (converter != null) {
                    value = converter.convertToModel(newValue, targetType, getParentGrid().getLocale());
                }
                else if (targetType == String.class) {
                    value = (T) newValue;
                }

                cell.setValue(value);
                fireItemEditEvent(itemId, row, columnPropertyId, value);
            }

            @Override
            public void onRender(final CellId id) {
                getRpcProxy(TextFieldRendererClientRpc.class).setEnabled(EditableRendererUtil
                    .isColumnComponentEnabled(getItemId(id.getRowId()), getParentGrid(), TextFieldRenderer.this.editableRendererEnabled), id);
            }

        });
    }

    protected Converter<String, T> getConverter(final String columnId, final Converter<String, T> preconfiguredConverter) {
        if (preconfiguredConverter != null) {
            return preconfiguredConverter;
        }
        return (Converter<String, T>) getColumn(columnId).getConverter();
    }

    @Override
    protected TextFieldRendererState getState() {
        return (TextFieldRendererState) super.getState();
    }

    public EditableRendererEnabled getEditableRendererEnabled() {
        return this.editableRendererEnabled;
    }

}
