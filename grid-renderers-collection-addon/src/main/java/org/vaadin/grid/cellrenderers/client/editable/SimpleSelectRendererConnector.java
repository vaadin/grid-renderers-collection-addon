package org.vaadin.grid.cellrenderers.client.editable;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.grid.ColumnConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import org.vaadin.grid.cellrenderers.editable.SimpleSelectRenderer;

import elemental.json.JsonObject;

/**
 * 
 * @author Tatu Lund
 *
 */
@Connect(SimpleSelectRenderer.class)
public class SimpleSelectRendererConnector extends ClickableRendererConnector<String> {

    SimpleSelectRendererServerRpc rpc = RpcProxy.create(
            SimpleSelectRendererServerRpc.class, this);
    private String value = null;
    
    public class SimpleSelectClientRenderer extends ClickableRenderer<String, ListBox> {

        private static final String ROW_KEY_PROPERTY = "rowKey";

        private boolean doesListBoxContainValue(ListBox listBox, String value) {
            for(int i = 0; i < listBox.getItemCount(); i++) {
                if (listBox.getValue(i).equals(value)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void render(RendererCellReference cell, String selectedValue,
                           ListBox listBox) {

            Element e = listBox.getElement();

            if (e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            // Generics issue, need a correctly typed column.


            List<String> dropDownList = getState().dropDownList;
            if (dropDownList.size() != listBox.getItemCount()) {
                listBox.clear();
                int currentIndex = 0;
                for (String dropDownEntry : dropDownList) {
                    listBox.addItem(dropDownEntry);
                    if (dropDownEntry.equals(selectedValue)) {
                        listBox.setSelectedIndex(currentIndex);
                    }

                    currentIndex++;
                }
            } else {           	
                int currentIndex = 0;
                for (String dropDownEntry : dropDownList) {
                    if (dropDownEntry.equals(selectedValue)) {
                        listBox.setSelectedIndex(currentIndex);
                        break;
                    }
                    currentIndex++;
                }
            }

            listBox.setEnabled(getGrid().isEnabled() && !getState().readOnly);
			if (getState().hasIsEnabledProvider) rpc.applyIsEnabledCheck(e.getPropertyString(ROW_KEY_PROPERTY));
        }

        @Override
        public ListBox createWidget() {
            ListBox listBox = GWT.create(ListBox.class);

            listBox.setWidth("100%");
            listBox.setVisibleItemCount(1);
            listBox.setTitle(getState().title);
            listBox.getElement().getStyle().setProperty("border-radius", "0");
            listBox.getElement().getStyle().setTop(-1, Style.Unit.PX);
            
            listBox.sinkBitlessEvent(BrowserEvents.CHANGE);
            listBox.sinkBitlessEvent(BrowserEvents.CLICK);
            listBox.sinkBitlessEvent(BrowserEvents.MOUSEDOWN);

            listBox.addChangeHandler(event -> {
            	ListBox lb = (ListBox) event.getSource();
            	Element e = lb.getElement();
        		String newValue = lb.getSelectedValue();
        		if (value != null && !value.equals(newValue)) {
        			rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),                            
        					newValue);
        			value = newValue;
        		}
            });

            listBox.addClickHandler(event -> {
                event.stopPropagation();
            });

            listBox.addMouseDownHandler(event -> {
                event.stopPropagation();
            });

            listBox.addFocusHandler(event -> {
            	ListBox field = (ListBox) event.getSource();
        		value = field.getSelectedValue();
            });            
            
			registerRpc(SimpleSelectRendererClientRpc.class,
					new SimpleSelectRendererClientRpc() {
						@Override
						public void setEnabled(boolean enabled, String rowKey) {
	                		Element e = listBox.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								listBox.setEnabled(getGrid().isEnabled() && enabled);
							}
						}

						@Override
						public void switchEnabled(String rowKey) {
	                		Element e = listBox.getElement();
							if (rowKey.equals(e.getPropertyString(ROW_KEY_PROPERTY))) {
								listBox.setEnabled(getGrid().isEnabled() && !listBox.isEnabled());
							}
						}
			});
            
            return listBox;
        }
    }

    @Override
    public SimpleSelectRendererState getState() {
        return (SimpleSelectRendererState) super.getState();
    }

    @Override
    protected SimpleSelectClientRenderer createRenderer() {
        return new SimpleSelectClientRenderer();
    }

    @Override
    public SimpleSelectClientRenderer getRenderer() {
        return (SimpleSelectClientRenderer) super.getRenderer();
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