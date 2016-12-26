package org.vaadin.grid.cellrenderers.editable;


import com.vaadin.server.Setter;
import com.vaadin.ui.renderers.ClickableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.DateFieldRendererState;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DateFieldRenderer<T> extends ClickableRenderer<T, LocalDate> {
    public DateFieldRenderer(Setter<T, LocalDate> setter) {
        super(LocalDate.class);

        registerRpc((DateFieldRendererServerRpc) (rowKey, columnId, newValue) -> {
            T item = getParentGrid().getDataCommunicator().getKeyMapper().get(rowKey);
            LocalDate newLocalDate;
            if (newValue == null) {
                newLocalDate = null;
            } else {
                newLocalDate = Instant.ofEpochMilli(newValue.getTime()).atZone(ZoneOffset.UTC)
                        .toLocalDate();
            }
            setter.accept(item, newLocalDate);
        });
    }

    @Override
    protected DateFieldRendererState getState() {
        return (DateFieldRendererState) super.getState();
    }

}