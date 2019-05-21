package org.vaadin.grid.cellrenderers;

import com.vaadin.data.ValueProvider;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererState;
import org.vaadin.grid.cellrenderers.client.shared.EditableRendererState;

/**
 * Superclass for editable renderers (e.g. TextFieldRenderer, DateFieldRenderer)
 *
 * This a base class for EditableRenderers defining some common methods and
 * edit eventing mechanism
 * 
 * @see EditableRenderer#addItemEditListener(ItemEditListener)
 * @see EditableRenderer#setIsEnabledProvider(ValueProvider)
 * @see EditableRenderer#setReadOnly(boolean)
 * @see EditableRenderer#isReadOnly()
 *  
 * @param <A> Bean type of the Grid
 * @param <T> Type of the Rendered value
 *
 * @author Mikael Grankvist and Tatu Lund - Vaadin
 */
public abstract class EditableRenderer<A,T> extends ClickableRenderer<A,T> {

    protected ValueProvider<A,Boolean> isEnabledProvider;

    /**
     * Constructor
     *  
     * @param presentationType Presentation type
     */
	protected EditableRenderer(Class<T> presentationType) {
        super(presentationType);
    }

    /**
     * Constructor
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

    // Used internally
    public void fireItemEditEvent(A item, Column<A,T> column, T newValue) {
        fireEvent(new ItemEditEvent(getParentGrid(), item, column, newValue));
    }

    /**
     * Toggle Renderer to be editable / non-editable (=true). Default is editable. 
     * 
     * Note: Setting this to true will have priority over {@link EditableRenderer#setIsEnabledProvider(ValueProvider)}
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

    /**
     * Set a provider function for the renderer to control whether the field is enabled
     * or not based on function return value. This method makes it possible to have
     * selected fields to be dynamically controlled.
     * 
     * Note: Using the function will add an additional server round trip in the rendering
     * process and with slow network connections may impact Grid rendering performance.
     * 
     * @param isEnabledProvider Lambda expression or function reference of boolean type
     */
    public void setIsEnabledProvider(ValueProvider<A,Boolean> isEnabledProvider) {
    	if (isEnabledProvider != null) {
    		this.isEnabledProvider = isEnabledProvider;
    		getState().hasIsEnabledProvider = true;
    	} else {
    		this.isEnabledProvider = null;
    		getState().hasIsEnabledProvider = false;    		
    	}
    }

    
    /**
     * Set a provider function for the renderer to control whether the field is enabled or not,
     * or enabled state toggled based on function return value. This method makes it possible to have
     * selected fields to be dynamically controlled.
     * 
     * Note: Using the function will add an additional server round trip in the rendering
     * process and with slow network connections may impact Grid rendering performance.
     * 
     * When togglingMode = true field enabled status is toggled if provider function provides value
     * true.
     * 
     * @param isEnabledProvider Lambda expression or function reference of boolean type
     * @param togglingMode Set to true to use toggling mode
     */
    public void setIsEnabledProvider(ValueProvider<A,Boolean> isEnabledProvider, boolean togglingMode) {    	
    	getState().isEnabledProviderTogglingMode = togglingMode;
    	setIsEnabledProvider(isEnabledProvider);
    }    
    
    @Override
    protected EditableRendererState getState() {
    	return (EditableRendererState) super.getState();
    }
    
    
}
