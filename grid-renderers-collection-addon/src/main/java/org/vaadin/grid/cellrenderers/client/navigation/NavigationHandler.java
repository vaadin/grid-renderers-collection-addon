package org.vaadin.grid.cellrenderers.client.navigation;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.widget.escalator.Cell;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widgets.Grid;

public class NavigationHandler implements KeyDownHandler {

	protected final Grid grid;
	protected final GridFocusHandler gridFocusHandler;

	protected boolean secondTimeTabWithoutShift = false;
	protected boolean secondTimeTabWithShift = false;

	public NavigationHandler(Grid grid, GridFocusHandler gridFocusHandler) {
		this.grid = grid;
		this.gridFocusHandler = gridFocusHandler;
	}

	@Override
	public void onKeyDown(final KeyDownEvent keyDownEvent) {
		final Element focusedElement = WidgetUtil.getFocusedElement();
		if (!(focusedElement.getNodeName()
			.equals("INPUT")
				|| focusedElement.getNodeName()
					.equals("BUTTON"))) {
			return;
		}

		final CellReference cellReference;
		switch (keyDownEvent.getNativeKeyCode()) {
		case KeyCodes.KEY_ESCAPE:
			keyDownEvent.preventDefault();
			keyDownEvent.stopPropagation();

			// blur input element se we get a value change
			focusedElement.blur();
			cellReference = this.grid.getCellReference(focusedElement.getParentElement());
			NavigationUtil.focusCell(	this.grid, cellReference.getRowIndex(), cellReference.getColumnIndex(),
										this.gridFocusHandler);

			break;
		case KeyCodes.KEY_UP:
			keyDownEvent.preventDefault();
			keyDownEvent.stopPropagation();

			// blur input element se we get a value change
			focusedElement.blur();
			cellReference = this.grid.getCellReference(focusedElement.getParentElement());

			int cellRowIndex = cellReference.getRowIndex() - 1;
			while (cellReference.getRowIndex() > 0) {
				// move down one row
				NavigationUtil.focusCell(	this.grid, cellRowIndex, cellReference.getColumnIndex(),
											this.gridFocusHandler);
				Cell cell = NavigationUtil.getFocusedCell(this.grid);
				if (NavigationUtil.isDisabled(cell.getElement())) {
					cellRowIndex--;
					continue;
				}
				NavigationUtil.focusInputField(this.grid);
				break;
			}

			if (cellReference.getRowIndex() <= 0) {
				// refocus element in case we can't move down
				focusedElement.focus();
			}
			break;
		case KeyCodes.KEY_ENTER:
		case KeyCodes.KEY_DOWN:
			keyDownEvent.preventDefault();
			keyDownEvent.stopPropagation();

			// blur input element se we get a value change
			focusedElement.blur();
			cellReference = this.grid.getCellReference(focusedElement.getParentElement());

			cellRowIndex = cellReference.getRowIndex() + 1;
			while (cellRowIndex < this.grid.getDataSource()
				.size()) {
				// move down one row
				NavigationUtil.focusCell(	this.grid, cellRowIndex, cellReference.getColumnIndex(),
											this.gridFocusHandler);
				Cell cell = NavigationUtil.getFocusedCell(this.grid);
				if (NavigationUtil.isDisabled(cell.getElement())) {
					cellRowIndex++;
					continue;
				}
				NavigationUtil.focusInputField(this.grid);
				break;
			}

			if (cellRowIndex >= this.grid.getDataSource()
				.size()) {
				// refocus element in case we can't move down
				focusedElement.focus();
			}
			break;
		case KeyCodes.KEY_TAB:
			final boolean shiftKeyDown = keyDownEvent.isShiftKeyDown();
			// check if we are in a buttons field
			if (focusedElement.getNodeName()
				.equalsIgnoreCase("button")) {
				Node element;
				if (shiftKeyDown) {
					element = focusedElement.getPreviousSibling();
				} else {
					element = focusedElement.getNextSibling();
				}
				if (element != null && element.getNodeName()
					.equalsIgnoreCase("button")) {
					// if previous element is a button too
					// let the browser handle the change
					break;
				}
			}

			cellReference = this.grid.getCellReference(getCellElement(focusedElement));
			final Element tdElement = cellReference.getElement();

			if (shiftKeyDown) {
				focusedElement.blur();

				if (NavigationUtil.isFirstCell(cellReference, tdElement, shiftKeyDown) && this.secondTimeTabWithShift) {
					this.gridFocusHandler.setSkipFocus(true);
					this.secondTimeTabWithShift = false;
					break;
				}

				this.secondTimeTabWithShift = false;

				// Prevent default
				// we have to handle the changes by ourself
				keyDownEvent.preventDefault();
				keyDownEvent.stopPropagation();

				new Timer() {
					@Override
					public void run() {

						if (NavigationUtil.isFirstCell(cellReference, tdElement, shiftKeyDown)) {
							focusedElement.focus();
							NavigationHandler.this.secondTimeTabWithShift = true;
							return;
						}

						final int cnt = NavigationUtil.getPreviousInputElementCounter(tdElement, shiftKeyDown);

						if (cnt == -1) {
							// no steppable element found in this row
							// Step down one row and to the first column
							NavigationUtil.focusLastEditableElementFromFirstElementOfRow(	NavigationHandler.this.grid,
																							cellReference.getRowIndex()
																									- 1,
																							NavigationHandler.this.gridFocusHandler,
																							shiftKeyDown);
							return;
						}

						// focus previous cell in row
						NavigationHandler.this.gridFocusHandler.setShiftKeyDown(true);
						NavigationUtil.focusCell(	NavigationHandler.this.grid, cellReference.getRowIndex(),
													NavigationHandler.this.grid.getCellReference(tdElement)
														.getColumnIndex() - cnt,
													NavigationHandler.this.gridFocusHandler);
					}
				}.schedule(100);
			} else {
				focusedElement.blur();

				if (NavigationUtil.isLastCell(cellReference, NavigationHandler.this.grid, tdElement, shiftKeyDown)
						&& this.secondTimeTabWithoutShift) {
					this.secondTimeTabWithoutShift = false;
					break;
				}

				this.secondTimeTabWithoutShift = false;

				// Prevent default
				// we have to handle the changes by ourself
				keyDownEvent.preventDefault();
				keyDownEvent.stopPropagation();

				new Timer() {
					@Override
					public void run() {
						if (NavigationUtil.isLastCell(	cellReference, NavigationHandler.this.grid, tdElement,
														shiftKeyDown)) {
							focusedElement.focus();
							NavigationHandler.this.secondTimeTabWithoutShift = true;
							return;
						}
						final int cnt = NavigationUtil.getNextInputElementCounter(tdElement, shiftKeyDown);

						if (cnt == -1) {
							// no steppable element found in this row
							// Step down one row and to the first column
							NavigationUtil.focusFirstEditableElementFromFirstElementOfRow(	NavigationHandler.this.grid,
																							cellReference.getRowIndex()
																									+ 1,
																							NavigationHandler.this.gridFocusHandler,
																							shiftKeyDown);
							return;
						}

						// focus next cell in row
						NavigationUtil.focusCell(	NavigationHandler.this.grid, cellReference.getRowIndex(),
													NavigationHandler.this.grid.getCellReference(tdElement)
														.getColumnIndex() + cnt,
													NavigationHandler.this.gridFocusHandler);
					}
				}.schedule(100);

				break;
			}
			break;
		}
	}

	/**
	 * Get the cell element even if we have to recurse one or two steps.
	 * 
	 * @param focusedElement
	 *            Element for which we want the parent cell element
	 * @return Grid cell element if found else last parent element
	 */
	private Element getCellElement(Element focusedElement) {
		if (!focusedElement.hasParentElement()) {
			return focusedElement;
		}
		if (focusedElement.getParentElement()
			.getNodeName()
			.equals("TD")) {
			return focusedElement.getParentElement();
		}

		return getCellElement(focusedElement.getParentElement());
	}

}