/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean;

import br.com.jaaday.projetomei.modelo.TipoLogradouro;
import br.com.jaaday.projetomei.negocio.TipoLogradouroRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TipoLogradouroMB {

    /**
     * Creates a new instance of TipoLogradouroMB
     */
    private TipoLogradouroRN negocio;
    
    public TipoLogradouroMB() {
        negocio = new TipoLogradouroRN();
    }
    
    public List<TipoLogradouro> getTipoLogradouros(){
        return negocio.buscarTodos();
    }

    public TipoLogradouroRN getNegocio() {
        return negocio;
    }

    public void setNegocio(TipoLogradouroRN negocio) {
        this.negocio = negocio;
    }
    
}
