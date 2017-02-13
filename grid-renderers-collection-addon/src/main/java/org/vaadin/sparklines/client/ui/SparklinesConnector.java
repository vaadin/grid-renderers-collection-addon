package org.vaadin.sparklines.client.ui;

import org.vaadin.sparklines.Sparklines;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Sparklines.class)
public class SparklinesConnector extends LegacyConnector {

    public static final String graphHeight = "gh";
    public static final String graphWidth = "gw";
    public static final String displayRangeMax = "dmx";
    public static final String displayRangeMin = "dmi";

    public static final String pathColor = "pc";
    public static final String pathWidth = "pw";

    public static final String valueVisible = "vlv";
    public static final String valueDotVisible = "vdv";
    public static final String valueColor = "vc";

    public static final String normalRangeVisible = "nrv";
    public static final String normalRangeColor = "nrc";
    public static final String normalRangeMax = "nmx";
    public static final String normalRangeMin = "nmi";

    public static final String averageVisible = "av";
    public static final String averageColor = "ac";

    public static final String minmaxLabelsVisible = "mlv";
    public static final String minmaxDotsVisible = "mdv";
    public static final String maxColor = "mxc";
    public static final String minColor = "mic";

    public static final String DATA = "d";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    @Override
    protected void init() {
        super.init();

        getLayoutManager().layoutLater();
    }

    /**
     * Called whenever an update is received from the server
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        if (!isRealUpdate(uidl)) {
            return;
        }

        SparklinesGWT sparkline = getWidget();

        this.client = client;
        paintableId = uidl.getId();

        // sparkline.setCaption(uidl.getStringAttribute("caption"));

        sparkline.setSparklineHeight(uidl.getIntAttribute(graphHeight));
        sparkline.setSparklineWidth(uidl.getIntAttribute(graphWidth));
        sparkline.setDisplayRange(uidl.getIntAttribute(displayRangeMin),
                uidl.getIntAttribute(displayRangeMax));

        sparkline.setPathColor(uidl.getStringAttribute(pathColor));
        sparkline.setPathWidth(uidl.getIntAttribute(pathWidth));

        sparkline.setValueVisible(uidl.getBooleanAttribute(valueVisible));
        sparkline.setValueDotVisible(uidl.getBooleanAttribute(valueDotVisible));
        sparkline.setValueColor(uidl.getStringAttribute(valueColor));

        sparkline.setNormalRangeVisible(uidl
                .getBooleanAttribute(normalRangeVisible));
        sparkline
                .setNormalRangeColor(uidl.getStringAttribute(normalRangeColor));
        sparkline.setNormalRange(uidl.getIntAttribute(normalRangeMin),
                uidl.getIntAttribute(normalRangeMax));

        sparkline.setAverageVisible(uidl.getBooleanAttribute(averageVisible));
        sparkline.setAverageColor(uidl.getStringAttribute(averageColor));

        sparkline.setMinMaxVisible(
                uidl.getBooleanAttribute(minmaxLabelsVisible),
                uidl.getBooleanAttribute(minmaxDotsVisible));
        sparkline.setMaxColor(uidl.getStringAttribute(maxColor));
        sparkline.setMinColor(uidl.getStringAttribute(minColor));

        if (uidl.hasAttribute(DATA)) {
            String[] rawData = uidl.getStringArrayAttribute(DATA);
            double[] data = new double[rawData.length];
            for (int i = 0; i < rawData.length; i++) {
                // Sigh. getDoubleArray() would have been nice...
                data[i] = Double.parseDouble(rawData[i]);
            }
            sparkline.setData(data);
        }

    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getWidget().setCaption(getState().caption);
    }

    @Override
    public boolean delegateCaptionHandling() {
        return false;
    }

    @Override
    protected SparklinesGWT createWidget() {
        return GWT.create(SparklinesGWT.class);
    }

    @Override
    public SparklinesGWT getWidget() {
        return (SparklinesGWT) super.getWidget();
    }

}
