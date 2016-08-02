package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;

public class RatingStarsRendererState extends SharedState {
	public int stars = 0;
	public Double value = null;
	public boolean readOnly = true;
}
