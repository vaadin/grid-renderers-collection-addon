package org.vaadin.grid.cellrenderers.client.editable;

import org.vaadin.grid.cellrenderers.client.editable.common.CellId;
import org.vaadin.grid.cellrenderers.client.editable.common.EditableRendererClientUtil;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.grid.renderers.RendererClickRpc;

import elemental.json.JsonObject;

@Connect(TextFieldRenderer.class)
public class TextFieldRendererConnector extends ClickableRendererConnector<String> {
	TextFieldRendererServerRpc rpc = RpcProxy.create(TextFieldRendererServerRpc.class, this);

	public class TextFieldClientRenderer extends ClickableRenderer<String, VTextField> {

		@Override
		public void render(RendererCellReference cell, String selectedValue, VTextField textField) {

			Element e = textField.getElement();

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

			textField.setValue(selectedValue);

			if (!cell.getColumn()
				.isEditable()
					|| !cell.getGrid()
						.isEnabled()) {
				textField.setEnabled(false);
				return;
			}

			TextFieldRendererConnector.this.rpc
				.onRender(new CellId(e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
						e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY)));
		}

		@Override
		public VTextField createWidget() {
			onUnregister();

			final VTextField textField = GWT.create(VTextField.class);

			if (getState().maxLength > 0) {
				textField.setMaxLength(getState().maxLength);
			}

			if (getState().fitToCell) {
				textField.setWidth("100%");

				textField.getElement()
					.getStyle()
					.setPosition(Style.Position.RELATIVE);
				textField.getElement()
					.getStyle()
					.setProperty("border-radius", "0");
				textField.getElement()
					.getStyle()
					.setProperty("padding", "0 16px");
				textField.getElement()
					.getStyle()
					.setLeft(-16.0, Style.Unit.PX);
				textField.getElement()
					.getStyle()
					.setProperty("border", "none");
				textField.getElement()
					.getStyle()
					.setTop(-1, Style.Unit.PX);
			}

			textField.addFocusHandler(new FocusHandler() {

				@Override
				public void onFocus(FocusEvent event) {
					VTextField textField = (VTextField) event.getSource();
					textField.selectAll();
				}
			});

			textField.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent changeEvent) {
					VTextField textField = (VTextField) changeEvent.getSource();
					Element e = textField.getElement();
					TextFieldRendererConnector.this.rpc.onChange(
																	e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
																	e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY),
																	textField.getValue());
				}
			});

			textField.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					VTextField textField = (VTextField) event.getSource();
					Element e = textField.getElement();
					getRpcProxy(RendererClickRpc.class)
						.click(	e.getPropertyString(EditableRendererClientUtil.ROW_KEY_PROPERTY),
								e.getPropertyString(EditableRendererClientUtil.COLUMN_ID_PROPERTY),
								MouseEventDetailsBuilder.buildMouseEventDetails(event.getNativeEvent()));
				}
			});

			textField.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					event.stopPropagation();
				}
			});

			registerRpc(DateFieldRendererClientRpc.class, new DateFieldRendererClientRpc() {

				@Override
				public void setEnabled(boolean enabled, CellId id) {
					if (id.equals(EditableRendererClientUtil.getCellId(textField))) {
						textField.setEnabled(enabled);
					}
				}

			});

			return textField;
		}
	}

	@Override
	public TextFieldRendererState getState() {
		return (TextFieldRendererState) super.getState();
	}

	@Override
	protected Renderer<String> createRenderer() {
		return new TextFieldClientRenderer();
	}

	@Override
	public TextFieldClientRenderer getRenderer() {
		return (TextFieldClientRenderer) super.getRenderer();
	}

	@Override
	protected HandlerRegistration addClickHandler(RendererClickHandler<JsonObject> handler) {
		return getRenderer().addClickHandler(handler);
	}

}
