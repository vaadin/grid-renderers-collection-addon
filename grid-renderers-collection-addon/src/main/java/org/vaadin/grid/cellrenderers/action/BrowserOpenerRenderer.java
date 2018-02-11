package org.vaadin.grid.cellrenderers.action;

import org.vaadin.grid.cellrenderers.client.action.BrowserOpenerRendererState;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class BrowserOpenerRenderer extends HtmlButtonRenderer {

    private static class BrowserWindowOpenerUIProvider extends UIProvider {

        private final String path;
        private final Class<? extends UI> uiClass;

        public BrowserWindowOpenerUIProvider(Class<? extends UI> uiClass,
                String path) {
            this.path = ensureInitialSlash(path);
            this.uiClass = uiClass;
        }

        private static String ensureInitialSlash(String path) {
            if (path == null) {
                return null;
            } else if (!path.startsWith("/")) {
                return '/' + path;
            } else {
                return path;
            }
        }

        @Override
        public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
            String requestPathInfo = event.getRequest().getPathInfo();
            if (path.equals(requestPathInfo)) {
                return uiClass;
            } else {
                return null;
            }
        }
    }

    private static String generateUIClassUrl(Class<? extends UI> uiClass) {
        return "popup/" + uiClass.getSimpleName();
    }
    
    private BrowserWindowOpenerUIProvider uiProvider;

    @Override
    public void attach() {
        super.attach();
        if (uiProvider != null
                && !getSession().getUIProviders().contains(uiProvider)) {
            getSession().addUIProvider(uiProvider);
        }
    }

    @Override
    public void detach() {
        if (uiProvider != null) {
            getSession().removeUIProvider(uiProvider);
        }
        super.detach();
    }

    /**
     * Set tooltip text for the button
     * 
     * @param tooltip The tooltip text to be used, if null or "", the url will be used for tooltip
     */
    @Override
    public void setDescription(String tooltip) {
    	if (tooltip != null || !tooltip.equals("")) {
    		getState().tooltip = tooltip;
    		getState().enableTooltip = true;
    	} else {
    		getState().tooltip = null;    		
    	}
    }    

    /**
     * Creates a window opener button renderer that will open windows to the uri fragment provided in the
     *  Grid cell and base UI given as constructor parameter. 
     *
     * @param uiClass
     *            the UI class to be used in opening browser window.
     * @param caption
     *            the Caption of the button, Use setHtmlAllowed(true) to allow html e.g. VaadinIcons
     * @param listener
     *            the click listener to register
     */
    public BrowserOpenerRenderer(Class<? extends UI> uiClass, String caption, HtmlButtonRendererClickListener listener) {
    	this(caption, listener);
    	getState().baseUrl = generateUIClassUrl(uiClass);
        this.uiProvider = new BrowserWindowOpenerUIProvider(uiClass, getState().baseUrl);
    }

    /**
     * Creates a window opener button renderer that will open windows to the url provided in the Grid cell.
     *
     * @param caption
     *            the Caption of the button, Use setHtmlAllowed(true) to allow html e.g. VaadinIcons
     * @param listener
     *            the click listener to register
     */
    public BrowserOpenerRenderer(String caption, HtmlButtonRendererClickListener listener) {
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