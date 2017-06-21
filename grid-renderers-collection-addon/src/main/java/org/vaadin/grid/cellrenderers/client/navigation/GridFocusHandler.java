package org.vaadin.grid.cellrenderers.client.navigation;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Timer;
import com.vaadin.client.widgets.Grid;

public class GridFocusHandler implements FocusHandler {

	protected final Grid grid;
	protected boolean startAtBeginning = true;
	protected boolean shiftKeyDown = false;
	protected boolean skipFocus = false;
	protected boolean mouseClickEvent = true;

	protected Timer focusTimer;

	public GridFocusHandler(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void onFocus(FocusEvent event) {
		if (this.mouseClickEvent) {
			// event is always mouseclick, so when we manually switch columns we
			// set mouseclick false to get past this and normal mouseclicks wont
			// have effects on this
			return;
		}

		final boolean shiftKey = event.getNativeEvent()
			.getShiftKey();
		final GridFocusHandler currentThis = this;
		final boolean currentStartAtBeginning = this.startAtBeginning;
		final boolean currentSkipFocus = this.skipFocus;

		if (this.focusTimer == null) {
			this.focusTimer = new Timer() {
				@Override
				public void run() {
					if (!currentSkipFocus) {
						if (currentStartAtBeginning) {
							NavigationUtil
								.focusFirstEditableElementFromFirstElementOfRow(GridFocusHandler.this.grid, 0,
																				currentThis,
																				GridFocusHandler.this.shiftKeyDown);
						}
						NavigationUtil.focusInputField(GridFocusHandler.this.grid, GridFocusHandler.this.shiftKeyDown);
						GridFocusHandler.this.shiftKeyDown = false;
					} else {
						setSkipFocus(shiftKey);
					}
				}
			};
		}
		this.focusTimer.schedule(50);
	}

	public void setStartAtBeginning(boolean startAtBeginning) {
		this.startAtBeginning = startAtBeginning;
	}

	public void setSkipFocus(boolean skipFocus) {
		this.skipFocus = skipFocus;
	}

	public void setShiftKeyDown(boolean shiftKeyDown) {
		this.shiftKeyDown = shiftKeyDown;
	}

	public void setMouseClickEvent(boolean mouseClickEvent) {
		this.mouseClickEvent = mouseClickEvent;
	}

	public void cancelFocusTimer() {
		if (this.focusTimer != null) {
			this.focusTimer.cancel();
		}
	}
}