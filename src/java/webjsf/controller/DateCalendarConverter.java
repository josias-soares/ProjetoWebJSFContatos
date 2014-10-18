/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;

/**
 *
 * @author josias
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "dateCalendarConverter")
public class DateCalendarConverter implements Converter {

    private static final DateFormat format = DateFormat.getDateInstance();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            Calendar date = Calendar.getInstance();
            try {
                date.setTime(format.parse(value));
                
                return date;
            } catch (ParseException ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null) {
            Calendar date = (Calendar) value;

            return format.format(date.getTime());
        }

        return null;
    }

}
