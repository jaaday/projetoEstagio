/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.bean;

import br.com.jaaday.projetomei.negocio.EmpresaRN;
import java.io.Serializable;

/**
 *
 * @author sephi_000
 */
public class EmpresaMB implements Serializable{

    /**
     * Creates a new instance of EmpresaMB
     */
    private EmpresaRN negocio;
    
    public EmpresaMB() {
        negocio = new EmpresaRN();
    }

    public String primeiroPasso(){
        return "PrimeiroPasso";
    }
    
    public String segundoPasso(){
        return "SegundoPasso";
    }
    
    public String terceiroPasso(){
        return "TerceiroPasso";
    }
    
    public String quartoPasso(){
        return "QuartoPasso";
    }

    public EmpresaRN getNegocio() {
        return negocio;
    }

    public void setNegocio(EmpresaRN negocio) {
        this.negocio = negocio;
    }
    
}
