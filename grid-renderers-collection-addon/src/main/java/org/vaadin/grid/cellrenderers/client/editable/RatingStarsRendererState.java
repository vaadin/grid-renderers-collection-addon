package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;

/**
 * @author Tatu Lund - Vaadin
 */
public class RatingStarsRendererState extends SharedState {
	public int stars = 0;
	public Double value = null;
	public boolean readOnly = true;
}
