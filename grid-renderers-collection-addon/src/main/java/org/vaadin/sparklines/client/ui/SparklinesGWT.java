package org.vaadin.sparklines.client.ui;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

// TODO override setWidth etc and add javadoc about setSparklineWidth
// TODO ? rename setSparklineWidth/Height -> setGraph/setPath/...
// TODO setPathWidth
// TODO setAverageWidth
// TODO setDotSize
// TODO ? set
public class SparklinesGWT extends Composite {

    public static String CLASSNAME = "v-sparkline";

    private static int PAD = 4;
    private static int OFFSET = PAD / 2;

    protected DrawingArea canvas;
    protected int graphWidth = 0; // auto
    protected int graphHeight = 0; // auto

    protected Rectangle normalRange;

    protected Line average;
    protected String averageColor = "#ddd";

    protected Circle minDot;
    protected Circle maxDot;
    protected Circle endDot;

    protected Path path;
    protected String pathColor = "#ccc";
    protected int pathWidth = 1;

    protected double max = Double.MIN_VALUE;
    protected double maxidx = 0;
    protected double minidx = 0;
    protected double min = Double.MAX_VALUE;

    protected int avg = 0;

    protected double vscale = 1;
    protected double hscale = 1;

    protected int displayLow = Integer.MIN_VALUE; // auto
    protected int displayHigh = Integer.MAX_VALUE; // auto

    protected int normalLow = Integer.MIN_VALUE; // off
    protected int normalHigh = Integer.MAX_VALUE; // off
    protected String normalRangeColor = "#efefef";

    protected double[] data = {};

    protected Label maxLabel;
    protected String maxColor = pathColor;

    protected Label minLabel;
    protected String minColor = pathColor;

    protected Label value;
    protected Label caption;
    protected String valueColor = "#00f";

    public SparklinesGWT(String caption, int graphWidth, int graphHeight,
            int displayRangeMin, int displayRangeMax) {
        this.graphHeight = graphHeight;
        this.graphWidth = graphWidth;
        this.displayHigh = displayRangeMax;
        this.displayLow = displayRangeMin;

        final HorizontalPanel main = new HorizontalPanel();
        initWidget(main);
        main.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        setStylePrimaryName(CLASSNAME);

        canvas = new DrawingArea(graphWidth, graphHeight);
        canvas.setStylePrimaryName(CLASSNAME + "graph");
        main.add(canvas);

        path = new Path(0, 0);
        path.setStrokeColor(pathColor);
        path.setStrokeWidth(pathWidth);
        canvas.add(path);

        this.caption = new Label(caption);
        this.caption.setStylePrimaryName(CLASSNAME + "caption");
        this.caption.getElement().getStyle().setColor(pathColor);
        main.add(this.caption);

        value = new Label();
        value.setStylePrimaryName(CLASSNAME + "value");
        value.getElement().getStyle().setColor(valueColor);
        main.add(value);
        // main.setCellWidth(value, "100%");

        FlowPanel minmax = new FlowPanel();
        minmax.setStylePrimaryName(CLASSNAME + "minmax");
        main.add(minmax);

        maxLabel = new Label();
        maxLabel.setStylePrimaryName(CLASSNAME + "max");
        maxLabel.getElement().getStyle().setColor(maxColor);
        minmax.add(maxLabel);

        minLabel = new Label();
        minLabel.setStylePrimaryName(CLASSNAME + "min");
        minLabel.getElement().getStyle().setColor(minColor);
        minmax.add(minLabel);

        normalRange = new Rectangle(0, 0, graphWidth, graphHeight);
        normalRange.setFillColor(normalRangeColor);
        normalRange.setStrokeOpacity(0);
        canvas.add(normalRange);

        average = new Line(0, 0, 0, 0);
        average.setStrokeWidth(pathWidth);
        average.setStrokeColor(averageColor);
        canvas.add(average);

        minDot = new Circle(0, 0, 1);
        minDot.setStrokeWidth(pathWidth);
        minDot.setStrokeColor(minColor);
        canvas.add(minDot);

        maxDot = new Circle(0, 0, 1);
        maxDot.setStrokeColor(maxColor);
        canvas.add(maxDot);

        endDot = new Circle(0, 0, 1);
        endDot.setStrokeColor(valueColor);
        canvas.add(endDot);
    }

    public SparklinesGWT(String caption, int graphWidth, int graphHeight) {
        this(caption, graphWidth, graphHeight, Integer.MAX_VALUE,
                Integer.MIN_VALUE);
    }

    public SparklinesGWT(String caption) {
        this(caption, 0, 0);
    }

    public SparklinesGWT() {
        this("");
    }

    public String getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(String averageColor) {
        this.averageColor = averageColor;
        average.setStrokeColor(averageColor);
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
        path.setStrokeWidth(pathWidth);
        average.setStrokeWidth(pathWidth);
        minDot.setStrokeWidth(pathWidth);
        maxDot.setStrokeWidth(pathWidth);
        endDot.setStrokeWidth(pathWidth);
    }

    public String getPathColor() {
        return pathColor;
    }

    public void setPathColor(String pathColor) {
        this.pathColor = pathColor;
        path.setStrokeColor(pathColor);
    }

    public String getNormalRangeColor() {
        return normalRangeColor;
    }

    public void setNormalRangeColor(String normalRangeColor) {
        this.normalRangeColor = normalRangeColor;
        normalRange.setFillColor(normalRangeColor);
    }

