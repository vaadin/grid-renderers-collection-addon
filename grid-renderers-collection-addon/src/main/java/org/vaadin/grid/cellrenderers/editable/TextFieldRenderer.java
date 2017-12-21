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

    public TextFieldRenderer() {
        this(-1, null);
    }

    public TextFieldRenderer(final int maxLength) {
        this(maxLength, null);
    }

    public TextFieldRenderer(final EditableRendererEnabled editableRendererEnabled) {
        this(-1, editableRendererEnabled);
    }

    public TextFieldRenderer(final int maxLength, final EditableRendererEnabled editableRendererEnabled) {
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
                final
                Property<Object> cell = row.getItemProperty(columnPropertyId);

                final Class<T> targetType = (Class<T>) cell.getType();
                final Converter<String, T> converter = (Converter<String, T>) getColumn(columnId).getConverter();
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
            public void onRender(final CellId id) {
                getRpcProxy(TextFieldRendererClientRpc.class)
                .setEnabled(	EditableRendererUtil.isColumnComponentEnabled(getItemId(id.getRowId()),
                            	                                              getParentGrid(),
                            	                                              TextFieldRenderer.this.editableRendererEnabled), id);
            }

        });
    }

    @Override
    protected TextFieldRendererState getState() {
        return (TextFieldRendererState) super.getState();
    }

}
