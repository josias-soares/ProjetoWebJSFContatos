/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import webjsf.model.Telefone;

/**
 *
 * @author josias
 */
@FacesConverter(forClass = Telefone.Tipo.class, value = "tipoTelefone")
public class TipoTelefoneConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String id) {
        if (id != null) {
            return Telefone.Tipo.tipoPorId(id);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object tipo) {
        if (tipo != null) {
            return ((Telefone.Tipo) tipo).getId();
        }

        return null;
    }

}
