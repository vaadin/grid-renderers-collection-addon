package org.vaadin.grid.cellrenderers;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * @author Mikael Grankvist - Vaadin
 */
public class EditableRenderer<A,T> extends ClickableRenderer<A,T> {

    protected EditableRenderer(Class<T> presentationType) {
        super(presentationType);
    }

    /**
     * Superclass for editable renderers (e.g. TextFieldRenderer, DateFieldRenderer)
     * 
     * @param presentationType Presentation type
     * @param nullRepresentation Null presentation
     */
    public EditableRenderer(Class<T> presentationType, String nullRepresentation) {
        super(presentationType, nullRepresentation);
    }

    public interface ItemEditListener extends ConnectorEventListener {

        Method ITEM_EDIT_METHOD = ReflectTools.findMethod(
                ItemEditListener.class, "itemEdited", ItemEditEvent.class);

        void itemEdited(ItemEditEvent event);
    }

    /**
     * 
     * ItemEditEvent is fired when a property value of the item in Grid is
     * being changed by EditableRenderer. You can use this to event to 
     * e.g. commit changes to database.
     *
     * @param <T> Type parameter
     */
    public static class ItemEditEvent<A,T> extends Component.Event {

        private final A item;
        private final Column<A,T> column;
        private final T newValue;

        public ItemEditEvent(Grid<A> grid, A item, Column<A,T> column, T newValue) {
            super(grid);
            this.item = item;
            this.column = column;
            this.newValue = newValue;
        }

        /**
         * Get Item of the item which was edited by a EditableRenderer
         * 
         * @return Item, you need to cast this to your type.
         */
       public A getItem() {
            return item;
        }

       /**
        * Get column which was edited.
        * 
        * @return Object, typically a String with most Containers.
        */
        public Column<A,T> getColumn() {
            return column;
        }

        /**
         * Get the value that was edited.
         * 
         * @return Object, you need to cast this to your property type.
         */
       public T getNewValue() {
            return newValue;
        }
    }

    /**
     * Add a new ItemEditEvent listener
     * 
     * @param listener The listener instance to be added
     * @return Returns registration object that can be used for listener removal
     */
    public Registration addItemEditListener(ItemEditListener listener) {
        Registration reg = addListener(ItemEditEvent.class, listener,
                ItemEditListener.ITEM_EDIT_METHOD);
        return reg;
    }

    public void fireItemEditEvent(A item, Column<A,T> column, T newValue) {
        fireEvent(new ItemEditEvent(getParentGrid(), item, column, newValue));
    }

}
