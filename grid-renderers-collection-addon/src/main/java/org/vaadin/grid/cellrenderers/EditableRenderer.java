package org.vaadin.grid.cellrenderers;

import com.vaadin.data.Item;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * @author Mikael Grankvist - Vaadin
 */
public class EditableRenderer<T> extends ClickableRenderer<T> {

    protected EditableRenderer(Class<T> presentationType) {
        super(presentationType);
    }

    public EditableRenderer(Class<T> presentationType, String nullRepresentation) {
        super(presentationType, nullRepresentation);
    }

    public interface ItemEditListener extends ConnectorEventListener {

        Method ITEM_EDIT_METHOD = ReflectTools.findMethod(
                ItemEditListener.class, "itemEdited", ItemEditEvent.class);

        void itemEdited(ItemEditEvent event);
    }

    public static class ItemEditEvent<T> extends Component.Event {

        private final Object itemId;
        private final Item item;
        private final Object columnPropertyId;
        private final T newValue;

        public ItemEditEvent(Grid grid, Object itemId, Item item, Object columnPropertyId, T newValue) {
            super(grid);
        this.itemId = itemId;
            this.item = item;
            this.columnPropertyId = columnPropertyId;
            this.newValue = newValue;
        }

        public Object getItemId() {
            return itemId;
        }

        public Item getItem() {
            return item;
        }

        public Object getColumnPropertyId() {
            return columnPropertyId;
        }

        public T getNewValue() {
            return newValue;
        }
    }

    public void addItemEditListener(ItemEditListener listener) {
        addListener(ItemEditEvent.class, listener,
                ItemEditListener.ITEM_EDIT_METHOD);
    }

    public void removeItemEditListener(ItemEditListener listener) {
        removeListener(ItemEditListener.class, listener);
    }

    /**
     * Fires a event to all listeners without any event details.
     *
     */
    public void fireItemEditEvent(Object itemId, Item item, Object columnPropertyId, T newValue) {
        fireEvent(new ItemEditEvent(getParentGrid(), itemId, item, columnPropertyId, newValue));
    }
}
