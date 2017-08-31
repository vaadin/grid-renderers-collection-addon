package org.vaadin.grid.cellrenderers.client.navigation;


import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.TableCellElement;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.widget.escalator.Cell;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widgets.Grid;

/**
 * Utilities for Grid keyboard navigation
 *
 * @author Mikael Grankvist - Vaadin
 */
public final class NavigationUtil {

	/**
	 * Focus into input field below given element. Note! Element needs to be a
	 * table TD element so we don't mistakenly recurse too much.
	 *
	 * @param componentElement
	 *            TD element to be searched for input and focused
	 */
	protected static void focusInputField(final Element componentElement) {
		focusInputField(componentElement, false);
	}

	/**
	 * Focus into input field below given element. Note! Element needs to be a
	 * table TD element so we don't mistakenly recurse too much.
	 *
	 * @param componentElement
	 *            TD element to be searched for input and focused
	 * @param shiftKeyDown
	 *            if shiftkey was down when event was triggered
	 */
	protected static void focusInputField(final Element componentElement, boolean shiftKeyDown) {
		if (componentElement == null || !componentElement.getNodeName()
			.equals("TD")) {
			return;
		}

		Element input = getInputElement(componentElement.getChildNodes(), shiftKeyDown);
		if (input != null) {
			WidgetUtil.focus(input);
			input.scrollIntoView();
		}
	}

	/**
	 * Recursively find an input element for given Child node list
	 *
	 * @param nodes
	 *            NodeList of child nodes for element to find input under
	 * 
	 * @return Input node if found else null.
	 */
	protected static Element getInputElement(NodeList<Node> nodes) {
		return getInputElement(nodes, false);
	}

	/**
	 * Recursively find an input element for given Child node list
	 *
	 * @param nodes
	 *            NodeList of child nodes for element to find input under
	 * @param shiftKeyDown
	 *            if shiftkey was down when event was triggered
	 * 
	 * @return Input node if found else null.
	 */
	protected static Element getInputElement(NodeList<Node> nodes, boolean shiftKeyDown) {
		shiftKeyDown = shiftKeyDown && (isActionsTD(nodes));

		if (shiftKeyDown) {
			for (int i = nodes.getLength() - 1; i >= 0; i--) {
				Element element = getInputElement(nodes, i, shiftKeyDown);
				if (element != null) {
					return element;
				}
			}
		} else {
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = getInputElement(nodes, i, shiftKeyDown);
				if (element != null) {
					return element;
				}
			}
		}

