package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

/**
 * @author Tatu Lund - Vaadin
 */
public class DateFieldRendererState extends ClickableRendererState {
	public Date value = null;
	public boolean readOnly = false;
	public boolean blurChangeMode = false;
	public DateResolution dateResolution = DateResolution.DAY;
}
