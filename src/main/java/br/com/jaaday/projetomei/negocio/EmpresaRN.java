/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.dao.EmpresaDAO;
import br.com.jaaday.projetomei.dao.util.JPAUtil;
import br.com.jaaday.projetomei.modelo.Empresa;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class EmpresaRN implements Serializable{
    private EmpresaDAO dao;
    private Empresa empresa;
    
    public EmpresaRN(){
        dao = new EmpresaDAO((JPAUtil.EMF));
        empresa = new Empresa();
    }
    
    public void inserir(){
        try {
            dao.create(this.empresa);
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
            dao.edit(this.empresa);
        } catch (Exception e) {
        }
    }
    
    public Empresa buscar(Integer id){
        try {
            return dao.findEmpresa(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Empresa> buscarTodos(){
        try {
            return dao.findEmpresaEntities();
        } catch (Exception e) {
            return null;
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
}
