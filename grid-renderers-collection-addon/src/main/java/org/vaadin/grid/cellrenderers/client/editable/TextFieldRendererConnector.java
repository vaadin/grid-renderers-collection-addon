package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.dom.client.Style;

import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.dom.client.BrowserEvents; 
import com.google.gwt.dom.client.Element; 
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.communication.RpcProxy; 
import com.vaadin.client.connectors.ClickableRendererConnector; 
import com.vaadin.client.connectors.grid.ColumnConnector;
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
        private String value = null;
        
        private boolean doesTextFieldContainValue(VTextField textField, String value) {
            if(textField.getValue().equals(value)) {
            	return true;
            }
            return false;
        }

        @Override
        public void render(RendererCellReference cell, String selectedValue,
                           VTextField textField) {

            textField.setValue(selectedValue);
            
        	Element e = textField.getElement();
            
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.

            textField.setReadOnly(!getGrid().isEnabled() || getState().readOnly);
			if (getState().hasIsEnabledProvider) rpc.applyIsEnabledCheck(e.getPropertyString(ROW_KEY_PROPERTY));
            
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

            textField.addChangeHandler(changeEvent -> {
            	VTextField field = (VTextField) changeEvent.getSource();
        		String newValue = field.getValue();
        		if (value != null && !value.equals(newValue)) {
        			Element e = field.getElement();
        			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
        					newValue);
        			value = newValue;
        		}
            });

            textField.addFocusHandler(event -> {
        		VTextField field = (VTextField) event.getSource();
            	value = field.getValue();
            });
            
            textField.addBlurHandler(event -> { 
            	if (getState().blurChangeMode) {
            		VTextField field = (VTextField) event.getSource();
            		String newValue = field.getValue();
            		if (value != null && !value.equals(newValue)) {
            			Element e = field.getElement();
            			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
            					newValue);
            			value = newValue;
            		}
            	}            	            	
            });
            
            textField.addKeyUpHandler(event -> {
            	if (getState().eagerChangeMode) {
            		VTextField field = (VTextField) event.getSource();
            		String newValue = field.getValue();
            		if (value != null && !value.equals(newValue)) {
            			Element e = field.getElement();
            			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
            					newValue);
            			value = newValue;
            		}
            	}            	
            });
            
            textField.addClickHandler(event -> {
                event.stopPropagation();
            });

            textField.addMouseDownHandler(event -> {
                event.stopPropagation();
            });

			registerRpc(TextFieldRendererClientRpc.class,
					new TextFieldRendererClientRpc() {
						@Override
						public void setEnabled(boolean enabled, String rowKey) {
	                		Element e = textField.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								textField.setReadOnly(!getGrid().isEnabled() || !enabled);
							}
						}

						@Override
						public void switchEnabled(String rowKey) {
	                		Element e = textField.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								textField.setReadOnly(!getGrid().isEnabled() || !textField.isReadOnly());
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
    	ColumnConnector column = (ColumnConnector) getParent();
        return column.getParent().getWidget();
    }

}
