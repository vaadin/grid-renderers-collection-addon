package org.vaadin.sparklines;

import com.vaadin.annotations.PropertyId;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Theme("sparklines")
public class SparklinesRoot extends UI {
    private Window configure;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        content.setStyleName("black");
        content.setHeight("100%");
        content.setMargin(true);

        Label info = new Label(
                "<h1>Sparklines for GWT and Vaadin</h1><h2>\"small, intense, simple datawords\"</h2><a href=\"http://www.edwardtufte.com/bboard/q-and-a-fetch-msg?msg_id=0001OR\">Refer to Edward Tufte for more information about sparklines</a> (must read)");
        info.setContentMode(ContentMode.HTML);
        content.addComponent(info);

        List<Integer> data1 = Arrays.asList(60, 62, 55, 62, 63, 64, 63, 65, 68,
                65, 69, 70, 75, 74, 75, 74, 78, 76, 74, 85, 70, 65, 63, 64, 69,
                74, 65);
        Integer[] data2 = new Integer[30];
        List<Integer> data3 = Arrays.asList(60, 62, 55, 62, 63, 64, 63, 65, 68,
                65, 69, 70, 75, 74, 75, 74, 78, 76, 74, 85, 70, 65, 63, 64, 69,
                74, 55);

        Sparklines s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("All extras turned off");
        s.setValue(data1);
        s.setValueDotVisible(false);
        s.setValueLabelVisible(false);
        s.setMinmaxLabelsVisible(false);
        s.setMinmaxDotsVisible(false);
        content.addComponent(s);
        s.setWidth("100px");
        s.setHeight("50px");

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Shows current value, visually connected to graph with color");
        s.setValue(data1);
        s.setMinmaxLabelsVisible(false);
        s.setMinmaxDotsVisible(false);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Current, minimum and maximum values are shown");
        s.setValue(data1);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Adds line indicating average");
        s.setValue(data1);
        s.setAverageVisible(true);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Shaded area indicates 'normal' range");
        s.setValue(data1);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Everything turned on");
        s.setValue(data1);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Color indicates if min/max is the current value");
        s.setValue(data3);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        content.addComponent(s);

        for (int i = 0; i < data2.length; i++) {
            data2[i] = (int) (Math.random() * 140 + 60);
        }
        s = new Sparklines("Pulse", 0, 0, 0, 200);
        s.setDescription("Minimum and maximum can be color-connected as well");
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        s.setValue(Arrays.asList(data2));
        content.addComponent(s);

        data2 = new Integer[30];
        for (int i = 0; i < data2.length; i++) {
            data2[i] = (int) (Math.random() * 140 + 60);
        }

        HorizontalLayout hl = new HorizontalLayout();
        content.addComponent(hl);
        ((VerticalLayout) getContent()).setExpandRatio(hl, 1);
        ((VerticalLayout) getContent()).setComponentAlignment(hl,
                Alignment.MIDDLE_LEFT);

        final Sparklines s2 = new Sparklines("Random", 200, 70, 0, 200) {
            // Round to two decimal points.
            // Can be used for lotsa stuff, though.

            @Override
            protected List<Number> translateData(List<Number> data) {
                List<Number> d = super.translateData(data);
                for (int i = 0; i < d.size(); i++) {
                    d.set(i, Math.round(d.get(i).doubleValue() * 100.0) / 100.0);
                }
                return d;
            }

        };
        s2.setWidth("380px");
        s2.setValueColor("#69f");
        s2.setPathWidth(2);
        // s2.setNormalRangeVisible(true);
        // s2.setNormalRangeMax(130);
        // s2.setNormalRangeMin(80);
        // s2.setNormalRangeColor("#444");
        // s2.setAverageVisible(true);
        // s2.setAverageColor("#999");
        s2.setMaxColor("#f69");
        s2.setMinColor("#6f9");
        s2.setDescription("40 random values between 40 and 200");
        s2.setValue(random());


