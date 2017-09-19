package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.google.gwt.dom.client.Style;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;
import org.vaadin.grid.cellrenderers.client.editable.common.EditableRendererClientUtil;
import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.LocaleService;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.ui.VOverlay;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.grid.renderers.RendererClickRpc;

import elemental.json.JsonObject;

@Connect(DateFieldRenderer.class)
public class DateFieldRendererConnector extends ClickableRendererConnector<Date> {
	DateFieldRendererServerRpc rpc = RpcProxy.create(DateFieldRendererServerRpc.class, this);

	public class DateFieldClientRenderer extends ClickableRenderer<Date, VMyPopupCalendar> {

		@Override
		public void render(final RendererCellReference cell, final Date selectedValue,
				final VMyPopupCalendar dateField) {
			final Element e = dateField.getElement();

			getState().value = selectedValue;

			if (e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell
				.getRow())) {
				e.setPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY, getRowKey((JsonObject) cell.getRow()));
			}
			// Generics issue, need a correctly typed column.

			if (e
				.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY) != getColumnId(EditableRendererClientUtil
					.getGridFromParent(getParent())
					.getColumn(cell.getColumnIndex()))) {
				e.setPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY,
									getColumnId(EditableRendererClientUtil.getGridFromParent(getParent())
										.getColumn(cell.getColumnIndex())));
			}

			// Setting and showing the date from the Grid
			dateField.setCurrentDate(selectedValue);
			dateField.buildDate();

			if (!cell.getColumn()
				.isEditable()
					|| !cell.getGrid()
						.isEnabled()) {
				dateField.setEnabled(false);
				return;
			}

			DateFieldRendererConnector.this.rpc
				.onRender(new CellId(e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
						e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY)));
		}

		@Override
		public VMyPopupCalendar createWidget() {
			final VMyPopupCalendar dateField = GWT.create(VMyPopupCalendar.class);

			dateField.setWidth("100%");

			dateField.getElement()
				.getStyle()
				.setProperty("border-radius", "0");
			dateField.getElement()
				.getStyle()
				.setTop(-1, Style.Unit.PX);

			dateField.setCurrentResolution(Resolution.valueOf(getState().dateFieldResolutionName));

			dateField.calendar.setDateTimeService(dateField.getDateTimeService());
			dateField.calendar.setShowISOWeekNumbers(dateField.isShowISOWeekNumbers());
			if (dateField.calendar.getResolution() != dateField.getCurrentResolution()) {
				boolean hasSelectedDate = false;
				dateField.calendar.setResolution(dateField.getCurrentResolution());
				if (dateField.calendar.getDate() != null && dateField.getCurrentDate() != null) {
					hasSelectedDate = true;
					dateField.calendar.setDate((Date) dateField.getCurrentDate()
						.clone());
				}
				// force re-render when changing resolution only
				dateField.calendar.renderCalendar(hasSelectedDate);
			}
			dateField.setTextFieldTabIndex();

			// Re-setting connector so that popup inherits correct theme /
			// styles from Grid
			final GridConnector gridConnector = (GridConnector) getParent();
			dateField.popup.setOwner(gridConnector.getWidget());

			// Set application connection
			dateField.client = getConnection();
			dateField.paintableId = getConnectorId();

			final LocaleService lservice = new LocaleService();

			// Set date locale
			final String locale = lservice.getDefaultLocale();
			dateField.setCurrentLocale(locale);

			// Add change handler (for textual date input)
			dateField.addDomHandler(new ChangeHandler() {
				@Override
				public void onChange(final ChangeEvent changeEvent) {
					final Element e = dateField.getElement();
					DateFieldRendererConnector.this.rpc.onChange(
																	e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
																	e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY),
																	dateField.getDate());
				}
			}, ChangeEvent.getType());

			dateField.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					event.stopPropagation();
					final Element e = dateField.getElement();
					getRpcProxy(RendererClickRpc.class)
						.click(	e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
								e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY),
								MouseEventDetailsBuilder.buildMouseEventDetails(event.getNativeEvent()));
				}
			}, ClickEvent.getType());

			dateField.addDomHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(final MouseDownEvent event) {
					event.stopPropagation();
				}
			}, MouseDownEvent.getType());

			// Add close handler to popup calendar panel
			// This is needed to get value change when user selects date from
			// popup
			// Note: Popup doesn't update currentDate automatically
			dateField.popup.addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(final CloseEvent<PopupPanel> closeEvent) {
					final VOverlay popup = (VOverlay) closeEvent.getSource();
					final Element e = dateField.getElement();
					DateFieldRendererConnector.this.rpc.onChange(
																	e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
																	e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY),
																	dateField.calendar.getDate());
				}
			});

			registerRpc(DateFieldRendererClientRpc.class, new DateFieldRendererClientRpc() {

				@Override
				public void setOnRenderSettings(final boolean enabled, final CellId id) {
					if (id.equals(EditableRendererClientUtil.getCellId(dateField))) {
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
	protected Renderer<Date> createRenderer() {
		return new DateFieldClientRenderer();
	}

	@Override
	public DateFieldClientRenderer getRenderer() {
		return (DateFieldClientRenderer) super.getRenderer();
	}

	@Override
	protected HandlerRegistration addClickHandler(final RendererClickHandler<JsonObject> handler) {
		return getRenderer().addClickHandler(handler);
	}

}
