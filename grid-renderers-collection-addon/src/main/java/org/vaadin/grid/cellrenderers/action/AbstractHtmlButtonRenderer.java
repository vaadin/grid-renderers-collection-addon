package org.vaadin.grid.cellrenderers.action;

import java.lang.reflect.Method;

import org.vaadin.grid.cellrenderers.client.action.HtmlButtonRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.action.HtmlButtonRendererState;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

public abstract class AbstractHtmlButtonRenderer<T,A> extends ClickableRenderer<T,A> {

    /**
     * An interface for listening to {@link HtmlButtonRendererClickEvent renderer click
     * events}.
     *
     */
    public interface HtmlButtonRendererClickListener<T> extends ConnectorEventListener {

        static final Method CLICK_METHOD = ReflectTools.findMethod(
        		HtmlButtonRendererClickListener.class, "click", HtmlButtonRendererClickEvent.class);

        /**
         * Called when a rendered button is clicked.
         *
         * @param event
         *            the event representing the click
         */
        void click(HtmlButtonRendererClickEvent<T> event);
    }

    /**
     * An event fired when a clickable widget rendered by a HtmlButtonRenderer is
     * clicked.
     *
     */
    public static class HtmlButtonRendererClickEvent<T> extends ClickEvent {

        private final T item;
        private final Column column;

        protected HtmlButtonRendererClickEvent(Grid source, T item,
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
        public T getItem() {
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
     * Creates a new html button renderer and adds the given click listener to it
     *
     * @param listener
     *            the click listener to register
     */
    public AbstractHtmlButtonRenderer(HtmlButtonRendererClickListener listener) {
        super((Class<A>) Object.class);
        addClickListener(listener);
        setupRpc();
    }

    /**
     * Creates a new html button renderer.
     *
     */
    public AbstractHtmlButtonRenderer() {
        super((Class<A>) Object.class);
        setupRpc();
    }

    private void setupRpc() {
    	registerRpc(new HtmlButtonRendererServerRpc() {
            public void onClick(String rowKey, MouseEventDetails mouseDetails) {
            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T, A> column = getParent();
            	fireEvent(new HtmlButtonRendererClickEvent<T>(grid, item, column, mouseDetails));
    		}
    	});
    }

    /**
     * Set tooltip text for the button
     * 
     * @param tooltip The tooltip text to be used
     */
    public void setDescription(String tooltip) {
    	if (tooltip != null || !tooltip.equals("")) {
    		getState().tooltip = tooltip;
    		getState().enableTooltip = true;
    	} else {
    		getState().enableTooltip = false;
    	}
    }

    /**
     * Get current tooltip. 
     * 
     * @return The tooltip as string.
     */
    public String getDescription() {
   		return getState().tooltip;    	
    }    

    /**
     * Toggle tooltip on/off
     * 
     * @param enableTooltip If set to true tooltip is shown
     */
    public void setTooltipEnabled(boolean enableTooltip) {
   		getState().enableTooltip = enableTooltip;    	
    }    
    
    /**
     * Check if tooltip is enable
     * 
     * @return Current state of tooltip
     */
    public boolean isTooltipEnabled() {
   		return getState().enableTooltip;    	
    }    
    
    
    @Override
    protected HtmlButtonRendererState getState() {
        return (HtmlButtonRendererState) super.getState();
    }

    @Override
    protected HtmlButtonRendererState getState(boolean markAsDirty) {
        return (HtmlButtonRendererState) super.getState(markAsDirty);
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
	 * @return Returns Registration object that can be used for listener removal 
     */
    public Registration addClickListener(HtmlButtonRendererClickListener listener) {
        return addListener(HtmlButtonRendererClickEvent.class, listener,
        		HtmlButtonRendererClickListener.CLICK_METHOD);
    }
    
}
