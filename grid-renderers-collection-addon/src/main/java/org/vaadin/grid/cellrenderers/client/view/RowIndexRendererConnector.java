package org.vaadin.grid.cellrenderers.client.view;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.ui.Connect;

/**
 * @author Tatu Lund - Vaadin
 */
@Connect(org.vaadin.grid.cellrenderers.view.RowIndexRenderer.class)
public class RowIndexRendererConnector extends
        AbstractRendererConnector<String> {

    private static LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<>();

	public RowIndexRendererConnector() {
		super();
	    roman_numerals.put("M", 1000);
	    roman_numerals.put("CM", 900);
	    roman_numerals.put("D", 500);
	    roman_numerals.put("CD", 400);
	    roman_numerals.put("C", 100);
	    roman_numerals.put("XC", 90);
	    roman_numerals.put("L", 50);
	    roman_numerals.put("XL", 40);
	    roman_numerals.put("X", 10);
	    roman_numerals.put("IX", 9);
	    roman_numerals.put("V", 5);
	    roman_numerals.put("IV", 4);
	    roman_numerals.put("I", 1);		
	}
	
	public static String repeat(String s, int n) {
		if(s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
	  
	private static String roman(int number) {
	    String result = "";
	    for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
	      int matches = number/entry.getValue();
	      result += repeat(entry.getKey(), matches);
	      number = number % entry.getValue();
	    }
	    return result;
	}
	
	// Helper method to create ordinal String
	private static String ordinal(int i) {
	    int mod100 = i % 100;
	    int mod10 = i % 10;
	    if (mod10 == 1 && mod100 != 11) {
	        return i + "st";
	    } else if(mod10 == 2 && mod100 != 12) {
	        return i + "nd";
	    } else if(mod10 == 3 && mod100 != 13) {
	        return i + "rd";
	    } else {
	        return i + "th";
	    }
	}

    public class RowIndexClientRenderer implements Renderer<String> {

        @Override
        public void render(RendererCellReference cell, String htmlString) {
        	int rowIndex = cell.getRowIndex()+getState().offset;
        	String content = null;
        	if (getState().rowIndexMode == RowIndexMode.ORDINAL) {
        		content = ordinal(rowIndex);
        	} else if (getState().rowIndexMode == RowIndexMode.ROMAN) {
        		content = roman(rowIndex);        		
        	} else {
        		content = ""+rowIndex;
        	}
            cell.getElement()
                    .setInnerSafeHtml(SafeHtmlUtils.fromSafeConstant(content));
        }        

    }
    
    @Override
    protected RowIndexClientRenderer createRenderer() {
        return new RowIndexClientRenderer();
    }

    @Override
    public RowIndexClientRenderer getRenderer() {
        return (RowIndexClientRenderer) super.getRenderer();
    }

    @Override
    public RowIndexRendererState getState() {
    	return (RowIndexRendererState) super.getState();
    }    
 }
