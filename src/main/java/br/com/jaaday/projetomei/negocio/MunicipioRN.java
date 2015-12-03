/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.dao.MunicipioDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.Municipio;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class MunicipioRN implements Serializable{
    private MunicipioDAO dao;
    private Municipio municipio;
    
    public MunicipioRN(){
        dao = new MunicipioDAO((JPAUtil.EMF));
        municipio = new Municipio();
    }
    
    public void inserir(){
        try {
            dao.create(this.municipio);
        } catch (Exception e) {
        }
    }
    
    public void deletar(Integer id){
        try {
            dao.destroy(id);
        } catch (Exception e) {
        }
    }
    
    public void editar(){
        try {
            dao.edit(this.municipio);
        } catch (Exception e) {
        }
    }
    
    public Municipio buscar(Integer id){
        try {
            return dao.findMunicipio(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Municipio> buscarTodos(){
        try {
            return dao.findMunicipioEntities();
        } catch (Exception e) {
            return null;
        }
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
