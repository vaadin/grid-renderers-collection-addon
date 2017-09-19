package org.vaadin.grid.cellrenderers;

import com.vaadin.data.Item;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Mikael Grankvist - Vaadin
 */
public class EditableRenderer<T> extends ClickableRenderer<T> {

	private static final long serialVersionUID = 5590369317672309950L;

	public enum Mode {
		SINGLE, MULTI
	}

	protected EditableRenderer(Class<T> presentationType) {
		super(presentationType);
	}

	public interface ItemEditListener<T> extends ConnectorEventListener {

		Method ITEM_EDIT_METHOD = ReflectTools.findMethod(ItemEditListener.class, "itemEdited", ItemEditEvent.class);

		void itemEdited(ItemEditEvent<T> event);
	}

	public static class ItemEditEvent<T> extends Component.Event {

		private static final long serialVersionUID = 1L;

		private final Mode mode;
		private final Object itemId;
		private final Item item;
		private final Object columnPropertyId;
		private final T newValue;
		private final Set<T> newValues;

		public ItemEditEvent(Grid grid, Object itemId, Item item, Object columnPropertyId, T newValue) {
			super(grid);
			this.mode = Mode.SINGLE;
			this.itemId = itemId;
			this.item = item;
			this.columnPropertyId = columnPropertyId;
			this.newValue = newValue;
			this.newValues = null;
		}

		public ItemEditEvent(Grid grid, Object itemId, Item item, Object columnPropertyId, Set<T> newValues) {
			super(grid);
			this.mode = Mode.MULTI;
			this.itemId = itemId;
			this.item = item;
			this.columnPropertyId = columnPropertyId;
			this.newValue = null;
			this.newValues = newValues;
		}

		public Object getItemId() {
			return this.itemId;
		}

		public Item getItem() {
			return this.item;
		}

		public Object getColumnPropertyId() {
			return this.columnPropertyId;
		}

		public T getNewValue() {
			return this.newValue;
		}

		public Mode getMode() {
			return this.mode;
		}

		public Set<T> getNewValues() {
			return this.newValues;
		}
	}

	public void addItemEditListener(ItemEditListener<T> listener) {
		addListener(ItemEditEvent.class, listener, ItemEditListener.ITEM_EDIT_METHOD);
	}

	public void removeItemEditListener(ItemEditListener<T> listener) {
		removeListener(ItemEditListener.class, listener);
	}

	/**
	 * Fires a event with single value to all listeners without any event
	 * details.
	 * 
	 * @param itemId
	 *            itemId
	 * @param item
	 *            item
	 * @param columnPropertyId
	 *            columnPropertyId
	 * @param newValue
	 *            newValue
	 */
	public void fireItemEditEvent(Object itemId, Item item, Object columnPropertyId, T newValue) {
		fireEvent(new ItemEditEvent<T>(getParentGrid(), itemId, item, columnPropertyId, newValue));
	}

	/**
	 * Fires a event with multi value to all listeners without any event
	 * details.
	 * 
	 * @param itemId
	 *            itemId
	 * @param item
	 *            item
	 * @param columnPropertyId
	 *            columnPropertyId
	 * @param newValues
	 *            newValues
	 */
	public void fireItemEditEvent(Object itemId, Item item, Object columnPropertyId, Set<T> newValues) {
		fireEvent(new ItemEditEvent<T>(getParentGrid(), itemId, item, columnPropertyId, newValues));
	}

}
