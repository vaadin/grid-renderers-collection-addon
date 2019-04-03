package org.vaadin.grid.cellrenderers.action;

import org.vaadin.grid.cellrenderers.action.AbstractHtmlButtonRenderer.HtmlButtonRendererClickListener;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer.DeleteRendererClickEvent;

/**
 * HtmlButtonRenderer creates a button in Grid column. This is otherwise similar to
 * regular button renderer of the framework, but also allows html content in button
 * caption  
 * 
 * @author Tatu Lund
 */
public class HtmlButtonRenderer extends AbstractHtmlButtonRenderer<String> {

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