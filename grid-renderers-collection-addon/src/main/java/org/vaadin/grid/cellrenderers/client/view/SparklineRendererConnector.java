package org.vaadin.grid.cellrenderers.client.view;

import com.vaadin.client.connectors.grid.GridConnector;
import org.vaadin.sparklines.client.ui.SparklinesGWT;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.vaadin.client.widgets.Grid; 
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

@Connect(org.vaadin.grid.cellrenderers.view.SparklineRenderer.class)
public class SparklineRendererConnector extends
        AbstractRendererConnector<double[]> {

     public class SparklineClientRenderer extends WidgetRenderer<double[], FlowPanel> {
    	 NumberFormat fmt = NumberFormat.getDecimalFormat();
    	 {
    		 fmt.overrideFractionDigits(2, 2);
    	 }

    	 private void updateSettings(SparklinesGWT s) {
    		 SparklineRendererState state = getState();
        	 s.setNormalRangeVisible(state.normalRangeVisible);
        	 s.setAverageVisible(state.averageVisible);
        	 s.setValueVisible(state.valueVisible);
        	 s.setMinMaxVisible(state.minmaxVisible, state.minmaxDotsVisible);
        	 s.setValueDotVisible(state.valueDotVisible);
        	 s.setPathWidth(state.pathWidth);
        	 s.setPathColor(state.pathColor);
        	 s.setNormalRangeColor(state.normalRangeColor);
        	 s.setAverageColor(state.averageColor);
        	 s.setValueColor(state.valueColor);
        	 s.setMinColor(state.minmaxColor);
        	 s.setMaxColor(state.minmaxColor);
        	     		 
    	 }
    	 
    	 @Override
    	 public FlowPanel createWidget() {
    		 SparklineRendererState state = getState();
    		 SparklinesGWT s = new SparklinesGWT(null, state.width, state.height);
        	 s.setCaption(state.caption);
    		 updateSettings(s);
    		 
        	 s.addStyleName("v-widget");

        	 HTML html = new HTML();

        	 FlowPanel p = new FlowPanel();
        	 p.add(s);
        	 p.add(html);
        	 p.addStyleName("v-widget sparkline");

        	 return p;
    	 }

    	@Override
        public void render(RendererCellReference cell, double[] data,
        	            FlowPanel widget) {
    		SparklinesGWT s = (SparklinesGWT) widget.getWidget(0);
    		updateSettings(s);
 
    		HTML html = (HTML) widget.getWidget(1);
    		double change = data[data.length - 1] - data[0];
    		double min = data[0];
    		double max = data[1];
    		for (int i = 1; i < data.length; i++) {
    			if (data[i] < min) {
    				min = data[i];
    			}
    			if (data[i] > max) {
    				max = data[i];
    			}
    		}
    		s.setData(data);
    		s.setDisplayRange((int) (min - 5), (int) (max + 5));
    		if (change >= 0) {
    			html.setStyleName("going-up");
    		} else {
    			html.setStyleName("going-down");
    		}
    	}
    }
     
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        Grid grid = getGrid();
		refresh(grid);
    }
 
    private native static void refresh(Grid<?> grid)
	 /*-{    
	   grid.@com.vaadin.client.widgets.Grid::refreshHeader()();
	   grid.@com.vaadin.client.widgets.Grid::refreshBody()();
	   grid.@com.vaadin.client.widgets.Grid::refreshFooter()();
	 }-*/;
    
    @Override
    protected SparklineClientRenderer createRenderer() {
        return new SparklineClientRenderer();
    }

    @Override
    public SparklineClientRenderer getRenderer() {
        return (SparklineClientRenderer) super.getRenderer();
    }

    @Override
    public double[] decode(JsonValue json) {
        // Using json in order to be compatible with Number
        JsonArray arr = (JsonArray) json;
        double[] value = new double[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            value[i] = arr.get(i).asNumber();
        }

        return value;
    }

    @Override
    public SparklineRendererState getState() {
    	return (SparklineRendererState) super.getState();
    }
 
    private Grid<JsonObject> getGrid() {
        return ((GridConnector) getParent()).getWidget();
    }
    
}
