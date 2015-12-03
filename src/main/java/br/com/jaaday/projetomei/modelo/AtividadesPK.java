/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sephi_000
 */
@Embeddable
public class AtividadesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cnae_id")
    private int cnaeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "empresa_id")
    private int empresaId;

    public AtividadesPK() {
    }

    public AtividadesPK(int cnaeId, int empresaId) {
        this.cnaeId = cnaeId;
        this.empresaId = empresaId;
    }

    public int getCnaeId() {
        return cnaeId;
    }

    public void setCnaeId(int cnaeId) {
        this.cnaeId = cnaeId;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cnaeId;
        hash += (int) empresaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtividadesPK)) {
            return false;
        }
        AtividadesPK other = (AtividadesPK) object;
        if (this.cnaeId != other.cnaeId) {
            return false;
        }
        if (this.empresaId != other.empresaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.AtividadesPK[ cnaeId=" + cnaeId + ", empresaId=" + empresaId + " ]";
    }
    
}
