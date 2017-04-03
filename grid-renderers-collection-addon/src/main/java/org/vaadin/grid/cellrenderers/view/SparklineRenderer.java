package org.vaadin.grid.cellrenderers.view;

import com.vaadin.ui.Grid.AbstractRenderer;
import org.vaadin.grid.cellrenderers.client.view.SparklineRendererState;

/**
 * @author Tatu Lund - Vaadin
 * 
 */
public class SparklineRenderer extends AbstractRenderer<Number[]> {
	
	/**
	 * Configuration class for a SparklineRenderer
	 * The parameters are roughly the same as with
	 * Sparkline Add-On
	 * 
	 */
	public class SparklineConfiguration {

		/**
		 * The default constructor with default values
		 */
		public SparklineConfiguration() {
		}

		/**
		 * Create configuration with specific width and height
		 * @param width The width
		 * @param height The height. Note if this is higher than default Grid row
		 *               height, you need to style your grid row height accordingly.
		 */
		public SparklineConfiguration(int width, int height) {
			getState().width = width;
			getState().height = height;
		}

		/**
		 * Create configuration with specific width and height
		 * @param width The width
		 * @param height The height. Note if this is higher than default Grid row
		 *               height, you need to style your grid row height accordingly.
		 * @param caption The new caption as String
		 */
		public SparklineConfiguration(int width, int height, String caption) {
			getState().width = width;
			getState().height = height;
			getState().caption = caption;
		}
			
		/**
		 * Enable/disalbe showing normal range
		 * 
		 * @param normalRangeVisible Setting
		 */
		public void setNormalRangeVisible(boolean normalRangeVisible) {
			getState().normalRangeVisible = normalRangeVisible;
		}

		/**
		 * Enable/disable showing average line
		 * 
		 * @param averageVisible Setting
		 */
		public void setAverageVisible(boolean averageVisible) {
			getState().averageVisible = averageVisible;
		}

		/**
		 * Enable/disable display of the last value as number on the right side
		 * 
		 * @param valueVisible Setting
		 */
		public void setValueVisible(boolean valueVisible) {
			getState().valueVisible = valueVisible;
		}

		/**
		 * Enable/disable viewing of the minimum and maximum values on the right
		 * 
		 * @param minmaxVisible Setting
		 */
		public void setMinMaxVisible(boolean minmaxVisible) {
			getState().minmaxVisible = minmaxVisible;
		}
		
		/**
		 * Enable/disable highlight of the minimum and maximum values
		 * 
		 * @param minmaxDotsVisible Setting
		 */
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
	
	SparklineConfiguration config = null;
	
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