		return null;
	}

	private static boolean isActionsTD(NodeList<Node> nodes) {
		if (nodes.getLength() == 1) {
			Node node = nodes.getItem(0);
			if (isGridActionPanel((Element) node)) {
				return true;
			}
		}

		Node node = nodes.getItem(0);
		if (node == null) {
			return false;
		}

		return isGridActionPanel(node.getParentElement());
	}

	private static boolean isGridActionPanel(Element element) {
		if (element == null) {
			return false;
		}

		String className = element.getClassName();
		if (className == null) {
			return false;
		}

		return className.contains("grid-action-panel");
	}

	private static Element getInputElement(NodeList<Node> nodes, int i, boolean shiftKeyDown) {
		Node node = nodes.getItem(i);

		if (((Element) node).getPropertyBoolean("disabled")) {
			return null;
		}

		// Focus on <input> and <button> but only if they are visible.
		if ((node.getNodeName().equals("INPUT")
				|| node.getNodeName().equals("BUTTON")
				|| node.getNodeName().equals("SELECT"))
				&& !((Element) node).getStyle()
					.getDisplay()
					.equals("none")) {
			return (Element) node;
		} else if (node.getChildNodes()
			.getLength() > 0) {
			return getInputElement(node.getChildNodes(), shiftKeyDown);
		}

		return null;
	}

	protected static boolean hasInputElement(final Element componentElement, boolean shiftKeyDown) {
		if (componentElement == null || !componentElement.getNodeName()
			.equals("TD")) {
			return false;
		}

		if (isDisabled(componentElement)) {
			return false;
		}

		Element input = getInputElement(componentElement.getChildNodes(), shiftKeyDown);
		return input != null;
	}

	public static boolean isDisabled(Element componentElement) {
		if (componentElement.getChildCount() == 1) {
			Element firstChildElement = componentElement.getFirstChildElement();
			if (firstChildElement != null && (firstChildElement.hasClassName("v-filterselect")
					|| firstChildElement.hasClassName("v-datefield"))) {
				Element firstChildElementInput = (Element) firstChildElement.getChild(1);

				if (firstChildElementInput.getPropertyBoolean("disabled")) {
					return true;
				}
			}
			if (firstChildElement != null && firstChildElement.hasClassName("v-checkbox-disabled")) {
				return true;
			}
			if (firstChildElement != null && firstChildElement.getPropertyBoolean("disabled")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if cell is the first cell in the whole grid
	 *
	 * @param cellReference
	 *            Cell
	 * @param element
	 *            element
	 * @param shiftKeyDown
	 *            if shiftKey was down when event was triggered
	 * @return true if first cell in the first row
	 */
	protected static boolean isFirstCell(CellReference cellReference, Element element, boolean shiftKeyDown) {
		return isFirstRow(cellReference) && !hasPreviousInputElement(element, shiftKeyDown);
	}

	/**
	 * Check if given cell is in the first row of the grid
	 *
	 * @param cellReference
	 *            cell
	 * @return true if in first row of the grid
	 */
	protected static boolean isFirstRow(CellReference cellReference) {
		return cellReference.getRowIndex() == 0;
	}

	/**
	 * Check if given cell is in the first column of the row
	 *
	 * @param cellReference
	 *            cell
	 * @return true if in first column
	 */
	protected static boolean isFirstColumn(CellReference cellReference) {
		return cellReference.getColumnIndex() == 0;
	}

	/**
	 * Check if the cell is the last cell in the whole grid
	 *
	 * @param cellReference
	 *            Cell
	 * @param grid
	 *            Grid to check
	 * @param element
	 *            element
	 * @param shiftKeyDown
	 * 			  Shift key
	 * 
	 * @return true if the last column in the last row
	 */
	protected static boolean isLastCell(CellReference cellReference, Grid grid, Element element, boolean shiftKeyDown) {
		return isLastRow(cellReference, grid) && !hasNextInputElement(element, shiftKeyDown);
	}

	/**
	 * Check if given cell is in the last row of the grid
	 *
	 * @param cellReference
	 *            cell
	 * @param grid
	 *            Grid for cell
	 * @return true if in first row of the grid
	 */
	protected static boolean isLastRow(CellReference cellReference, Grid grid) {
		return cellReference.getRowIndex() + 1 == grid.getDataSource()
			.size();
	}

	/**
	 * Check if given cell is on the last column of the row
	 *
	 * @param cellReference
	 *            Cell
	 * @param grid
	 *            Grid for cell
	 * @return true if in last column
	 */
	protected static boolean isLastColumn(CellReference cellReference, Grid grid) {
		return cellReference.getColumnIndex() + 1 == grid.getColumnCount();
	}

	protected static void focusCell(Grid<?> grid, int rowIndex, int columnIndex, GridFocusHandler gridFocusHandler) {
		gridFocusHandler.setMouseClickEvent(false);
		gridFocusHandler.setStartAtBeginning(false);
		focusCell(grid, rowIndex, columnIndex);
		gridFocusHandler.setMouseClickEvent(true);
		gridFocusHandler.setStartAtBeginning(true);
	}

	/**
	 * Focus grid cell at rowIndex, columnIndex
	 */
	private native static void focusCell(Grid<?> grid, int rowIndex, int columnIndex) /*-{
																						grid.@com.vaadin.client.widgets.Grid::focusCell(II)(rowIndex, columnIndex);
																						}-*/;

	/**
	 * Get the currently focused cell for Grid
	 *
	 * @param grid The Grid
	 * 
	 * @return Currently focused cell
	 */
	protected native static Cell getFocusedCell(Grid<?> grid) /*-{
																var cfh = grid.@com.vaadin.client.widgets.Grid::cellFocusHandler;
																return cfh.@com.vaadin.client.widgets.Grid.CellFocusHandler::getFocusedCell()();
																}-*/;

	protected static boolean hasNextInputElement(Element element, boolean shiftKeyDown) {
		return hasNextInputElementRecursive(element.getNextSiblingElement(), shiftKeyDown);
	}

	private static boolean hasNextInputElementRecursive(Element element, boolean shiftKeyDown) {
		if (element == null) {
			return false;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return true;
		}
		return hasNextInputElementRecursive(element.getNextSiblingElement(), shiftKeyDown);
	}

	protected static int getNextInputElementCounter(Element element, boolean shiftKeyDown) {
		return getNextInputElementCounterRecursive(element.getNextSiblingElement(), 0, shiftKeyDown);
	}

	private static int getNextInputElementCounterRecursive(Element element, int cnt, boolean shiftKeyDown) {
		if (element == null) {
			return -1;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return ++cnt;
		}
		return getNextInputElementCounterRecursive(element.getNextSiblingElement(), ++cnt, shiftKeyDown);
	}

	protected static boolean hasPreviousInputElement(Element element, boolean shiftKeyDown) {
		return hasPreviousInputElementRecursive(element.getPreviousSiblingElement(), shiftKeyDown);
	}

	private static boolean hasPreviousInputElementRecursive(Element element, boolean shiftKeyDown) {
		if (element == null) {
			return false;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return true;
		}

		return hasPreviousInputElementRecursive(element.getPreviousSiblingElement(), shiftKeyDown);
	}

	protected static int getPreviousInputElementCounter(Element element, boolean shiftKeyDown) {
		return getPreviousInputElementCounterRecursive(element.getPreviousSiblingElement(), 0, shiftKeyDown);
	}

	private static int getPreviousInputElementCounterRecursive(Element element, int cnt, boolean shiftKeyDown) {
		if (element == null) {
			return -1;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return ++cnt;
		}
		return getPreviousInputElementCounterRecursive(element.getPreviousSiblingElement(), ++cnt, shiftKeyDown);
	}

	public static Element getFirstEditableElement(Element element, boolean shiftKeyDown) {
		return getFirstEditableElementRecursive(element, shiftKeyDown);
	}

	private static Element getFirstEditableElementRecursive(Element element, boolean shiftKeyDown) {
		if (element == null) {
			return null;
		}

		Element editableElement = getFirstEditableElementRecursive(element.getPreviousSiblingElement(), shiftKeyDown);

		if (editableElement != null) {
			return editableElement;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return element;
		}
		return null;
	}

	public static void focusFirstEditableElementFromFirstElementOfRow(Grid grid, int rowIndex,
			GridFocusHandler gridFocusHandler, boolean shiftKeyDown) {
		NavigationUtil.focusCell(grid, rowIndex, 0, gridFocusHandler);
		TableCellElement element = NavigationUtil.getFocusedCell(grid)
			.getElement();

		if (element == null) {
			return;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return;
		}

		int counter = getNextInputElementCounter(element, shiftKeyDown);

		if (counter > 0) {
			NavigationUtil.focusCell(grid, rowIndex, counter, gridFocusHandler);
		} else if (counter == -1) {
			focusFirstEditableElementFromFirstElementOfRow(grid, rowIndex + 1, gridFocusHandler, shiftKeyDown);
		}
	}

	public static void focusLastEditableElementFromFirstElementOfRow(Grid grid, int rowIndex,
			GridFocusHandler gridFocusHandler, boolean shiftKeyDown) {
		gridFocusHandler.setShiftKeyDown(true);

		NavigationUtil.focusCell(grid, rowIndex, grid.getColumnCount() - 1, gridFocusHandler);
		TableCellElement element = NavigationUtil.getFocusedCell(grid)
			.getElement();

		if (element == null) {
			return;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return;
		}

		int counter = getPreviousInputElementCounter(element, shiftKeyDown);

		if (counter > 0) {
			NavigationUtil.focusCell(grid, rowIndex, (grid.getColumnCount() - 1) - counter, gridFocusHandler);
		} else if (counter == -1) {
			focusLastEditableElementFromFirstElementOfRow(grid, rowIndex - 1, gridFocusHandler, shiftKeyDown);
		}

	}

	public static Element getLastEditableElement(Element element, boolean shiftKeyDown) {
		return getLastEditableElementRecursive(element, shiftKeyDown);
	}

	private static Element getLastEditableElementRecursive(Element element, boolean shiftKeyDown) {
		if (element == null) {
			return null;
		}

		Element editableElement = getLastEditableElementRecursive(element.getNextSiblingElement(), shiftKeyDown);

		if (editableElement != null) {
			return editableElement;
		}

		if (hasInputElement(element, shiftKeyDown)) {
			return element;
		}
		return null;
	}

	public static void focusInputField(final Grid<?> grid) {
		focusInputField(grid, false);
	}

	public static void focusInputField(final Grid<?> grid, final boolean shiftKeyDown) {
		// We need to delay cell focus for 2 animation frames so that the
		// escalator has time to populate the new cell.
		AnimationScheduler.get()
			.requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
				@Override
				public void execute(double timestamp) {
					AnimationScheduler.get()
						.requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
							@Override
							public void execute(double timestamp) {
								NavigationUtil.focusInputField(NavigationUtil.getFocusedCell(grid)
									.getElement(), shiftKeyDown);
							}
						});
				}
			});
	}

}