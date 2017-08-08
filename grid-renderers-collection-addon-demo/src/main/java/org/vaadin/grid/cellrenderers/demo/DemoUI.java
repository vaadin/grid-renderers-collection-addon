package org.vaadin.grid.cellrenderers.demo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;

import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer.DeleteRendererClickEvent;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer.DeleteRendererClickListener;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;
import org.vaadin.grid.cellrenderers.navigation.GridNavigationExtension;
import org.vaadin.grid.cellrenderers.EditableRenderer.ItemEditEvent;
import org.vaadin.grid.cellrenderers.EditableRenderer.ItemEditListener;
import org.vaadin.grid.cellrenderers.editable.BooleanSwitchRenderer;
import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;
import org.vaadin.grid.cellrenderers.editable.RatingStarsRenderer;
import org.vaadin.grid.cellrenderers.editable.SimpleSelectRenderer;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import org.vaadin.grid.cellrenderers.view.BlobImageRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer.SparklineConfiguration;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.annotation.WebServlet;

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
    		byte[] image;
    		
    		public MyPojo(int i) {
    			id = i;
    			numbers = new Number[60];
    			rand = new Random(id);
    			stars =  (double) rand.nextInt(10) / 2.0;
    		}

    		public byte[] getImage() {
    			return image;
    		}
    		
    		public void setImage(byte[] image) {
    			this.image = image;
    		}
    		
    		public Double getStars() {
    			return stars;
    		}
    		
    		public void setStars(Double stars) {
    			this.stars = stars;
    		}
    		    		
    		public Number[] getNumbers() {
    			for (int i=0;i<60;i++) {
        			numbers[i] = rand.nextInt()/10000.0;    				
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

    	private Grid createGrid(SparklineRenderer sparkline) {
    		BeanItemContainer<MyPojo> container = new BeanItemContainer<MyPojo>(MyPojo.class);
    		for (int i = 0; i < 1000; ++i) {
    			container.addBean(new MyPojo(i));
    		}

    		Grid grid = new Grid(container);
    		grid.setSizeFull();
    		grid.getColumn("numbers").setRenderer(sparkline);
    		grid.getColumn("stars").setRenderer(new RatingStarsRenderer(5));
    		grid.getColumn("stars").setEditable(false);
    		grid.setColumns("id", "foo", "bar", "stars", "numbers");
    		return grid;
    	}

    	public SparklineDemo() {
    		SparklineRenderer sparkline = new SparklineRenderer(200,30); 
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

    	public static final String castle = "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";
    	
		public CheckBoxDemo() {		
			Random random = new Random(4837291937l);
			BeanItemContainer<SimplePojo> container = new BeanItemContainer<SimplePojo>(SimplePojo.class);
			SimplePojo bean1 = new SimplePojo(0, "Me", true, new Date(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			SimplePojo bean2 = new SimplePojo(1, "You", false, new Date(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			SimplePojo bean3 = new SimplePojo(2, "He", true, new Date(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			container.addBean(bean1);
			container.addBean(bean2);
			container.addBean(bean3);
			byte[] image = Base64.decodeBase64(castle);
			bean1.setImage(image);
			bean2.setImage(image);
			bean3.setImage(image);
			
			Grid grid = new Grid(container);
			grid.setColumns("description","yes","truth","date","number","image");
			grid.setSizeFull();
			grid.setEditorEnabled(true);
			grid.setEditorBuffered(false);
			Grid.Column yes = grid.getColumn("yes");
			yes.setRenderer(new CheckboxRenderer());
			yes.setEditable(true);
			grid.getColumn("image").setEditable(false);
    		grid.getColumn("image").setRenderer(new BlobImageRenderer(30,30,"image/png"));
 
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
			setComponentAlignment(grid
					, Alignment.MIDDLE_CENTER);
			setMargin(true);
		}  
	}
	
   
	public class DateTextDemo extends VerticalLayout {

		public DateTextDemo() {		
			Random random = new Random(4837291937l);
			final BeanItemContainer<SimplePojo> container = new BeanItemContainer<SimplePojo>(SimplePojo.class);
			for (int i=0;i<1000;i++) {
				container.addBean(new SimplePojo(i, "Bean", true, new Date(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5))));
			}

			Grid grid = new Grid(container);
			GridNavigationExtension.extend(grid);
			
			grid.setColumns("action","description","stars","truth","date","number","choice");
			grid.setSizeFull();
			grid.setEditorEnabled(false);

			DeleteButtonRenderer deleteButton = new DeleteButtonRenderer(new DeleteRendererClickListener() {
				@Override
				public void click(DeleteRendererClickEvent event) {
					container.removeItem(event.getItem());
				}
				
			},FontAwesome.TRASH.getHtml()+" Delete",FontAwesome.CHECK.getHtml()+" Confirm");
			deleteButton.setHtmlContentAllowed(true);
			grid.getColumn("action").setRenderer(deleteButton);

			BooleanSwitchRenderer booleanRenderer = new BooleanSwitchRenderer("True","False");
			booleanRenderer.addItemEditListener(new ItemEditListener() {
				@Override
				public void itemEdited(ItemEditEvent event) {
					Notification.show("Property " + event.getColumnPropertyId() + " edited with value " + event.getNewValue().toString());				
				}
				
			} );
			grid.getColumn("truth").setRenderer(booleanRenderer);

			TextFieldRenderer<String> textFieldRenderer = new TextFieldRenderer<String>();
			textFieldRenderer.addItemEditListener(new ItemEditListener() {
				@Override
				public void itemEdited(ItemEditEvent event) {
					Notification.show("Property " + event.getColumnPropertyId() + " edited with value " + event.getNewValue().toString());				
				}
				
			} );
			grid.getColumn("description").setRenderer(textFieldRenderer);
			grid.getColumn("number").setConverter(new StringToBigDecimalConverter());
			TextFieldRenderer<BigDecimal> decimalFieldRenderer = new TextFieldRenderer<BigDecimal>();
			decimalFieldRenderer.addItemEditListener(new ItemEditListener() {
				@Override
				public void itemEdited(ItemEditEvent event) {
					Notification.show("Property " + event.getColumnPropertyId() + " edited with value " + event.getNewValue().toString());				
				}
				
			} );
			grid.getColumn("number").setRenderer(decimalFieldRenderer);
			grid.getColumn("date").setRenderer(new DateFieldRenderer());
			grid.getColumn("stars").setRenderer(new RatingStarsRenderer(5));
			
			MyStringToIntegerConverter myConverter = new MyStringToIntegerConverter();
			grid.getColumn("choice").setConverter(myConverter);
			SimpleSelectRenderer<Integer> choiceFieldRenderer = new SimpleSelectRenderer<Integer>(Arrays.asList(1,2,3,4,5),myConverter,"Select a number 1 to 5 fromt the drop down");
			choiceFieldRenderer.addItemEditListener(new ItemEditListener() {
				@Override
				public void itemEdited(ItemEditEvent event) {
					Notification.show("Property " + event.getColumnPropertyId() + " edited with value " + event.getNewValue().toString());				
				}
				
			} );
			grid.getColumn("choice").setRenderer(choiceFieldRenderer);			
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
