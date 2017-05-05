package org.vaadin.grid.cellrenderers.client.view;

import java.util.Date;

import com.vaadin.shared.communication.SharedState;

/**
 * @author Tatu Lund - Vaadin
 */
public class SparklineRendererState extends SharedState{
	public int width = 75;
	public int height = 16;
	public int pathWidth = 1;
	
	public String caption;
	
	public String pathColor = "#999";
	public String valueColor = "#69f";
	public String normalRangeColor = "#ddd";
	public String averageColor = "#aaa";
	public String minmaxColor = valueColor;
	
	public boolean normalRangeVisible = false;
	public boolean averageVisible = false;
	public boolean valueVisible = false;
	public boolean minmaxVisible = false;
	public boolean minmaxDotsVisible = false;
	public boolean valueDotVisible = false;
}
