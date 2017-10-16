package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.dom.client.Style;
import com.vaadin.client.VConsole;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.dom.client.BrowserEvents; 
import com.google.gwt.dom.client.Element; 
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.communication.RpcProxy; 
import com.vaadin.client.connectors.ClickableRendererConnector; 
import com.vaadin.client.connectors.grid.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer; 
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler; 
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.widget.grid.RendererCellReference; 
import com.vaadin.client.widgets.Grid; 
import com.vaadin.shared.ui.Connect; 

import elemental.json.JsonObject;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(TextFieldRenderer.class)
public class TextFieldRendererConnector extends ClickableRendererConnector<String>  {
    TextFieldRendererServerRpc rpc = RpcProxy.create(
            TextFieldRendererServerRpc.class, this);

    public class TextFieldClientRenderer extends ClickableRenderer<String, VTextField> {

        private static final String ROW_KEY_PROPERTY = "rowKey";

        private boolean doesTextFieldContainValue(VTextField textField, String value) {
            if(textField.getValue().equals(value)) {
            	return true;
            }
            return false;
        }

        @Override
        public void render(RendererCellReference cell, String selectedValue,
                           VTextField textField) {

        	getState().value = selectedValue;
            textField.setValue(selectedValue);
            
        	Element e = textField.getElement();
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.

            textField.setEnabled(!getState().readOnly);
        }

        @Override
        public VTextField createWidget() {
            final VTextField textField = GWT.create(VTextField.class);

            if(getState().fitToCell) {
                textField.setWidth("100%");

                textField.getElement().getStyle().setPosition(Style.Position.RELATIVE);
                textField.getElement().getStyle().setProperty("border-radius", "0");
                textField.getElement().getStyle().setProperty("padding", "0 16px");
                textField.getElement().getStyle().setLeft(-16.0, Style.Unit.PX);
                textField.getElement().getStyle().setProperty("border", "none");
                textField.getElement().getStyle().setTop(-1, Style.Unit.PX);
            }

            textField.sinkBitlessEvent(BrowserEvents.CLICK);
            textField.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);

            textField.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                    VTextField textField = (VTextField) changeEvent.getSource();
                    Element e = textField.getElement();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
                            textField.getValue());
                }
            });

            textField.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    event.stopPropagation();
                }
            });

            textField.addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
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
    protected TextFieldClientRenderer createRenderer() {
        return new TextFieldClientRenderer();
    }

    @Override
    public TextFieldClientRenderer getRenderer() {
        return (TextFieldClientRenderer) super.getRenderer();
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
