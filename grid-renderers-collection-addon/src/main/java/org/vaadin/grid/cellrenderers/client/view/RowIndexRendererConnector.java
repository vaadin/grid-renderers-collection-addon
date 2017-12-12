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


    public class RowIndexClientRenderer implements Renderer<String> {

        @Override
        public void render(RendererCellReference cell, String htmlString) {
        	int rowIndex = cell.getRowIndex();
        	String content = ""+rowIndex;
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

 }