    public String getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(String maxColor) {
        this.maxColor = maxColor;
        maxDot.setStrokeColor(maxColor);
        maxLabel.getElement().getStyle().setColor(maxColor);
    }

    public String getMinColor() {
        return minColor;
    }

    public void setMinColor(String minColor) {
        this.minColor = minColor;
        minDot.setStrokeColor(minColor);
        minLabel.getElement().getStyle().setColor(minColor);
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
        path.setStrokeColor(valueColor);
        endDot.setStrokeColor(valueColor);
        value.getElement().getStyle().setColor(valueColor);
    }

    public void setCaption(String caption) {
        this.caption.setVisible(caption != null);
        this.caption.setText(caption);
    }

    public String getCaption() {
        return this.caption.getText();
    }

    public void setSparklineWidth(int width) {
        graphWidth = width;
        redraw();
    }

    public void setDisplayRange(int lower, int upper) {
        displayHigh = upper;
        displayLow = lower;
        redraw();
    }

    public void setSparklineHeight(int height) {
        graphHeight = height;
        redraw();
    }

    public void setNormalRange(int lower, int upper) {
        normalHigh = upper;
        normalLow = lower;
        redraw();
    }

    public void setData(double... data) {
        this.data = data;
        redraw();
    }

    public void setMinMaxVisible(boolean labelsVisible, boolean dotsVisible) {
        minLabel.setVisible(labelsVisible);
        minDot.setVisible(dotsVisible);
        maxLabel.setVisible(labelsVisible);
        maxDot.setVisible(dotsVisible);
    }

    public void setValueVisible(boolean valueVisible) {
        value.setVisible(valueVisible);
    }

    public void setNormalRangeVisible(boolean normalRangeVisible) {
        normalRange.setVisible(normalRangeVisible);
    }

    public void setAverageVisible(boolean averageVisible) {
        average.setVisible(averageVisible);
    }

    public void setValueDotVisible(boolean valueDotVisible) {
        endDot.setVisible(valueDotVisible);
    }

    private void redraw() {
        max = Double.MIN_VALUE;
        maxidx = 0;
        minidx = 0;
        min = Integer.MAX_VALUE;

        avg = 0;

        vscale = 1;
        hscale = 1;

        if (data == null || data.length == 0) {
            return;
        }
        for (int i = data.length - 1; i >= 0; i--) {
            double v = data[i];
            if (max <= v) {
                maxidx = i;
                max = v;
            }
            if (min > v) {
                minidx = i;
                min = v;
            }
            avg += v;
        }

        minLabel.setText(String.valueOf(min));
        if (data.length - 1 == minidx) {
            minLabel.getElement().getStyle().setColor(valueColor);
        } else {
            minLabel.getElement().getStyle().setColor(minColor);
        }
        maxLabel.setText(String.valueOf(max));
        if (data.length - 1 == maxidx) {
            maxLabel.getElement().getStyle().setColor(valueColor);
        } else {
            maxLabel.getElement().getStyle().setColor(maxColor);
        }
        value.setText(String.valueOf(data[data.length - 1]));

        double effectiveMin = (displayLow < Double.MAX_VALUE ? displayLow : min);
        double effectiveMax = (displayHigh > Double.MIN_VALUE ? displayHigh
                : max);

        if (graphHeight < 1) {
            // canvas.setHeight(effectiveMax - effectiveMin + PAD);
            canvas.setHeight(30);
        } else {
            canvas.setHeight(graphHeight);
        }

        if (graphWidth < 1) {
            // canvas.setWidth(5 * canvas.getHeight() + PAD);
            canvas.setWidth(100);
        } else {
            canvas.setWidth(graphWidth);
        }

        vscale = (double) (canvas.getHeight() - PAD)
                / (effectiveMax - effectiveMin);
        hscale = (double) (canvas.getWidth() - PAD) / (data.length - 1);

        // average
        avg = Math.round(avg / data.length);
        average.setY1(translateY(avg));
        average.setY2(translateY(avg));
        average.setX2(canvas.getWidth());

        // normal range
        normalRange.setWidth(canvas.getWidth());
        normalRange.setY(translateY(normalHigh));
        normalRange.setHeight((int) ((normalHigh - normalLow) * vscale));

        // path
        if (path != null) {
            canvas.remove(path);
        }
        path = new Path(translateX(0), translateY(data[0]));
        path.setStrokeWidth(pathWidth);
        path.setStrokeColor(pathColor);
        path.setFillOpacity(0);
        for (int i = 1; i < data.length; i++) {
            path.lineTo(translateX(i), translateY(data[i]));
        }
        canvas.add(path);

        // dots
        canvas.remove(endDot);
        endDot.setX(translateX(data.length - 1));
        endDot.setY(translateY(data[data.length - 1]));
        canvas.add(endDot);

        canvas.remove(minDot);
        if (minidx != data.length - 1) {
            minDot.setX(translateX(minidx));
            minDot.setY(translateY(min));
            canvas.add(minDot);
        }

        canvas.remove(maxDot);
        if (maxidx != data.length - 1) {
            maxDot.setX(translateX(maxidx));
            maxDot.setY(translateY(max));
            canvas.add(maxDot);
        }

    }

    private int translateY(double y) {
        double effectiveMin = (displayLow < Integer.MAX_VALUE ? displayLow
                : min);
        int newY = (int) ((y - effectiveMin) * vscale);
        int res = (canvas.getHeight() - newY) - OFFSET;
        return res;
    }

    private int translateX(double x) {
        return (int) (x * hscale) + OFFSET;
    }

}
