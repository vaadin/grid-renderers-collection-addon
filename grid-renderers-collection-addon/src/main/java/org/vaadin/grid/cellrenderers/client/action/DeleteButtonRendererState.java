package org.vaadin.grid.cellrenderers.client.action;

import com.vaadin.shared.communication.SharedState;

public class DeleteButtonRendererState extends SharedState {
    public boolean htmlContentAllowed = false;
    public String delete = "Delete";
    public String confirm = "Confirm";
}
