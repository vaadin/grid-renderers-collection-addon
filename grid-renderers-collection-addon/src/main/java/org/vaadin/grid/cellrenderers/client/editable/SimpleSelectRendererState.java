package org.vaadin.grid.cellrenderers.client.editable;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.grid.cellrenderers.client.shared.EditableRendererState;


/**
 * 
 * @author Tatu Lund
 *
 */
public class SimpleSelectRendererState extends EditableRendererState {
    public List<String> dropDownList = new ArrayList<String>();
    public String title;    

}