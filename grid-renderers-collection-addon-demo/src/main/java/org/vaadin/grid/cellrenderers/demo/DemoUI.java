package org.vaadin.grid.cellrenderers.demo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;
import org.vaadin.grid.cellrenderers.editable.RatingStarsRenderer;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer.SparklineConfiguration;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.HtmlRenderer;

@Theme("valo")
@Title("Grid Renderers collection For Vaadin7 Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.grid.cellrenderers.demo.DemoWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	public class SparklineDemo extends VerticalLayout {

		public class MyPojo {
			String foo = "foo";
			String bar = "bar";
			int id = -1;
			final Number[] numbers;
			final Random rand;
			Double stars;

			public MyPojo(int i) {
				this.id = i;
				this.numbers = new Number[60];
				this.rand = new Random(this.id);
				this.stars = (double) this.rand.nextInt(10) / 2.0;
			}

			public Double getStars() {
				return this.stars;
			}

			public void setStars(Double stars) {
				this.stars = stars;
			}

			public Number[] getNumbers() {
				for (int i = 0; i < 60; i++) {
					this.numbers[i] = this.rand.nextInt() / 10000.0;
				}
				return this.numbers;
			}

			public String getFoo() {
				return this.foo;
			}

			public void setFoo(String foo) {
				this.foo = foo;
			}

			public String getBar() {
				return this.bar;
			}

			public void setBar(String bar) {
				this.bar = bar;
			}

			public int getId() {
				return this.id;
			}
		}

		private Grid createGrid(SparklineRenderer sparkline) {
			BeanItemContainer<MyPojo> container = new BeanItemContainer<MyPojo>(MyPojo.class);
			for (int i = 0; i < 1000; ++i) {
				container.addBean(new MyPojo(i));
			}

			Grid grid = new Grid(container);
			grid.setSizeFull();
			grid.getColumn("numbers")
				.setRenderer(sparkline);
			grid.getColumn("stars")
				.setRenderer(new RatingStarsRenderer(5, true));
			grid.setColumns("id", "foo", "bar", "stars", "numbers");
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
			normalRangeBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setNormalRangeVisible(!config.isNormalRangeVisible());
				}
			});
			Button averageBtn = new Button("Average");
			averageBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setAverageVisible(!config.isAverageVisible());
				}
			});
			Button minMaxBtn = new Button("Min Max");
			minMaxBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setMinMaxVisible(!config.isMinMaxVisible());
				}
			});
			Button valueBtn = new Button("Value");
			valueBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setValueVisible(!config.isValueVisible());
				}
			});
			Button valueDotBtn = new Button("Value Dot");
			valueDotBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setValueDotVisible(!config.isValueDotVisible());
				}
			});
			Button minmaxDotBtn = new Button("Minmax Dot");
			minmaxDotBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					config.setMinMaxDotsVisible(!config.isMinMaxDotsVisible());
				}
			});
			HorizontalLayout buttonsLayout = new HorizontalLayout();
			buttonsLayout.addComponents(normalRangeBtn, minMaxBtn, averageBtn, valueBtn, valueDotBtn, minmaxDotBtn);
			addComponent(buttonsLayout);
			setMargin(true);
			setSpacing(true);
		}
	}

	public class CheckBoxDemo extends VerticalLayout {

		public CheckBoxDemo() {
			Random random = new Random(4837291937l);
			BeanItemContainer<SimplePojo> container = new BeanItemContainer<SimplePojo>(SimplePojo.class);
			container.addBean(new SimplePojo(0, "Me", true, new Date(), BigDecimal.valueOf(random.nextDouble() * 100),
					Double.valueOf(random.nextInt(5))));
			container.addBean(new SimplePojo(1, "You", false, new Date(), BigDecimal.valueOf(random.nextDouble() * 100),
					Double.valueOf(random.nextInt(5))));
			container.addBean(new SimplePojo(2, "He", true, new Date(), BigDecimal.valueOf(random.nextDouble() * 100),
					Double.valueOf(random.nextInt(5))));

			Grid grid = new Grid(container);
			grid.setColumns("description", "yes", "truth", "date", "number");
			grid.setSizeFull();
			grid.setEditorEnabled(true);
			grid.setEditorBuffered(false);
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
			});
			truth.setEditable(false);

			setStyleName("demoContentLayout");
			setSizeFull();
			addComponent(grid);
			setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
			setMargin(true);
		}
	}

	public class DateTextDemo extends VerticalLayout {

		public DateTextDemo() {
			Random random = new Random(4837291937l);
			BeanItemContainer<SimplePojo> container = new BeanItemContainer<SimplePojo>(SimplePojo.class);
			for (int i = 0; i < 1000; i++) {
				container.addBean(new SimplePojo(i, "Bean", true, new Date(),
						BigDecimal.valueOf(random.nextDouble() * 100), Double.valueOf(random.nextInt(5))));
			}

			Grid grid = new Grid(container);
			grid.setColumns("description", "stars", "truth", "date", "number");
			grid.setSizeFull();
			grid.setEditorEnabled(false);
			Grid.Column yes = grid.getColumn("yes");

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
			});
			truth.setEditable(false);

			TextFieldRenderer<String> tfr = new TextFieldRenderer<String>(3);
			tfr.addClickListener(new RendererClickListener() {

				@Override
				public void click(RendererClickEvent event) {
					System.out.println("blub");
				}
			});
			grid.getColumn("description")
				.setRenderer(tfr);
			grid.getColumn("number")
				.setConverter(new StringToBigDecimalConverter());
			grid.getColumn("number")
				.setRenderer(new TextFieldRenderer<BigDecimal>());
			grid.getColumn("date")
				.setRenderer(new DateFieldRenderer());
			grid.getColumn("stars")
				.setRenderer(new RatingStarsRenderer(5, false));

			setStyleName("demoContentLayout");
			setSizeFull();
			addComponent(grid);
			setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
			setMargin(true);
		}
	}

	@Override
	protected void init(VaadinRequest request) {

		TabSheet tabSheet = new TabSheet();
		// tabSheet.addTab(new CheckBoxDemo(), "Checkbox renderer");
		tabSheet.addTab(new DateTextDemo(), "DateField and TextFieldrenderers");
		// tabSheet.addTab(new SparklineDemo(), "Sparkline renderer");
		tabSheet.setSizeFull();
		setContent(tabSheet);

	}
}
