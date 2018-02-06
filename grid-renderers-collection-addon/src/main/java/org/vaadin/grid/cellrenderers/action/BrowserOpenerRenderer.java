package org.vaadin.grid.cellrenderers.action;

import org.vaadin.grid.cellrenderers.action.HtmlButtonRenderer.HtmlButtonRendererClickListener;
import org.vaadin.grid.cellrenderers.client.action.BrowserOpenerRendererState;
import org.vaadin.grid.cellrenderers.client.action.HtmlButtonRendererState;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.shared.ApplicationConstants;
import com.vaadin.shared.ui.BrowserWindowOpenerState;
import com.vaadin.ui.UI;

public class BrowserOpenerRenderer<T> extends HtmlButtonRenderer<T> {


    /**
     * Creates a window opener button renderer that will open windows to the url provided in the Grid cell.
     *
     * @param caption
     *            the Caption of the button, Use setHtmlAllowed(true) to allow html e.g. VaadinIcons
     * @param listener
     *            the click listener to register
     */
    public BrowserOpenerRenderer(String caption, HtmlButtonRendererClickListener<T> listener) {
    	super(listener);
    	getState().caption = caption;
    }

    /**
     * Sets the target window name that will be used. If a window has already
     * been opened with the same name, the contents of that window will be
     * replaced instead of opening a new window. If the name is
     * <code>null</code> or <code>"_blank"</code>, a new window will always be
     * opened.
     *
     * @param windowName
     *            the target name for the window
     */
    public void setWindowName(String windowName) {
        getState().target = windowName;
    }

    /**
     * Gets the target window name.
     *
     * @see #setWindowName(String)
     *
     * @return the window target string
     */
    public String getWindowName() {
        return getState(false).target;
    }

    /**
     * Sets the features for opening the window. See e.g.
     * https://developer.mozilla.org/en-US/docs/DOM/window.open#Position_and_size_features
     * for a description of the commonly supported features.
     *
     * @param features a string with window features, or <code>null</code> to use the default features.
     */
    public void setFeatures(String features) {
        getState().features = features;
    }

    /**
     * Gets the window features.
     *
     * @see #setFeatures(String)
     * @return
     */
    public String getFeatures() {
        return getState(false).features;
    }

    @Override
    protected BrowserOpenerRendererState getState() {
        return (BrowserOpenerRendererState) super.getState();
    }

    @Override
    protected BrowserOpenerRendererState getState(boolean markAsDirty) {
        return (BrowserOpenerRendererState) super.getState(markAsDirty);
    }

}
