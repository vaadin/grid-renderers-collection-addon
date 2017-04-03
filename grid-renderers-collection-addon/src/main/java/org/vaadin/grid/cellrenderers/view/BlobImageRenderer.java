package org.vaadin.grid.cellrenderers.view;

import org.apache.commons.codec.binary.Base64;
import org.vaadin.grid.cellrenderers.client.view.BlobImageRendererState;
import org.vaadin.grid.cellrenderers.client.view.SparklineRendererState;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer.SparklineConfiguration;

import com.vaadin.ui.Grid.AbstractRenderer;

import elemental.json.JsonValue;

/**
 * @author Tatu Lund - Vaadin
 */
public class BlobImageRenderer extends AbstractRenderer<byte[]> {

    private static final String TRANSPARENT_GIF_1PX = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACwAAAAAAQABAAACAkQBADs=";
    private String mimeType = "image/png";
    
    private String convertToHtml(byte[] value) {
    	String htmlValue = null;
    	if (value == null) htmlValue = TRANSPARENT_GIF_1PX;
    	else htmlValue = "data:"+mimeType+";base64," + Base64.encodeBase64String(value);
    	return htmlValue;
    }
    
    /**
     * Constructor for a new BlobImageRenderer with parameter settings
     * 
     * @param width Set width of the image. Use -1 for default width.
     * @param height Set height of the image. If this is more than default 
     *               height of the grid row, you need to also style your grid'
     *               row height to fit the image. Use -1 for the default.
     */
    public BlobImageRenderer(int width, int height) {
        super(byte[].class);
        getState().width = width;
        getState().height = height;        
    }

    /**
     * Constructor for a new BlobImageRenderer with parameter settings
     * 
     * @param width Set width of the image. Use -1 for default width.
     * @param height Set height of the image. If this is more than default 
     *               height of the grid row, you need to also style your grid'
     *               row height to fit the image. Use -1 for the default.
     * @param mimeType Set alternative mimeType if needed for some reason.
     */
    public BlobImageRenderer(int width, int height, String mimeType) {
        super(byte[].class);
        getState().width = width;
        getState().height = height;
        this.mimeType = mimeType;
    }
    
    /**
     *  Constructor for a new BlobImageRenderer with default parameters
     *  it is not recommended to use large images in Grid. For the best
     *  performance scale your images close to target resolution in order
     *  to avoid excess network load. Base64 encoding adds roughly 30%
     *  overhead.
     *    
     */
	public BlobImageRenderer() {
        super(byte[].class);
    }

	
    @Override
    public String getNullRepresentation() {
        return TRANSPARENT_GIF_1PX;
    }

    @Override
    public JsonValue encode(byte[] value) {
        if (value == null) {
            return encode(getNullRepresentation(), String.class);
        } else {
            return encode(convertToHtml(value), String.class);
        }
    }

    @Override
    protected BlobImageRendererState getState() {
    	return (BlobImageRendererState) super.getState();
    }
  
}
