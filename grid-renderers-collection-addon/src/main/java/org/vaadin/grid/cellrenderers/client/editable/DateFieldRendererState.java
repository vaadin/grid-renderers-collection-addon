package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.datefield.Resolution;

/**
 * @author Tatu Lund - Vaadin
 */
public class DateFieldRendererState extends SharedState{
	public Date value = null;
	public boolean blurChangeMode = false;
	public Resolution dateResolution = Resolution.DAY;
}
