package org.vaadin.grid.cellrenderers.demo;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.icons.VaadinIcons;

import org.vaadin.grid.cellrenderers.action.BrowserOpenerRenderer;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer.DeleteRendererClickEvent;
import org.vaadin.grid.cellrenderers.action.DeleteButtonRenderer.DeleteRendererClickListener;
import org.vaadin.grid.cellrenderers.action.AbstractHtmlButtonRenderer;
import org.vaadin.grid.cellrenderers.action.HtmlButtonRenderer;
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
import org.vaadin.grid.cellrenderers.view.ConverterRenderer;
import org.vaadin.grid.cellrenderers.view.RowIndexRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer.SparklineConfiguration;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.annotation.WebServlet;

@Push
@Theme("demo")
@Title("Grid Renderers collection For Vaadin7 Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.grid.cellrenderers.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    public class SparklineDemo extends VerticalLayout {

    	public class MyPojo {
    		private String foo = "fragment";
    		private String bar = "<B>Vaadin</B> "+VaadinIcons.VAADIN_H.getHtml();
    		private int id = -1;
    		private final Number[] numbers;
    		private final Random rand;
    		private Double stars;
    		private byte[] image;
    		private BigDecimal number;
    		private ClassResource file;
    		
    		public MyPojo(int i) {
    			id = i;
    			numbers = new Number[60];
    			rand = new Random(id);
    			for (int j=0;j<60;j++) {
        			numbers[j] = rand.nextInt()/10000.0;    				
    			}
    			stars =  (double) rand.nextInt(10) / 2.0;
    			setNumber(BigDecimal.valueOf(rand.nextDouble()*100));
    			foo = foo+i;
    			file = new ClassResource("/lorem_ipsum.pdf");
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
    			return numbers;
    		}

    		public String getFoo() {
    			return foo;
    		}

    		public void setFoo(String foo) {
    			this.foo = foo;
    		}

    		public ClassResource getFile() {
    			return file;
    		}

    		public void setFile(ClassResource file) {
    			this.file = file;
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

			public BigDecimal getNumber() {
				return number;
			}

			public void setNumber(BigDecimal number) {
				this.number = number;
			}
    	}

    	private Grid<MyPojo> createGrid(SparklineRenderer<MyPojo> sparkline) {
    		List<MyPojo> beanList = new ArrayList<>();
    		for (int i = 0; i < 1000; ++i) {
    			beanList.add(new MyPojo(i));
    		}

    		Grid<MyPojo> grid = new Grid<>();
    		grid.setItems(beanList);
    		grid.setSizeFull();
    		grid.addColumn(value -> "", new RowIndexRenderer(true)).setCaption("Row index");;
    		RowIndexRenderer rowIndex = new RowIndexRenderer(true); 
    		rowIndex.setOffset(1);
    		grid.addColumn(value -> "", rowIndex).setCaption("Row index");
    		grid.addColumn(MyPojo::getId, new NumberRenderer()).setCaption("Id");
			BrowserOpenerRenderer<MyPojo,String> openButton = new BrowserOpenerRenderer<>(DemoUI.class, VaadinIcons.BROWSER.getHtml() ,clickEvent -> {
				MyPojo item = clickEvent.getItem();
				Notification.show("Browser open button has been clicked: "+item.getFoo());
			});
			openButton.setHtmlContentAllowed(true);
			openButton.setTooltipEnabled(true);
			openButton.setParameter("name", "tatu");
			grid.addColumn(MyPojo::getFoo, openButton).setCaption("Open Vaadin");
			HtmlButtonRenderer<MyPojo> htmlButton = new HtmlButtonRenderer<MyPojo>(clickEvent -> {
				Notification.show("HTML button has been clicked");
			});
			htmlButton.setHtmlContentAllowed(true);
			grid.addColumn(MyPojo::getBar, htmlButton).setCaption("Click Vaadin");

    		BrowserOpenerRenderer openButton2 = new BrowserOpenerRenderer(VaadinIcons.FILE.getHtml(), clickEvent-> {});
    		openButton2.setHtmlContentAllowed(true);
    		openButton2.setTooltipEnabled(true);
			grid.addColumn(MyPojo::getFile, openButton2).setCaption("Open File");
    		
    		grid.addColumn(MyPojo::getNumber, new ConverterRenderer<MyPojo,BigDecimal>(new MyStringToBigDecimalConverter("Error message"))).setCaption("Number");
    		RatingStarsRenderer<MyPojo> ratingStars = new RatingStarsRenderer<>(MyPojo::setStars, 5);
    		ratingStars.setReadOnly(true);
    		grid.addColumn(MyPojo::getStars, ratingStars).setCaption("Rating");
    		
    		grid.addColumn(MyPojo::getNumbers, sparkline).setCaption("A graph");
    		return grid;
    	}

    	public SparklineDemo() {
    		SparklineRenderer<MyPojo> sparkline = new SparklineRenderer<>(200,30,"Chart"); 
    		final SparklineConfiguration config = sparkline.getConfiguration();

    		config.setPathWidth(2);
    		config.setMinMaxColor("#f96");
		
    		Grid grid = createGrid(sparkline);
    		addComponent(grid);
    		Button normalRangeBtn = new Button("Normal range");
    		normalRangeBtn.addClickListener(event -> {
				config.setNormalRangeVisible(!config.isNormalRangeVisible());
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
			setStyleName("demoContentLayout");
    		setMargin(true);
    		setSpacing(true);
    	}
    }

	public class CheckBoxDemo extends VerticalLayout {

    	public static final String castle = "R0lGODlhOgA5APcAAAAAAAAAMwAAZgAAmQAAzAAA/wArAAArMwArZgArmQArzAAr/wBVAABVMwBVZgBVmQBVzABV/wCAAACAMwCAZgCAmQCAzACA/wCqAACqMwCqZgCqmQCqzACq/wDVAADVMwDVZgDVmQDVzADV/wD/AAD/MwD/ZgD/mQD/zAD//zMAADMAMzMAZjMAmTMAzDMA/zMrADMrMzMrZjMrmTMrzDMr/zNVADNVMzNVZjNVmTNVzDNV/zOAADOAMzOAZjOAmTOAzDOA/zOqADOqMzOqZjOqmTOqzDOq/zPVADPVMzPVZjPVmTPVzDPV/zP/ADP/MzP/ZjP/mTP/zDP//2YAAGYAM2YAZmYAmWYAzGYA/2YrAGYrM2YrZmYrmWYrzGYr/2ZVAGZVM2ZVZmZVmWZVzGZV/2aAAGaAM2aAZmaAmWaAzGaA/2aqAGaqM2aqZmaqmWaqzGaq/2bVAGbVM2bVZmbVmWbVzGbV/2b/AGb/M2b/Zmb/mWb/zGb//5kAAJkAM5kAZpkAmZkAzJkA/5krAJkrM5krZpkrmZkrzJkr/5lVAJlVM5lVZplVmZlVzJlV/5mAAJmAM5mAZpmAmZmAzJmA/5mqAJmqM5mqZpmqmZmqzJmq/5nVAJnVM5nVZpnVmZnVzJnV/5n/AJn/M5n/Zpn/mZn/zJn//8wAAMwAM8wAZswAmcwAzMwA/8wrAMwrM8wrZswrmcwrzMwr/8xVAMxVM8xVZsxVmcxVzMxV/8yAAMyAM8yAZsyAmcyAzMyA/8yqAMyqM8yqZsyqmcyqzMyq/8zVAMzVM8zVZszVmczVzMzV/8z/AMz/M8z/Zsz/mcz/zMz///8AAP8AM/8AZv8Amf8AzP8A//8rAP8rM/8rZv8rmf8rzP8r//9VAP9VM/9VZv9Vmf9VzP9V//+AAP+AM/+AZv+Amf+AzP+A//+qAP+qM/+qZv+qmf+qzP+q///VAP/VM//VZv/Vmf/VzP/V////AP//M///Zv//mf//zP///wAAAAAAAAAAAAAAACH5BAEAAPwALAAAAAA6ADkAAAj/AIlp0iRQGcGBBgUeTAiqIMGGAzVlikix4kBiDTNi3GjQIkRNyjIaHAny4cOEE4dJJJYSVCZin2BejDlyJKiHAl8iPAiRZMJPOEFmSmMwo6abCilmMpoM47CPFF0qhJipo6anJZVNBJopE5quG5OBBCVWa8eJMjUCJbj04MCJEyNW3VmSJaY0mCZ17Up3Y8OYoLCyvIoQFMOdHQdjHKiy6iRMkiJL4oWJUd6JYudO9DlQbMaXAjEqc5jwaMmVpiWJUS1MUpjWYXhJ1ulzs8SzEiMmhnjTYMyVRjE+ltx6GOXWrSVlLdlwac6cBsUeXVwXqdGOgSVOFsZumDBh84R5/2+NaVjVwM2tH217evdiktPhah0I+Xt4SfPyf/c+yS9Jpz0RtpRGBCrknGmJacfdMPIkww4y88jTjjCUlbVcSKI9hdFHVqln0UGAdcWLMJjMw2Ay+Y0nHkGAYWQhZ09NtIlpA/Vk1E03SaSXJOUJkwyK+eUzD4TDmNeRWRcKRZ1HNUYlVyaSSSKeePM8M8+PJqKoEno4LRUjjlc1BMcmY46pyYxnHoXmTWhIhomP3s0jZH5aIvPjMEdelxUxTRkFx0B/BiroQGhOwiNl3mFZoj750JOPlsls9pJL2Pl11JYzjmnHmX9uAkocnAZq6Hc9Dpllo3Mmg8wwgGnyG4uDAf+1pUJmahIHKGVq8uemmtwhkXjDJOPjqlcGiaKWmORU41ymCXQTXJ8eBaqupsERykDTdlUeMpFlMs8lxs6DiZCPmjfdR166GuaS1EZL5lF/XjtmJkXCaV+wc4ZHj36RNomjs03mptK113pqWsG7wotGoj1aGd6P+Qwj5J3CoIFGRGA22xuNm266iR3vwgGKp2hqgkl5rB0rj5xyogihsJmId5e/Af87siYF53iTyLretKkwMaMRhrARAkkunVeKl4x3krwh8sembQzmyJte+0Ymk7yh5rPeDSPGGcO8nF+QY78ctrDJlMfInyNPfTDOpnUl2WMYmxxsGAv7yPLRDqP/rfSU3mmNI8lt49j2JBRCJlncwc5zhpTJrIxiqqai6OPfwpaHyRueGr71taC4FKx5Mh/lUuZioPEdipKzPPaPUwobLOxFXuwpwaGb7unsfwvDNXjDPO5j6y0Xa/mPficadrADml536MH6CHNkaIixLSar7fcykEhf3s6qq6q6vLBF4W6xGBOpvnRyZ1gsSSSYsCOs9T1OGT4ywjx6pSRLyxO9iZdLRkgMYxhNiCEMPcAb2JaGifa5CXIVu0H2SKQ4xfFodG9a1baWF76nFAMUoLvBDcJwA6FZLi+QKc8wMBEz7IWBR0CLhGowsYg2EQcTlIlZ7MRjJzyFroBuGOEI/8Nwhit5500thMwRDxiJFSYnMg2EIolWGDMerQpwwiJgSDSBBhGGAQdhwITsNAc08UgJMu07GXlkaMEUOrGMKzyZHMUzqZB45QYx8CL/aAfHFEYCDWx8XxhTyCMYFqeNw7CMdyiTQhaap4sxCAMRSxQeKpIoaJh44eMkgYYzhPGQZ4yZd0SpxpOtMFiSwODJSEjCEYpxaRpcYWTOAL+TSYKWQosMib7TwiN654qj+9GbTAksyLDSlUlr4S3P8DjFtQlvf/TkGcjYIx3C8nL78VF57vQmMQxxhN6CHSYiwUwpPa5NncSlaxzYzCkig4VlZCDaZLe0ZECDRJIQ4gj1hv+JS3ztfZiwoScjsZpZRumWqQQc+BLFS/BlIhlowEQ7MmFML95geZEgJzkzEQlGXEJogAzpOWXoyQtiAhkxQ5sYFdq1YYSBokwbYR6nectIXAKhtwTkAQu5SZ3iDaFvGp/SNLjStKXtpavLZx5HqJo/RjGMCG0mOnMaTU1aJqj4g2WwUui3TITBenGUoAStRyKoRtQrMpTMGcSQVoq6JqBAXd4nshnDPVJQkm+SkhhwIIbUuXWvX8Rb6iDTppJGNDI/vSUj2mRL6gX0TRbb5jA4uZoXWuwHFrMhJ9d6QKF9DW9fJWJoE8tJg3ayk+tMXRgWwaO8tq+GB0wdDsaAhjT/cLJ658Pt1z4b2vZJ0pO/DS1bY7tWSe72hTBsJQn5itvSqka3ID3uCItLwgN68mvotOwfqZfGQnoWDYxYKw7Gi4OvODOnuPWk0ET7WaEVV2gEPUMNQarJZ972tuisXnn7qjrC/pGtnQzvZ61rXEmKVoHoPMNiS4uGGjq3TanjpIBvAMav8AjCa3VNJ796hiFumJm7XStBt/vHEheWu/fN7F6r5wMozhK31VPweo872rWK+MRTteFUqffc54bBB2PYL2Fhu1aLgdaT0/2aNAmcywaH1MQlZnBmVSwGH6CBtgwWLEjpe8ADtxe0232waiSc2SIvNnV9xZt+0Yzm79IXV8nqZaaB3dtecgKYpI+T8XPbHGNmtoHLEqywcMGIXSKGuIaavOVq2rdg8J4TvxabRPVuQEuNnuESZ2hDJDTtBnL6dq3TBS1onQxaTr5QgTaOcZNTp9uAAAA7";
    	
		public CheckBoxDemo() {		
			Random random = new Random(4837291937l);
			Grid<SimplePojo> grid = new Grid<>();
			List<SimplePojo> beanList = new ArrayList<>();
			SimplePojo bean1 = new SimplePojo(0, "Me", true, LocalDate.now(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			SimplePojo bean2 = new SimplePojo(1, "You", false, LocalDate.now(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			SimplePojo bean3 = new SimplePojo(2, "He", true, LocalDate.now(), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5)));
			byte[] image = Base64.decodeBase64(castle);
			bean1.setImage(image);
			bean2.setImage(image);
			bean3.setImage(image);
			beanList.add(bean1);
			beanList.add(bean2);
			beanList.add(bean3);
			grid.setItems(beanList);
			
			grid.addColumn(SimplePojo::getDescription).setCaption("Description");

			grid.addColumn(SimplePojo::isYes, new CheckboxRenderer<>(SimplePojo::setYes))
					.setCaption("Yes")
					.setEditorComponent(new CheckBox(), SimplePojo::setYes).setEditable(true);

			grid.addColumn(simplePojo -> (simplePojo.isTruth() ? VaadinIcons.CHECK_CIRCLE : VaadinIcons.CIRCLE).getHtml()
					, new HtmlRenderer())
					.setCaption("Truth");

			grid.addColumn(SimplePojo::getImage, new BlobImageRenderer(40,40,"image/png"))
				.setCaption("Image");
			
			grid.setSizeFull();
			grid.getEditor().setEnabled(true);
			grid.getEditor().setBuffered(true);
			grid.setRowHeight(50);
    
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
			final List<SimplePojo> beanList = new ArrayList<>();
			for (int i=0;i<300;i++) {
				boolean truth = (Integer.valueOf(random.nextInt(2)) == 1);
				beanList.add(new SimplePojo(i, "Bean", truth, LocalDate.now().plusDays(i), BigDecimal.valueOf(random.nextDouble()*100), Double.valueOf(random.nextInt(5)), Integer.valueOf(random.nextInt(5))));
			}

			Grid<SimplePojo> grid = new Grid<>();
			grid.setItems(beanList);
			GridNavigationExtension.extend(grid);
			
			grid.setSizeFull();
			grid.getEditor().setEnabled(false);
			
			// Add column with Delete button and event
			DeleteButtonRenderer<SimplePojo> deleteButton = new DeleteButtonRenderer<SimplePojo>(clickEvent -> {
				beanList.remove(clickEvent.getItem());
				grid.getDataProvider().refreshAll();
				
			},VaadinIcons.TRASH.getHtml()+" Delete",VaadinIcons.CHECK.getHtml()+" Confirm");
			deleteButton.setHtmlContentAllowed(true);
			grid.addColumn(SimplePojo::getAction, deleteButton).setCaption("Action");

			// Add column with Boolean check box and event 
			BooleanSwitchRenderer<SimplePojo> booleanRenderer = new BooleanSwitchRenderer<>(SimplePojo::setTruth,"True","False");
			booleanRenderer.addItemEditListener(event ->  {
					Notification.show("Column " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());				
				});
			booleanRenderer.setIsEnabledProvider(item -> {return item.getStars() < 5.0;});
			grid.addColumn(SimplePojo::isTruth, booleanRenderer).setCaption("Truth");
			
			TextFieldRenderer<SimplePojo,String> textFieldRenderer = new TextFieldRenderer<>(SimplePojo::setDescription);
			textFieldRenderer.setBlurChangeMode(true);
			textFieldRenderer.addItemEditListener(event -> {
					Notification.show("Column " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());
					SimplePojo pojo = (SimplePojo) event.getItem();
					pojo.setTruth(!pojo.isTruth());
				});
			grid.addColumn(SimplePojo::getDescription, textFieldRenderer).setCaption("Description");

			
			TextFieldRenderer<SimplePojo,BigDecimal> decimalFieldRenderer = new TextFieldRenderer<>(SimplePojo::setNumber);
			decimalFieldRenderer.setConverter(new StringToBigDecimalConverter("Error message"));
			decimalFieldRenderer.addItemEditListener(event -> {
					Notification.show("Property " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());								
			});
			grid.addColumn(SimplePojo::getNumber, decimalFieldRenderer).setCaption("Big decimal");

			DateFieldRenderer<SimplePojo> dateFieldRenderer = new DateFieldRenderer<SimplePojo>(SimplePojo::setDate);
			dateFieldRenderer.setBlurChangeMode(true);
			dateFieldRenderer.setDateResolution(DateResolution.DAY);
			dateFieldRenderer.addItemEditListener(event -> {
				Notification.show("Column " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());
				SimplePojo pojo = (SimplePojo) event.getItem();
			});
			grid.addColumn(SimplePojo::getDate, dateFieldRenderer).setCaption("Date");

			RatingStarsRenderer<SimplePojo> ratingStarsRenderer = new RatingStarsRenderer<SimplePojo>(SimplePojo::setStars,5);
			ratingStarsRenderer.addItemEditListener(event -> {
				Notification.show("Column " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());
				SimplePojo pojo = (SimplePojo) event.getItem();
			});
			grid.addColumn(SimplePojo::getStars, ratingStarsRenderer).setCaption("Rating");
			
			MyStringToIntegerConverter myConverter = new MyStringToIntegerConverter();

			SimpleSelectRenderer<SimplePojo,Integer> choiceFieldRenderer = new SimpleSelectRenderer<>(SimplePojo::setChoice, Arrays.asList(1,2,3,4,5));
			choiceFieldRenderer.setConverter(myConverter);
			choiceFieldRenderer.addItemEditListener(event -> { 
					Notification.show("Property " + event.getColumn().getCaption() + " edited with value " + event.getNewValue().toString());				
				});
			grid.addColumn(SimplePojo::getChoice, choiceFieldRenderer).setCaption("Choice");			

			setSizeFull();
			setStyleName("demoContentLayout");
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

        String uriFragment = Page.getCurrent().getUriFragment();
        String name = request.getParameter("name");
        if (uriFragment != null) Notification.show("Page opened with BrowserOpenerRenderer, URI fragment: "+uriFragment+" Parameter name="+name);
    }
}
