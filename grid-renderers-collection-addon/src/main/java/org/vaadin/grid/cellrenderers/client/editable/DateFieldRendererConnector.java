package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.google.gwt.dom.client.Style;

import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.dom.client.BrowserEvents; 
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;
import com.vaadin.client.LocaleService;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector; 
import com.vaadin.client.connectors.grid.ColumnConnector;
import com.vaadin.client.connectors.grid.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer; 
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler; 
import com.vaadin.client.ui.VOverlay;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid; 
import com.vaadin.shared.ui.Connect; 
import com.vaadin.shared.ui.datefield.DateResolution;

import elemental.json.JsonObject;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(DateFieldRenderer.class)
public class DateFieldRendererConnector extends ClickableRendererConnector<Date> {
    DateFieldRendererServerRpc rpc = RpcProxy.create(
            DateFieldRendererServerRpc.class, this);

    public class DateFieldClientRenderer extends ClickableRenderer<Date, VMyPopupCalendar> {

        private static final String ROW_KEY_PROPERTY = "rowKey";
        private Date value = null;
        
        private boolean doesTextFieldContainValue(VMyPopupCalendar dateField, Date value) {
            if(dateField.getDate().equals(value)) {
            	return true;
            }
            return false;
        }

        @Override
        public void render(RendererCellReference cell, Date selectedValue,
                           VMyPopupCalendar dateField) {

            Element e = dateField.getElement();

            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.

            // Setting and showing the date from the Grid
            dateField.setCurrentDate(selectedValue);
            dateField.buildDate();
            
            dateField.setEnabled(!getState().readOnly && getGrid().isEnabled());
			if (getState().hasIsEnabledProvider) rpc.applyIsEnabledCheck(e.getPropertyString(ROW_KEY_PROPERTY));
        }

        @Override
        public VMyPopupCalendar createWidget() {
            VMyPopupCalendar dateField = GWT.create(VMyPopupCalendar.class);

            dateField.setWidth("100%");

            dateField.getElement().getStyle().setProperty("border-radius", "0");
            dateField.getElement().getStyle().setTop(-1, Style.Unit.PX);

            dateField.sinkBitlessEvent(BrowserEvents.CHANGE);
            dateField.sinkBitlessEvent(BrowserEvents.CLICK);
            dateField.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);
            // Configuring the popup calendar panel for Day resolution
            // This is done in similar fashion than the regular connector does it
            dateField.setCurrentResolution(getState().dateResolution);
            dateField.calendar.setDateTimeService(dateField.getDateTimeService());
            dateField.calendar.setShowISOWeekNumbers(dateField
                    .isShowISOWeekNumbers());
            if (dateField.calendar.getResolution() != dateField
                    .getCurrentResolution()) {
                boolean hasSelectedDate = false;
                dateField.calendar.setResolution(dateField
                        .getCurrentResolution());
                if (dateField.calendar.getDate() != null
                        && dateField.getCurrentDate() != null) {
                    hasSelectedDate = true;
                    dateField.calendar.setDate((Date) dateField
                            .getCurrentDate().clone());
                }
                // force re-render when changing resolution only
                dateField.calendar.renderCalendar(hasSelectedDate);
            }
            dateField.setTextFieldTabIndex();

            // Re-setting connector so that popup inherits correct theme / styles from Grid 
            ColumnConnector columnConnector = (ColumnConnector) getParent();
            GridConnector gridConnector = columnConnector.getParent();
            dateField.popup.setOwner(gridConnector.getWidget());

            // Set application connection
            dateField.client = getConnection();   

            // Set date locale
            String locale = LocaleService.getDefaultLocale();
            dateField.setCurrentLocale(locale);

            // Add change handler (for textual date input)
            dateField.addDomHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                    Element e = dateField.getElement();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                            dateField.getDate());
                }
            }, ChangeEvent.getType());

            dateField.addDomHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    event.stopPropagation();
                }
            }, ClickEvent.getType());

            dateField.text.addFocusHandler(event -> {
            	value = dateField.getDate();
            });
            
            dateField.text.addDomHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
                	if (getState().blurChangeMode) {
                		Date newValue = dateField.getDate();
                		if (value != null && !value.equals(newValue)) {
                			Element e = dateField.getElement();
                			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                					newValue);
                			value = newValue;
                		}
                	}
				}            	
            }, BlurEvent.getType());
            
            dateField.addDomHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
                }
            }, MouseDownEvent.getType() );

            // Add close handler to popup calendar panel
            // This is needed to get value change when user selects date from popup
            // Note: Popup doesn't update currentDate automatically
            dateField.popup.addCloseHandler(new CloseHandler<PopupPanel>() {
            	@Override
                public void onClose(CloseEvent<PopupPanel> closeEvent) {
                    Element e = dateField.getElement();
                    Date date = dateField.calendar.getDate();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),date);
                }
            });
            
			registerRpc(DateFieldRendererClientRpc.class,
					new DateFieldRendererClientRpc() {
						@Override
						public void setEnabled(boolean enabled, String rowKey) {
	                		Element e = dateField.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								dateField.setEnabled(enabled);
							}
						}
			});
            
            return dateField;
        }
    }
    
    @Override
    public DateFieldRendererState getState() {
    	return (DateFieldRendererState) super.getState();
    }
    
    @Override
    protected DateFieldClientRenderer createRenderer() {
        return new DateFieldClientRenderer();
    }

    @Override
    public DateFieldClientRenderer getRenderer() {
        return (DateFieldClientRenderer) super.getRenderer();
    }

    @Override
    protected HandlerRegistration addClickHandler(
            RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }

    private Grid<JsonObject> getGrid() {
    	ColumnConnector column = (ColumnConnector) getParent();
        return column.getParent().getWidget();
    }

}
