package org.vaadin.grid.cellrenderers.client.action;

import com.vaadin.shared.communication.SharedState;

public class HtmlButtonRendererState extends SharedState {
    public boolean htmlContentAllowed = false;

    public String tooltip = null;

    public boolean enableTooltip = false;

}