package org.vaadin.grid.cellrenderers.client.action;

import java.util.HashMap;
import java.util.Map;

public class BrowserOpenerRendererState extends HtmlButtonRendererState {
    public static final String locationResource = "url";

	public String target = "_blank";

    public String features;

    public String caption;

    public Map<String, String> parameters = new HashMap<String, String>();

    public String baseUrl = null;

}