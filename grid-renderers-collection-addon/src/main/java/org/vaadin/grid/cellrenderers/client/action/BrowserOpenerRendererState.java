package org.vaadin.grid.cellrenderers.client.action;

import java.util.HashMap;
import java.util.Map;

public class BrowserOpenerRendererState extends HtmlButtonRendererState {

    public String target = "_blank";

    public String features;

    public String caption;
    
    public Map<String, String> parameters = new HashMap<>();
    
    public String baseUrl = null;

    public boolean isResource = false;
}
