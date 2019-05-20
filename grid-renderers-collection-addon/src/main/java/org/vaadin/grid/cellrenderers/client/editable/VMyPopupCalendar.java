package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.vaadin.client.VConsole;
import com.vaadin.client.ui.VPopupCalendar;

/**
 * 
 * Purpose of this class is to get rid of extra baggage of updateValue and checkGroupFocus 
 * which is not needed in renderer use
 * 
 * @author Tatu Lund - Vaadin
 */
public class VMyPopupCalendar extends VPopupCalendar {

	private static final String PARSE_ERROR_CLASSNAME = "-parseerror";
    private boolean groupFocus;

	@Override
	public void updateValue(Date newDate) {
		setCurrentDate(newDate);
	}

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == calendarToggle && isEnabled()) {
            openCalendarPanel();
        }
    }

    @Override
    protected void checkGroupFocus(boolean textFocus) {
        boolean newGroupFocus = textFocus | hasChildFocus();
        if (getClient() != null                
                && groupFocus != newGroupFocus) {

            groupFocus = newGroupFocus;
        }
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void onChange(ChangeEvent event) {
    	if (!text.getText().equals("")) {
        	try {
        		String enteredDate = text.getText();

            	setDate(getDateTimeService().parseDate(enteredDate,
                    getFormatString(), lenient));

            	if (lenient) {
            		// If date value was leniently parsed, normalize text
            		// presentation.
            		// FIXME: Add a description/example here of when this is
            		// needed
            		text.setValue(
                        getDateTimeService().formatDate(getDate(),
                                getFormatString()), false);
            	}

            	// remove possibly added invalid value indication
            	removeStyleName(getStylePrimaryName() + PARSE_ERROR_CLASSNAME);
        	} catch (final Exception e) {
            	VConsole.log(e);

            	addStyleName(getStylePrimaryName() + PARSE_ERROR_CLASSNAME);
            	setDate(null);
        	}
    	} else {
        	setDate(null);
        	// remove possibly added invalid value indication
        	removeStyleName(getStylePrimaryName() + PARSE_ERROR_CLASSNAME);
    	}
    }

}
