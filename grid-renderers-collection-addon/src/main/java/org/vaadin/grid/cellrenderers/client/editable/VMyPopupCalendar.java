package org.vaadin.grid.cellrenderers.client.editable;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.vaadin.client.VConsole;
import com.vaadin.client.ui.VPopupCalendar;

/*
 * Purpose of this class is to get rid of extra baggage of updateValue which is not needed in renderer usecase
 */
public class VMyPopupCalendar extends VPopupCalendar {

    private static final String PARSE_ERROR_CLASSNAME = "-parseerror";

    @Override
    public void updateValue(final Date newDate) {
        setCurrentDate(newDate);
    }

    @Override
    public void onClick(final ClickEvent event) {
        if (event.getSource() == this.calendarToggle && isEnabled()) {
            openCalendarPanel();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onChange(final ChangeEvent event) {
        if (!this.text.getText()
            .equals("")) {
            try {
                final String enteredDate = this.text.getText();

                setDate(getDateTimeService().parseDate(enteredDate, getFormatString(), this.lenient));

                if (this.lenient) {
                    // If date value was leniently parsed, normalize text
                    // presentation.
                    // FIXME: Add a description/example here of when this is
                    // needed
                    this.text.setValue(getDateTimeService().formatDate(getDate(), getFormatString()), false);
                }

                // remove possibly added invalid value indication
                removeStyleName(getStylePrimaryName() + VMyPopupCalendar.PARSE_ERROR_CLASSNAME);
            }
            catch (final Exception e) {
                VConsole.log(e);

                addStyleName(getStylePrimaryName() + VMyPopupCalendar.PARSE_ERROR_CLASSNAME);
                setDate(null);
            }
        }
        else {
            setDate(null);
            // remove possibly added invalid value indication
            removeStyleName(getStylePrimaryName() + VMyPopupCalendar.PARSE_ERROR_CLASSNAME);
        }

    }

}
