package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.google.gwt.dom.client.Style;
import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.dom.client.BrowserEvents; 
import com.google.gwt.dom.client.Element; 
import com.google.gwt.event.dom.client.*; 
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.HandlerRegistration; 
import com.vaadin.client.LocaleService;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector; 
import com.vaadin.client.connectors.GridConnector; 
import com.vaadin.client.renderers.ClickableRenderer; 
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler; 
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.ui.VOverlay;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid; 
import com.vaadin.shared.ui.Connect; 
import com.vaadin.shared.ui.datefield.Resolution;

import elemental.json.JsonObject;

@Connect(DateFieldRenderer.class)
public class DateFieldRendererConnector extends ClickableRendererConnector<Date> {
    DateFieldRendererServerRpc rpc = RpcProxy.create(
            DateFieldRendererServerRpc.class, this);

    public class DateFieldClientRenderer extends ClickableRenderer<Date, VMyPopupCalendar> {

        private static final String ROW_KEY_PROPERTY = "rowKey";
        private static final String COLUMN_ID_PROPERTY = "columnId";

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

            getState().value = selectedValue;
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.

            if(e.getPropertyString(COLUMN_ID_PROPERTY) != getColumnId(getGrid()
                    .getColumn(cell.getColumnIndex()))) {
                e.setPropertyString(COLUMN_ID_PROPERTY, getColumnId(getGrid()
                        .getColumn(cell.getColumnIndex())));
            }
            
            // Setting and showing the date from the Grid
            dateField.setCurrentDate(selectedValue);
            dateField.buildDate();
            
            if(dateField.isEnabled() != cell.getColumn().isEditable()) {
                dateField.setEnabled(cell.getColumn().isEditable());
            }
        }

        @Override
        public VMyPopupCalendar createWidget() {
            final VMyPopupCalendar dateField = GWT.create(VMyPopupCalendar.class);

            dateField.setWidth("100%");

            dateField.getElement().getStyle().setProperty("border-radius", "0");
            dateField.getElement().getStyle().setTop(-1, Style.Unit.PX);

            dateField.sinkBitlessEvent(BrowserEvents.CHANGE);
            dateField.sinkBitlessEvent(BrowserEvents.CLICK);
            dateField.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);

            // Configuring the popup calendar panel for Day resolution
            // This is done in similar fashion than the regular connector does it
            dateField.setCurrentResolution(Resolution.DAY);
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
            GridConnector gridConnector = (GridConnector) getParent();
            dateField.popup.setOwner(gridConnector.getWidget());

            // Set application connection
            dateField.client = getConnection();   
            dateField.paintableId = getConnectorId();

            LocaleService lservice = new LocaleService();
            
            // Set date locale
            String locale = lservice.getDefaultLocale();
            dateField.setCurrentLocale(locale);

            // Add change handler (for textual date input)
            dateField.addDomHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
//                    VMyPopupCalendar dateField = (VMyPopupCalendar) changeEvent.getSource();
                    Element e = dateField.getElement();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                            e.getPropertyString(COLUMN_ID_PROPERTY),
                            dateField.getDate());
                }
            }, ChangeEvent.getType());

            dateField.addDomHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    event.stopPropagation();
                }
            }, ClickEvent.getType());

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
                    VOverlay popup = (VOverlay) closeEvent.getSource();
                    Element e = dateField.getElement();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                            e.getPropertyString(COLUMN_ID_PROPERTY),
                            dateField.calendar.getDate());
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
    protected Renderer<Date> createRenderer() {
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
        return ((GridConnector) getParent()).getWidget();
    }

}
