package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

public class RatingStarsRendererState extends ClickableRendererState {
	public int stars = 0;
	public Double value = null;
	public boolean readOnly = true;
}
