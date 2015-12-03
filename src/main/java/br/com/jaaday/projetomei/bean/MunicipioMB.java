/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean;

import br.com.jaaday.projetomei.modelo.Municipio;
import br.com.jaaday.projetomei.negocio.MunicipioRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class MunicipioMB {

    /**
     * Creates a new instance of MunicipioMB
     */
    private MunicipioRN negocio;
    
    public MunicipioMB() {
        negocio = new MunicipioRN();
    }
    
    public List<Municipio> getMunicipios(){
        return negocio.buscarTodos();
    }

    public MunicipioRN getNegocio() {
        return negocio;
    }

    public void setNegocio(MunicipioRN negocio) {
        this.negocio = negocio;
    }
    
}
