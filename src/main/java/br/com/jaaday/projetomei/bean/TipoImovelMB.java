/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean;

import br.com.jaaday.projetomei.modelo.TipoImovel;
import br.com.jaaday.projetomei.negocio.TipoImovelRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TipoImovelMB {

    /**
     * Creates a new instance of TipoImovelMB
     */
    private TipoImovelRN negocio;
    
    public TipoImovelMB() {
        negocio = new TipoImovelRN();
    }
    
    public List<TipoImovel> getTipoImoveis(){
        return negocio.buscarTodos();
    }

    public TipoImovelRN getNegocio() {
        return negocio;
    }

    public void setNegocio(TipoImovelRN negocio) {
        this.negocio = negocio;
    }
    
}
