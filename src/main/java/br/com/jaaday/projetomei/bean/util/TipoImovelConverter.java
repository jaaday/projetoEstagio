/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean.util;

import br.com.jaaday.projetomei.dao.TipoImovelDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.TipoImovel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "tipoImovel", forClass = TipoImovel.class)
public class TipoImovelConverter implements Converter {

    private TipoImovel tipoImovel = new TipoImovel();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        TipoImovelDAO dao = new TipoImovelDAO(JPAUtil.EMF);
        return dao.findTipoImovel(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        tipoImovel = (TipoImovel) value;
        return String.valueOf(tipoImovel.getId());
    }
    
}