        hl.addComponent(s2);
        hl.setComponentAlignment(s2, Alignment.MIDDLE_LEFT);

        Button rand = new Button("Randomize", (Button.ClickListener) event -> s2.setValue(random()));
        hl.addComponent(rand);
        hl.setComponentAlignment(rand, Alignment.MIDDLE_LEFT);

        configure = new ConfigureWindow(s2);
        Button conf = new Button("Configure", (Button.ClickListener) event -> {
            if (configure.getParent() == null || !configure.isVisible()) {
                addWindow(configure);
            }
        });
        hl.addComponent(conf);
        hl.setComponentAlignment(conf, Alignment.MIDDLE_LEFT);

    }

    private Double[] random() {
        Double[] data = new Double[40];
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.random() * 160 + 40;
        }
        return data;
    }

    class ConfigureWindow extends Window {
        private CheckBox averageVisible = new CheckBox("Average Visible");
        private TextField averageColor = new TextField("Average Color");
        private TextField caption = new TextField("Caption");
        private TextField description = new TextField("Description");
        private TextField displayRangeMax = new TextField("Display Range Max");
        private TextField displayRangeMin = new TextField("Display Range Min");
        private TextField graphHeight = new TextField("Graph Height");
        private TextField graphWidth = new TextField("Graph Width");
        private CheckBox minmaxDotsVisible = new CheckBox("Minmax Dots Visible");
        private CheckBox minmaxLabelsVisible = new CheckBox("Minmax Labels Visible");
        private TextField maxColor = new TextField("Maxcolor");
        private TextField minColor = new TextField("Mincolor");
        private CheckBox normalRangeVisible = new CheckBox("Normal Range Visible");
        private TextField normalRangeColor = new TextField("Normal Range Color");
        private TextField normalRangeMin = new TextField("Normal Range Min");
        private TextField normalRangeMax = new TextField("Normal Range Max");
        private TextField pathColor = new TextField("Path Color");
        private TextField pathWidth = new TextField("Path Width");
        private CheckBox valueDotVisible = new CheckBox("Value Dot Visible");
        private CheckBox valueLabelVisible = new CheckBox("Value Label Visible");
        private TextField valueColor = new TextField("Value Color");

        ConfigureWindow(Sparklines s) {
            VerticalLayout content = new VerticalLayout();
            content.addComponents(averageVisible);
            content.addComponents(averageColor);
            content.addComponents(caption);
            content.addComponents(description);
            content.addComponents(displayRangeMax);
            content.addComponents(displayRangeMin);
            content.addComponents(graphHeight);
            content.addComponents(graphWidth);
            content.addComponents(minmaxDotsVisible);
            content.addComponents(minmaxLabelsVisible);
            content.addComponents(maxColor);
            content.addComponents(minColor);
            content.addComponents(normalRangeVisible);
            content.addComponents(normalRangeColor);
            content.addComponents(normalRangeMin);
            content.addComponents(normalRangeMax);
            content.addComponents(pathColor);
            content.addComponents(pathWidth);
            content.addComponents(valueDotVisible);
            content.addComponents(valueLabelVisible);
            content.addComponents(valueColor);

            setContent(content);

            setWidth("400px");
            setHeight("300px");

            final AbstractComponentContainer conf = new VerticalLayout();
            Binder<Sparklines> binder = new Binder<>(Sparklines.class);
            //todo report Property id is not reported
            //todo report StandardConverter through annotation
            binder.bindInstanceFields(this);
            content.addComponent(conf);
            binder.setBean(s);

            content.addComponent(new Button("Apply",
                    (Button.ClickListener) event -> binder.writeBeanIfValid(s)));
        }
    }

    @VaadinServletConfiguration(productionMode = false,
            widgetset = "org.vaadin.grid.cellrenderers.demo.DemoWidgetSet",
            ui = SparklinesRoot.class)
    @WebServlet(name="SparkLinesServlet",urlPatterns = "/sparklines/*")
    public static class SparkLinesServlet extends VaadinServlet {

    }
}
