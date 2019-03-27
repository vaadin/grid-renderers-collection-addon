package org.vaadin.grid.cellrenderers.client.editable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import java.util.List;

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

    public class SimpleSelectClientRenderer extends ClickableRenderer<String, ListBox> {

        private static final String ROW_KEY_PROPERTY = "rowKey";
        private static final String COLUMN_ID_PROPERTY = "columnId";

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

            if (e.getPropertyString(COLUMN_ID_PROPERTY) != getColumnId(getGrid()
                    .getColumn(cell.getColumnIndex()))) {
                e.setPropertyString(COLUMN_ID_PROPERTY, getColumnId(getGrid()
                        .getColumn(cell.getColumnIndex())));
            }

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

            listBox.setEnabled(!getState().readOnly && cell.getColumn().isEditable() && getGrid().isEnabled());
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

            listBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                    ListBox listBox = (ListBox) changeEvent.getSource();
                    Element e = listBox.getElement();
                    rpc.onChange(e.getPropertyString(ROW_KEY_PROPERTY),
                            e.getPropertyString(COLUMN_ID_PROPERTY),
                            listBox.getSelectedValue());
                }
            });

            listBox.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    event.stopPropagation();
                }
            });

            listBox.addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
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
    protected Renderer<String> createRenderer() {
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
        return ((GridConnector) getParent()).getWidget();
    }

}