/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.dao.TipoLogradouroDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.TipoLogradouro;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TipoLogradouroRN implements Serializable{
    private TipoLogradouroDAO dao;
    private TipoLogradouro tipoLogradouro;
    
    public TipoLogradouroRN(){
        dao = new TipoLogradouroDAO((JPAUtil.EMF));
        tipoLogradouro = new TipoLogradouro();
    }
    
    public void inserir(){
        try {
            dao.create(this.tipoLogradouro);
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
            dao.edit(this.tipoLogradouro);
        } catch (Exception e) {
        }
    }
    
    public TipoLogradouro buscar(Integer id){
        try {
            return dao.findTipoLogradouro(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<TipoLogradouro> buscarTodos(){
        try {
            return dao.findTipoLogradouroEntities();
        } catch (Exception e) {
            return null;
        }
    }

    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }
}
