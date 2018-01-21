package org.vaadin.grid.cellrenderers.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(org.vaadin.grid.cellrenderers.view.RowIndexRenderer.class)
public class RowIndexRendererConnector extends
        AbstractRendererConnector<String> {

	// Helper method to create ordinal String
	private static String ordinal(int i) {
	    int mod100 = i % 100;
	    int mod10 = i % 10;
	    if (mod10 == 1 && mod100 != 11) {
	        return i + "st";
	    } else if(mod10 == 2 && mod100 != 12) {
	        return i + "nd";
	    } else if(mod10 == 3 && mod100 != 13) {
	        return i + "rd";
	    } else {
	        return i + "th";
	    }
	}
	
    public class RowIndexClientRenderer implements Renderer<String> {

        @Override
        public void render(RendererCellReference cell, String htmlString) {
        	int rowIndex = cell.getRowIndex()+getState().offset;
        	String content = null;
        	if (getState().ordinalMode) {
        		content = ordinal(rowIndex);
        	} else {
        		content = ""+rowIndex;        		
        	}
        	cell.getElement()
                    .setInnerSafeHtml(SafeHtmlUtils.fromSafeConstant(content));
        }        

    }
    
    @Override
    protected RowIndexClientRenderer createRenderer() {
        return new RowIndexClientRenderer();
    }

    @Override
    public RowIndexClientRenderer getRenderer() {
        return (RowIndexClientRenderer) super.getRenderer();
    }

    @Override
    public RowIndexRendererState getState() {
    	return (RowIndexRendererState) super.getState();
    }
 }