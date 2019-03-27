package org.vaadin.grid.cellrenderers.action;

import org.vaadin.grid.cellrenderers.action.AbstractHtmlButtonRenderer.HtmlButtonRendererClickListener;

public class HtmlButtonRenderer<T> extends AbstractHtmlButtonRenderer<T,String> {

    /**
     * Creates a new html button renderer and adds the given click listener to it
     *
     * @param listener
     *            the click listener to register
     */
    public HtmlButtonRenderer(HtmlButtonRendererClickListener listener) {
    	super(listener);
    }
	
}
