package org.vaadin.sparklines;

import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import org.vaadin.sparklines.client.ui.SparklinesConnector;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Optional;

/**
 * <p>
 * A sparkline is a small information graphic with high information density.
 * Edward Tufte coined the term "sparklines" for what he calls "small, intense,
 * simple datawords" - see his book "Beautiful Evidence" and/or the online
 * version with recent develpoments and commentary at
 * http://www.edwardtufte.com/bboard/q-and-a-fetch-msg?msg_id=0001OR
 * </p>
 * <p>
 * This implementation supports the following features:
 * <ul>
 * <li>graph for a set of integers, settable min/max range (to allow
 * comparisons)</li>
 * <li>prominent label/caption indicating what the sparkline represents</li>
 * <li>textual label for the current (last) value (optional)</li>
 * <li>dot in the graph for the current (last) value (optional)</li>
 * <li>textual labels for min/max (optional)</li>
 * <li>dots in the graph for min/max values (optional)</li>
 * <li>customizable colors for value/min/max, connects dot with label</li>
 * <li>line in the graph indicating average value (optional)</li>
 * <li>colored range in the graph indicating 'normal' value range (optional)</li>
 * </ul>
 * <p>
 * Sparklines are very well suited for quick visual comparisons, provided that
 * they are properly configured: make sure to set the display range and use the
 * same amount of data (or adjust width) in order to make multiple sparklines
 * comparable.
 * </p>
 * <p>
 * Also note that aspect ratio is often important; as a rule of thumb,
 * variations in slopes are easiest to detect if the slopes average at 45
 * degrees. See Tufte for more on this subject.
 * </p>
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class Sparklines extends AbstractField<List<? extends Number>> implements Component {

    private int graphHeight;
    private int graphWidth;
    private int displayRangeMax;
    private int displayRangeMin;

    private String pathColor = "#999";
    private int pathWidth = 1;

    private boolean valueLabelVisible = true;
    private boolean valueDotVisible = true;
    private String valueColor = "#69f";

    private boolean normalRangeVisible;
    private String normalRangeColor = "#ddd";
    private int normalRangeMax;
    private int normalRangeMin;

    private boolean averageVisible;
    private String averageColor = "#aaa";

    private boolean minmaxLabelsVisible = true;
    private boolean minmaxDotsVisible = true;
    private String maxColor = pathColor;
    private String minColor = pathColor;

    private List<Number> data = Collections.emptyList();

    public Sparklines(String caption, int graphWidth, int graphHeight,
                      int displayRangeMin, int displayRangeMax) {
        setCaption(caption);
        this.graphHeight = graphHeight;
        this.graphWidth = graphWidth;
        this.displayRangeMax = displayRangeMax;
        this.displayRangeMin = displayRangeMin;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetValue(List<? extends Number> numbers) {
        this.data = (List<Number>) numbers;
    }

    @Override
    public void clear() {
        doSetValue(getEmptyValue());
    }

    @Override
    public List<Number> getValue() {
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends Number> getEmptyValue() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Optional<List<? extends Number>> getOptionalValue() {
        return Optional.of(data);
    }

    public void paintContent(PaintTarget target) throws PaintException {

        target.addAttribute(SparklinesConnector.graphHeight, graphHeight);
        target.addAttribute(SparklinesConnector.graphWidth, graphWidth);
        target.addAttribute(SparklinesConnector.displayRangeMax,
                displayRangeMax);
        target.addAttribute(SparklinesConnector.displayRangeMin,
                displayRangeMin);

        target.addAttribute(SparklinesConnector.pathColor, pathColor);
        target.addAttribute(SparklinesConnector.pathWidth, pathWidth);

        target.addAttribute(SparklinesConnector.valueVisible, valueLabelVisible);
        target.addAttribute(SparklinesConnector.valueDotVisible,
                valueDotVisible);
        target.addAttribute(SparklinesConnector.valueColor, valueColor);

        target.addAttribute(SparklinesConnector.normalRangeVisible,
                normalRangeVisible);
        target.addAttribute(SparklinesConnector.normalRangeColor,
                normalRangeColor);
        target.addAttribute(SparklinesConnector.normalRangeMax, normalRangeMax);
        target.addAttribute(SparklinesConnector.normalRangeMin, normalRangeMin);

        target.addAttribute(SparklinesConnector.averageVisible, averageVisible);
        target.addAttribute(SparklinesConnector.averageColor, averageColor);

        target.addAttribute(SparklinesConnector.minmaxLabelsVisible,
                minmaxLabelsVisible);
        target.addAttribute(SparklinesConnector.minmaxDotsVisible,
                minmaxDotsVisible);
        target.addAttribute(SparklinesConnector.maxColor, maxColor);
        target.addAttribute(SparklinesConnector.minColor, minColor);

        List<Number> c = getValue();
        List<Number> data = translateData(c);
        String[] rawData = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            rawData[i] = String.valueOf(data.get(i));
        }
        target.addAttribute(SparklinesConnector.DATA, rawData);
    }

    public String toString() {
        final List<Number> value = getValue();
        return Arrays.deepToString(value.toArray());
    }

    public void setValue(Number... newValue) {
        super.setValue(Arrays.asList(newValue));
    }

    protected List<Number> translateData(List<Number> data) {
        return data;
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public void setGraphHeight(int graphHeight) {
        this.graphHeight = graphHeight;
        markAsDirty();
    }

    public int getGraphWidth() {
        return graphWidth;
    }

    public void setGraphWidth(int graphWidth) {
        this.graphWidth = graphWidth;
        markAsDirty();
    }

    public int getDisplayRangeMax() {
        return displayRangeMax;
    }

    public void setDisplayRangeMax(int displayRangeMax) {
        this.displayRangeMax = displayRangeMax;
        markAsDirty();
    }

    public int getDisplayRangeMin() {
        return displayRangeMin;
    }

    public void setDisplayRangeMin(int displayRangeMin) {
        this.displayRangeMin = displayRangeMin;
        markAsDirty();
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
        markAsDirty();
    }

    public String getPathColor() {
        return pathColor;
    }

    public void setPathColor(String pathColor) {
        this.pathColor = pathColor;
        markAsDirty();
    }

    public boolean isValueLabelVisible() {
        return valueLabelVisible;
    }

    public void setValueLabelVisible(boolean valueVisible) {
        this.valueLabelVisible = valueVisible;
        markAsDirty();
    }

    public boolean isValueDotVisible() {
        return valueDotVisible;
    }

    public void setValueDotVisible(boolean valueDotVisible) {
        this.valueDotVisible = valueDotVisible;
        markAsDirty();
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
        markAsDirty();
    }

    public boolean isNormalRangeVisible() {
        return normalRangeVisible;
    }

    public void setNormalRangeVisible(boolean normalRangeVisible) {
        this.normalRangeVisible = normalRangeVisible;
        markAsDirty();
    }

    public String getNormalRangeColor() {
        return normalRangeColor;
    }

    public void setNormalRangeColor(String normalRangeColor) {
        this.normalRangeColor = normalRangeColor;
        markAsDirty();
    }

    public int getNormalRangeMax() {
        return normalRangeMax;
    }

    public void setNormalRangeMax(int normalRangeMax) {
        this.normalRangeMax = normalRangeMax;
        markAsDirty();
    }

    public int getNormalRangeMin() {
        return normalRangeMin;
    }

    public void setNormalRangeMin(int normalRangeMin) {
        this.normalRangeMin = normalRangeMin;
        markAsDirty();
    }

    public boolean isAverageVisible() {
        return averageVisible;
    }

    public void setAverageVisible(boolean averageVisible) {
        this.averageVisible = averageVisible;
        markAsDirty();
    }

    public String getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(String averageColor) {
        this.averageColor = averageColor;
        markAsDirty();
    }

    public boolean isMinmaxLabelsVisible() {
        return minmaxLabelsVisible;
    }

    public void setMinmaxLabelsVisible(boolean minmaxLabelsVisible) {
        this.minmaxLabelsVisible = minmaxLabelsVisible;
        markAsDirty();
    }

    public boolean isMinmaxDotsVisible() {
        return minmaxDotsVisible;
    }

    public void setMinmaxDotsVisible(boolean minmaxDotsVisible) {
        this.minmaxDotsVisible = minmaxDotsVisible;
        markAsDirty();
    }

    public String getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(String maxColor) {
        this.maxColor = maxColor;
        markAsDirty();
    }

    public String getMinColor() {
        return minColor;
    }

    public void setMinColor(String minColor) {
        this.minColor = minColor;
        markAsDirty();
    }

}
