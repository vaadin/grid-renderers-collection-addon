package org.vaadin.grid.cellrenderers.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;

import org.apache.commons.codec.binary.Base64;
import org.vaadin.grid.cellrenderers.client.view.SparklineRendererConnector.SparklineClientRenderer;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(org.vaadin.grid.cellrenderers.view.BlobImageRenderer.class)
public class BlobImageRendererConnector extends
        AbstractRendererConnector<String> {


    public class BlobImageClientRenderer extends ClickableRenderer<String, Image> {

        @Override
        public Image createWidget() {
        	BlobImageRendererState state = getState();
            Image image = GWT.create(Image.class);
            image.addClickHandler(this);
            if (state.width > -1) image.setWidth(state.width+"px");
            if (state.height > -1) image.setWidth(state.height+"px");
            return image;
        }

    	@Override
    	public void render(RendererCellReference cell, String imageData, Image image) {
            image.setUrl(imageData);
    	}
    }
    
    @Override
    protected BlobImageClientRenderer createRenderer() {
        return new BlobImageClientRenderer();
    }

    @Override
    public BlobImageClientRenderer getRenderer() {
        return (BlobImageClientRenderer) super.getRenderer();
    }

    @Override
    public BlobImageRendererState getState() {
    	return (BlobImageRendererState) super.getState();
    }
 
 }
