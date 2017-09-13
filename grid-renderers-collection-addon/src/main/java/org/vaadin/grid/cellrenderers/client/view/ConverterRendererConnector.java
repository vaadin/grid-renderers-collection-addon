package org.vaadin.grid.cellrenderers.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(org.vaadin.grid.cellrenderers.view.ConverterRenderer.class)
public class ConverterRendererConnector extends
        AbstractRendererConnector<String> {


    public class ConverterClientRenderer implements Renderer<String> {

        @Override
        public void render(RendererCellReference cell, String htmlString) {
        	String content = htmlString;
        	if (content == null) content = getState().nullRepresentation; 
            cell.getElement()
                    .setInnerSafeHtml(SafeHtmlUtils.fromSafeConstant(content));
        }        

    }
    
    @Override
    protected ConverterClientRenderer createRenderer() {
        return new ConverterClientRenderer();
    }

    @Override
    public ConverterClientRenderer getRenderer() {
        return (ConverterClientRenderer) super.getRenderer();
    }

    @Override
    public ConverterRendererState getState() {
    	return (ConverterRendererState) super.getState();
    }
 
 }
