package org.vaadin.grid.cellrenderers.client.editable;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.communication.SharedState;

/**
 * 
 * @author Tatu Lund
 *
 */
public class SimpleSelectRendererState extends SharedState {
    public List<String> dropDownList = new ArrayList<String>();
    public String title;
}