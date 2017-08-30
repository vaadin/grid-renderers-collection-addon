package org.vaadin.grid.cellrenderers.client.editable;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

/**
 * 
 * @author Tatu Lund
 *
 */
public class SimpleSelectRendererState extends ClickableRendererState {
    public List<String> dropDownList = new ArrayList<String>();
    public String title;
}