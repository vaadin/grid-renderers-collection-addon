package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.vaadin.shared.communication.SharedState;

public class DateFieldRendererState extends SharedState {
    public Date value = null;
    public String dateFieldResolutionName = "DAY";
}
