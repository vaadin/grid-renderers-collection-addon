package org.vaadin.grid.cellrenderers.demo;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import org.vaadin.grid.cellrenderers.CheckboxRenderer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import javax.servlet.annotation.WebServlet;

@Theme("valo")
@Title("Grid Renderers collection For Vaadin7 Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.grid.cellrenderers.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        BeanItemContainer<SimplePojo> container = new BeanItemContainer<SimplePojo>(SimplePojo.class);
        container.addBean(new SimplePojo(0, "Me", true));
        container.addBean(new SimplePojo(1, "You", false));
        container.addBean(new SimplePojo(2, "He", true));

        Grid grid = new Grid(container);
        grid.setColumns("description","yes","truth");
        grid.setSizeFull();
        grid.setEditorEnabled(true);
        Grid.Column yes = grid.getColumn("yes");
        yes.setRenderer(new CheckboxRenderer());
        yes.setEditable(true);

        Grid.Column truth = grid.getColumn("truth");
        truth.setRenderer(new HtmlRenderer(), new StringToBooleanConverter() {
                    @Override
                    protected String getTrueString() {
                        return FontAwesome.CHECK_CIRCLE_O.getHtml();
                    }

                    @Override
                    protected String getFalseString() {
                        return FontAwesome.CIRCLE_O.getHtml();
                    }
                }
        );
        truth.setEditable(false);

        final VerticalLayout layout = new VerticalLayout();

        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponent(grid);
        layout.setComponentAlignment(grid
                , Alignment.MIDDLE_CENTER);
        layout.setMargin(true);
        setContent(layout);
    }

}
