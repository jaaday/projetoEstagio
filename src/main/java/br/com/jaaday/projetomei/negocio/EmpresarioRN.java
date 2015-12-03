/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.dao.EmpresarioDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.Empresario;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class EmpresarioRN {
    private EmpresarioDAO dao;
    private Empresario empresario;
    
    public EmpresarioRN(){
        dao = new EmpresarioDAO((JPAUtil.EMF));
        empresario = new Empresario();
    }
    
    public void inserir(){
        try {
            dao.create(this.empresario);
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
            dao.edit(this.empresario);
        } catch (Exception e) {
        }
    }
    
    public Empresario buscar(Integer id){
        try {
            return dao.findEmpresario(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Empresario> buscarTodos(){
        try {
            return dao.findEmpresarioEntities();
        } catch (Exception e) {
            return null;
        }
    }

    public Empresario getEmpresario() {
        return empresario;
    }

    public void setEmpresario(Empresario empresario) {
        this.empresario = empresario;
    }
}
