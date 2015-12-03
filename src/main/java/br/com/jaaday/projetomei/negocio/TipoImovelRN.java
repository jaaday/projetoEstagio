/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.dao.TipoImovelDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.TipoImovel;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TipoImovelRN implements Serializable{
    private TipoImovelDAO dao;
    private TipoImovel tipoImovel;
    
    public TipoImovelRN(){
        dao = new TipoImovelDAO((JPAUtil.EMF));
        tipoImovel = new TipoImovel();
    }
    
    public void inserir(){
        try {
            dao.create(this.tipoImovel);
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
            dao.edit(this.tipoImovel);
        } catch (Exception e) {
        }
    }
    
    public TipoImovel buscar(Integer id){
        try {
            return dao.findTipoImovel(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<TipoImovel> buscarTodos(){
        try {
            return dao.findTipoImovelEntities();
        } catch (Exception e) {
            return null;
        }
    }

    public TipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }
}
