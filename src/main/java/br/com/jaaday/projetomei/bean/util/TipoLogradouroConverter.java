/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean.util;

import br.com.jaaday.projetomei.dao.TipoLogradouroDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.TipoLogradouro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "tipoLogradouro", forClass = TipoLogradouro.class)
public class TipoLogradouroConverter implements Converter {

    private TipoLogradouro tipoLogradouro = new TipoLogradouro();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        TipoLogradouroDAO dao = new TipoLogradouroDAO(JPAUtil.EMF);
        return dao.findTipoLogradouro(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        tipoLogradouro = (TipoLogradouro) value;
        return String.valueOf(tipoLogradouro.getId());
    }
    
}
