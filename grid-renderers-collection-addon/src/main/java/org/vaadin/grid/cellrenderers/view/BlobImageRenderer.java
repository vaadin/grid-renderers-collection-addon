package org.vaadin.grid.cellrenderers.view;

import org.apache.commons.codec.binary.Base64;

import com.vaadin.ui.Grid.AbstractRenderer;

import elemental.json.JsonValue;

public class BlobImageRenderer extends AbstractRenderer<byte[]> {

    public static final String TRANSPARENT_GIF_1PX = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACwAAAAAAQABAAACAkQBADs=";

    public String convertToHtml(byte[] value) {
    	String htmlValue = null;
    	if (value == null) htmlValue = TRANSPARENT_GIF_1PX;
    	else htmlValue = "<img src=\"data:image/png;base64," + Base64.encodeBase64String(value) + "\"/>";
    	return htmlValue;
    }
    
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

}
