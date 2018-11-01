package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import org.vaadin.grid.cellrenderers.client.shared.EditableRendererState;

import com.vaadin.shared.ui.datefield.DateResolution;

/**
 * @author Tatu Lund - Vaadin
 */
public class DateFieldRendererState extends EditableRendererState {
	public Date value = null;
	public boolean blurChangeMode = false;
	public DateResolution dateResolution = DateResolution.DAY;
}
