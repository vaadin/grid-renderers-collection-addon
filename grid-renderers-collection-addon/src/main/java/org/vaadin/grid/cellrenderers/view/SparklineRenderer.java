package org.vaadin.grid.cellrenderers.view;

import com.vaadin.ui.renderers.AbstractRenderer;
import org.vaadin.grid.cellrenderers.client.view.SparklineRendererState;

@SuppressWarnings("unused")
public class SparklineRenderer extends AbstractRenderer<Object, Number[]> {
	
	@SuppressWarnings("WeakerAccess")
	public class SparklineConfiguration {

		public SparklineConfiguration() {
		}

		public SparklineConfiguration(int width, int height) {
			getState().width = width;
			getState().height = height;
		}

		public SparklineConfiguration(int width, int height, String caption) {
			getState().width = width;
			getState().height = height;
			getState().caption = caption;
		}
			
		public void setNormalRangeVisible(boolean normalRangeVisible) {
			getState().normalRangeVisible = normalRangeVisible;
		}

		public void setAverageVisible(boolean averageVisible) {
			getState().averageVisible = averageVisible;
		}

		public void setValueVisible(boolean valueVisible) {
			getState().valueVisible = valueVisible;
		}

		public void setMinMaxVisible(boolean minmaxVisible) {
			getState().minmaxVisible = minmaxVisible;
		}
		
		public void setMinMaxDotsVisible(boolean minmaxDotsVisible) {
			getState().minmaxDotsVisible = minmaxDotsVisible;
		}
		
		public void setValueDotVisible(boolean minValueDotVisible) {
			getState().valueDotVisible = minValueDotVisible;
		}

		public boolean isNormalRangeVisible() {
			return getState().normalRangeVisible;
		}

		public boolean isAverageVisible() {
			return getState().averageVisible;
		}

		public boolean isValueVisible() {
			return getState().valueVisible;
		}

		public boolean isMinMaxVisible() {
			return getState().minmaxVisible;
		}
		
		public boolean isMinMaxDotsVisible() {
			return getState().minmaxDotsVisible;
		}
		
		public boolean isValueDotVisible() {
			return getState().valueDotVisible;
		}
		
		public void setPathColor (String pathColor) {
			getState().pathColor = pathColor;
		}
		
		public void setAverageColor (String averageColor) {
			getState().averageColor = averageColor;
		}

		public void setNormalRangeColor (String normalRangeColor) {
			getState().normalRangeColor = normalRangeColor;
		}
		
		public void setMinMaxColor (String minmaxColor) {
			getState().minmaxColor = minmaxColor;
		}
		
		public void setPathWidth (int pathWidth) {
			getState().pathWidth = pathWidth;
		}
		
		public String getPathColor() {
			return getState().pathColor;			
		}
		
		public String getMinMaxColor() {
			return getState().minmaxColor;			
		}
		
		public String getAverageColor() {
			return getState().averageColor;			
		}
		
		public String getNormalRangeColor() {
			return getState().normalRangeColor;
		}
		
		public int getPathWidth() {
			return getState().pathWidth;
		}

	}
	
	private SparklineConfiguration config = null;
	
    public SparklineRenderer() {
        super(Number[].class);
        config = new SparklineConfiguration();        
    }

    public SparklineRenderer(int width, int height) {
        super(Number[].class);
        config = new SparklineConfiguration(width, height);
    }

    public SparklineRenderer(int width, int height, String caption) {
        super(Number[].class);
        config = new SparklineConfiguration(width, height, caption);
    }

    
    public SparklineRenderer(int width, int height, String caption, boolean normalRangeVisible, boolean averageVisible, boolean valueVisible, boolean minmaxVisible, boolean minmaxDotsVisible, boolean valueDotVisible) {
        super(Number[].class);
        config = new SparklineConfiguration(width, height);
        getState().normalRangeVisible = normalRangeVisible;
        getState().averageVisible = averageVisible;
        getState().valueVisible = valueVisible;
        getState().minmaxVisible = minmaxVisible;
        getState().minmaxDotsVisible = minmaxDotsVisible;
        getState().valueDotVisible = valueDotVisible;
    }

    public SparklineConfiguration getConfiguration() {
    	return config;
    }
    
    @Override
    protected SparklineRendererState getState() {
    	return (SparklineRendererState) super.getState();
    }
   
}