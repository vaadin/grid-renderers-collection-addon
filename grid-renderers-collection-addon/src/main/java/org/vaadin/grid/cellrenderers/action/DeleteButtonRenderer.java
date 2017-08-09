package org.vaadin.grid.cellrenderers.action;

import java.lang.reflect.Method;

import org.vaadin.grid.cellrenderers.client.action.DeleteButtonRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.action.DeleteButtonRendererState;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.util.ReflectTools;

public class DeleteButtonRenderer extends ClickableRenderer<Boolean> {

    /**
     * An interface for listening to {@link DeleteRendererClickEvent renderer click
     * events}.
     *
     */
    public interface DeleteRendererClickListener extends ConnectorEventListener {

        static final Method CLICK_METHOD = ReflectTools.findMethod(
                DeleteRendererClickListener.class, "click", DeleteRendererClickEvent.class);

        /**
         * Called when a rendered button is clicked.
         *
         * @param event
         *            the event representing the click
         */
        void click(DeleteRendererClickEvent event);
    }

    /**
     * An event fired when a clickable widget rendered by a DeleteButtonRenderer is
     * clicked.
     *
     */
    public static class DeleteRendererClickEvent extends ClickEvent {

        private final Object item;
        private final Column column;

        protected DeleteRendererClickEvent(Grid source, Object item,
                Column column, MouseEventDetails mouseEventDetails) {
            super(source, mouseEventDetails);
            this.item = item;
            this.column = column;
        }

        /**
         * Returns the item of the row where the click event originated.
         *
         * @return the item of the clicked row
         */
        public Object getItem() {
            return item;
        }

        /**
         * Returns the {@link Column} where the click event originated.
         *
         * @return the column of the click event
         */
        public Column getColumn() {
            return column;
        }
    }


    /**
     * Creates a new button renderer
     * and e.g. localized Strings for meaning delete and confirm
     *
     * @param delete
     *            text meaning delete
     * @param confirm
     *            text meaning confirm
     */
    public DeleteButtonRenderer(String delete, String confirm) {
        super(Boolean.class, "");
        getState().delete = delete;
        getState().confirm = confirm;
        setupRpc();
    }

    /**
     * Creates a new delete button renderer and adds the given click listener to it
     * and e.g. localized Strings for meaning delete and confirm
     *
     * @param listener
     *            the click listener to register
     * @param delete
     *            text meaning delete
     * @param confirm
     *            text meaning confirm
     */
    public DeleteButtonRenderer(DeleteRendererClickListener listener,
            String delete, String confirm) {
        this(delete, confirm);
        addClickListener(listener);
    }

    /**
     * Creates a new delete button renderer.
     * 
     * Delete button renderer creates two stage Delete - Confirm button
     * When in confirm state "delete-confirm" stylename is set.
     */
    public DeleteButtonRenderer() {
        this("Delete","Confirm");
    }

    /**
     * Creates a new button renderer and adds the given click listener to it.
     *
     * @param listener
     *            the click listener to register
     */
    public DeleteButtonRenderer(DeleteRendererClickListener listener) {
        this(listener, "Delete", "Confirm");
    }

    private void setupRpc() {
    	registerRpc(new DeleteButtonRendererServerRpc() {
            public void onClick(String rowKey, String columnId,
                    MouseEventDetails mouseDetails) {
                fireEvent(new DeleteRendererClickEvent(getParentGrid(),
                        getItemId(rowKey), getColumn(columnId), mouseDetails));
    		}
    	});
    }
    
    @Override
    public String getNullRepresentation() {
        return super.getNullRepresentation();
    }

    @Override
    protected DeleteButtonRendererState getState() {
        return (DeleteButtonRendererState) super.getState();
    }

    @Override
    protected DeleteButtonRendererState getState(boolean markAsDirty) {
        return (DeleteButtonRendererState) super.getState(markAsDirty);
    }

    /**
     * Sets whether the data should be rendered as HTML (instead of text).
     * <p>
     * By default everything is rendered as text.
     *
     * @param htmlContentAllowed
     *            <code>true</code> to render as HTML, <code>false</code> to
     *            render as text
     */
    public void setHtmlContentAllowed(boolean htmlContentAllowed) {
        getState().htmlContentAllowed = htmlContentAllowed;
    }

    /**
     * Gets whether the data should be rendered as HTML (instead of text).
     * <p>
     * By default everything is rendered as text.
     *
     * @return <code>true</code> if the renderer renders a HTML,
     *         <code>false</code> if the content is rendered as text
     */
    public boolean isHtmlContentAllowed() {
        return getState(false).htmlContentAllowed;
    }

    /**
     * Adds a click listener to this button renderer. The listener is invoked
     * every time one of the buttons rendered by this renderer is clicked.
     *
     * @param listener
     *            the click listener to be added
     */
    public void addClickListener(DeleteRendererClickListener listener) {
        addListener(DeleteRendererClickEvent.class, listener,
        		DeleteRendererClickListener.CLICK_METHOD);
    }

    /**
     * Removes the given click listener from this renderer.
     *
     * @param listener
     *            the click listener to be removed
     */
    public void removeClickListener(DeleteRendererClickListener listener) {
        removeListener(DeleteRendererClickEvent.class, listener);
    }


}
