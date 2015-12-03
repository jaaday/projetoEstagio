/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean.util;

import br.com.jaaday.projetomei.dao.MunicipioDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.Municipio;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "municipio", forClass = Municipio.class)
public class MunicipioConverter implements Converter {

    private Municipio municipio = new Municipio();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        MunicipioDAO dao = new MunicipioDAO(JPAUtil.EMF);
        return dao.findMunicipio(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        municipio = (Municipio) value;
        return String.valueOf(municipio.getId());
    }
    
}
