package org.vaadin.grid.cellrenderers.demo;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import org.vaadin.grid.cellrenderers.editable.RatingStarsRenderer;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer.SparklineConfiguration;

import javax.servlet.annotation.WebServlet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Theme("valo")
@Title("Grid Renderers collection For Vaadin7 Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.grid.cellrenderers.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @SuppressWarnings("WeakerAccess")
    public class SparklineDemo extends VerticalLayout {

        @SuppressWarnings({"WeakerAccess", "unused"})
        public class MyPojo {
            String foo = "foo";
            String bar = "bar";
            int id = -1;
            final Number[] numbers;
            final Random rand;
            Double stars;

            public MyPojo(int i) {
                id = i;
                numbers = new Number[60];
                rand = new Random(id);
                stars = (double) rand.nextInt(10) / 2.0;
            }

            public Double getStars() {
                return stars;
            }

            public void setStars(Double stars) {
                this.stars = stars;
            }

            public Number[] getNumbers() {
                for (int i = 0; i < 60; i++) {
                    numbers[i] = rand.nextInt() / 10000.0;
                }
                return numbers;
            }

            public String getFoo() {
                return foo;
            }

            public void setFoo(String foo) {
                this.foo = foo;
            }

            public String getBar() {
                return bar;
            }

            public void setBar(String bar) {
                this.bar = bar;
            }

            public int getId() {
                return id;
            }
        }

        private Grid<MyPojo> createGrid(SparklineRenderer sparkline) {
            List<MyPojo> beanList = new ArrayList<>();
            for (int i = 0; i < 1000; ++i) {
                beanList.add(new MyPojo(i));
            }

            Grid<MyPojo> grid = new Grid<>();
            grid.setItems(beanList);
            grid.setSizeFull();
            grid.addColumn(MyPojo::getId, new NumberRenderer()).setCaption("Id");
            grid.addColumn(MyPojo::getFoo).setCaption("Foo");
            grid.addColumn(MyPojo::getBar).setCaption("Bar");
            grid.addColumn(MyPojo::getStars, new RatingStarsRenderer<>(5, MyPojo::setStars));
            grid.addColumn(MyPojo::getNumbers, sparkline);
            return grid;
        }

        public SparklineDemo() {
            SparklineRenderer sparkline = new SparklineRenderer(200, 30);
            final SparklineConfiguration config = sparkline.getConfiguration();

            config.setPathWidth(2);
            config.setMinMaxColor("#f96");

            Grid grid = createGrid(sparkline);
            addComponent(grid);
            Button normalRangeBtn = new Button("Normal range");
            normalRangeBtn.addClickListener((ClickListener) event -> config.setNormalRangeVisible(!config.isNormalRangeVisible()));
            Button averageBtn = new Button("Average");
            averageBtn.addClickListener((ClickListener) event -> config.setAverageVisible(!config.isAverageVisible()));
            Button minMaxBtn = new Button("Min Max");
            minMaxBtn.addClickListener((ClickListener) event -> config.setMinMaxVisible(!config.isMinMaxVisible()));
            Button valueBtn = new Button("Value");
            valueBtn.addClickListener((ClickListener) event -> config.setValueVisible(!config.isValueVisible()));
            Button valueDotBtn = new Button("Value Dot");
            valueDotBtn.addClickListener((ClickListener) event -> config.setValueDotVisible(!config.isValueDotVisible()));
            Button minmaxDotBtn = new Button("Minmax Dot");
            minmaxDotBtn.addClickListener((ClickListener) event -> config.setMinMaxDotsVisible(!config.isMinMaxDotsVisible()));
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            buttonsLayout.addComponents(normalRangeBtn, minMaxBtn, averageBtn, valueBtn, valueDotBtn, minmaxDotBtn);
            addComponent(buttonsLayout);
            setMargin(true);
            setSpacing(true);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class CheckBoxDemo extends VerticalLayout {

        public CheckBoxDemo() {
            Random random = new Random(4837291937L);
            Grid<SimplePojo> grid = new Grid<>();
            grid.setItems(
                    new SimplePojo(0, "Me", true, new Date(), BigDecimal.valueOf(random.nextDouble() * 100), (double) random.nextInt(5)),
                    new SimplePojo(1, "You", false, new Date(), BigDecimal.valueOf(random.nextDouble() * 100), (double) random.nextInt(5)),
                    new SimplePojo(2, "He", true, new Date(), BigDecimal.valueOf(random.nextDouble() * 100), (double) random.nextInt(5))
            );

            grid.addColumn(SimplePojo::getDescription).setCaption("Description");
            grid.addColumn(SimplePojo::isYes, new CheckboxRenderer<>(SimplePojo::setYes)).setCaption("Yes").setEditorComponent(new CheckBox()).setEditable(true);
            grid.addColumn(
                    simplePojo -> (simplePojo.isTruth() ? FontAwesome.CHECK_CIRCLE_O : FontAwesome.CIRCLE_O).getHtml()
                    , new HtmlRenderer()).setCaption("Truth");

            grid.addColumn(SimplePojo::getDate, new DateRenderer()).setCaption("Date")
                    .setEditorComponent(new DateField())
                    .setEditable(true);
            grid.addColumn(SimplePojo::getNumber, new NumberRenderer()).setCaption("Number")
                    .setEditorComponent(new TextField())
                    .setEditable(true);

            grid.setSizeFull();
            grid.getEditor().setEnabled(true);
            grid.getEditor().setBuffered(false);

            //TODO editable all fields except Truth

            setStyleName("demoContentLayout");
            setSizeFull();
            addComponent(grid);
            setComponentAlignment(grid
                    , Alignment.MIDDLE_CENTER);
            setMargin(true);
        }
    }


    @SuppressWarnings("WeakerAccess")
    public class DateTextDemo extends VerticalLayout {

        public DateTextDemo() {
            Random random = new Random(4837291937L);
            List<SimplePojo> pojoList = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                pojoList.add(new SimplePojo(i, "Bean", true, new Date(),
                        BigDecimal.valueOf(random.nextDouble() * 100), (double)random.nextInt(5)));
            }

            Grid<SimplePojo> grid = new Grid<>();
            grid.setItems(pojoList);

            grid.addColumn(SimplePojo::getDescription, new TextFieldRenderer<>(null)).setCaption("Description");
            grid.addColumn(SimplePojo::getStars, new RatingStarsRenderer<>(5, null)).setCaption("Stars");
            grid.addColumn(
                    simplePojo -> (simplePojo.isTruth() ? FontAwesome.CHECK_CIRCLE_O : FontAwesome.CIRCLE_O).getHtml()
                    , new HtmlRenderer()).setCaption("Truth")/*todo file bug!.setEditable(false)*/;

            grid.addColumn(SimplePojo::getDate, new DateRenderer()).setCaption("Date")/*todo file bug!.setEditable(false)*/;
            grid.addColumn(simplePojo -> String.valueOf(simplePojo.getNumber()),new TextFieldRenderer<>(null))
                    .setCaption("Number");

            grid.setSizeFull();
            grid.getEditor().setEnabled(false);

            setStyleName("demoContentLayout");
            setSizeFull();
            addComponent(grid);
            setComponentAlignment(grid
                    , Alignment.MIDDLE_CENTER);
            setMargin(true);
        }
    }

    @Override
    protected void init(VaadinRequest request) {

        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new CheckBoxDemo(), "Checkbox renderer");
        tabSheet.addTab(new DateTextDemo(), "DateField and TextField renderers");
        tabSheet.addTab(new SparklineDemo(), "Sparkline renderer");
        tabSheet.setSizeFull();
        setContent(tabSheet);

    }
}
