package org.vaadin.grid.cellrenderers;

import com.vaadin.data.Item;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

import org.vaadin.grid.cellrenderers.client.shared.EditableRendererState;

/**
 * Superclass for editable renderers (e.g. TextFieldRenderer, DateFieldRenderer)
 *
 * This a base class for EditableRenderers defining some common methods and
 * edit eventing mechanism
 * 
 * @see EditableRenderer#addItemEditListener(ItemEditListener)
 * @see EditableRenderer#setReadOnly(boolean)
 * @see EditableRenderer#isReadOnly()
 *  
 * @param <T> Type of the Rendered value
 *
 * @author Mikael Grankvist and Tatu Lund - Vaadin
 */
public class EditableRenderer<T> extends ClickableRenderer<T> {

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

        /**
         * Get ItemId of the item which was edited by a EditableRenderer
         * 
         * @return Object, you need to cast this to your type.
         */
        public Object getItemId() {
            return itemId;
        }

        /**
         * Get Item of the item which was edited by a EditableRenderer
         * 
         * @return Item, you need to cast this to your type.
         */
       public Item getItem() {
            return item;
        }

       /**
        * Get propety name which was edited.
        * 
        * @return Object, typically a String with most Containers.
        */
        public Object getColumnPropertyId() {
            return columnPropertyId;
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
     */
    public void addItemEditListener(ItemEditListener listener) {
        addListener(ItemEditEvent.class, listener,
                ItemEditListener.ITEM_EDIT_METHOD);
    }

    /**
     * Remove the ItemEditEvent listener
     * 
     * @param listener The listener to be removed
     */
    public void removeItemEditListener(ItemEditListener listener) {
        removeListener(ItemEditListener.class, listener);
    }

    public void fireItemEditEvent(Object itemId, Item item, Object columnPropertyId, T newValue) {
        fireEvent(new ItemEditEvent(getParentGrid(), itemId, item, columnPropertyId, newValue));
    }
    
    /**
     * Toggle Renderer to be editable / non-editable (=true). Default is editable. 
     * 
     * @param readOnly Boolean value
     */
    public void setReadOnly(boolean readOnly) {
    	getState().readOnly = readOnly;
    }
    
    /**
     * Returns if Renderer is editable or non-editable at the moment.
     * 
     * @return Boolean value
     */
    public boolean isReadOnly() {
    	return getState().readOnly;
    }

    
    @Override
    protected EditableRendererState getState() {
    	return (EditableRendererState) super.getState();
    }

}
