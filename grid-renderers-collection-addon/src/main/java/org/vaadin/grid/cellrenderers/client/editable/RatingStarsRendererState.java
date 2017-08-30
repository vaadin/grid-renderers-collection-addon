package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

/**
 * @author Tatu Lund - Vaadin
 */
public class RatingStarsRendererState extends ClickableRendererState {
	public int stars = 0;
	public Double value = null;
	public boolean readOnly = true;
	public int width = -1;
	public int height = -1;
}
