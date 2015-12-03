/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "atividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividades.findAll", query = "SELECT a FROM Atividades a"),
    @NamedQuery(name = "Atividades.findByCnaeId", query = "SELECT a FROM Atividades a WHERE a.atividadesPK.cnaeId = :cnaeId"),
    @NamedQuery(name = "Atividades.findByEmpresaId", query = "SELECT a FROM Atividades a WHERE a.atividadesPK.empresaId = :empresaId"),
    @NamedQuery(name = "Atividades.findByPrincipal", query = "SELECT a FROM Atividades a WHERE a.principal = :principal"),
    @NamedQuery(name = "Atividades.findByAtividadeEndereco", query = "SELECT a FROM Atividades a WHERE a.atividadeEndereco = :atividadeEndereco")})
public class Atividades implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AtividadesPK atividadesPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "principal")
    private boolean principal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "atividade_endereco")
    private boolean atividadeEndereco;
    @JoinColumn(name = "cnae_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cnae cnae;
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa;

    public Atividades() {
    }

    public Atividades(AtividadesPK atividadesPK) {
        this.atividadesPK = atividadesPK;
    }

    public Atividades(AtividadesPK atividadesPK, boolean principal, boolean atividadeEndereco) {
        this.atividadesPK = atividadesPK;
        this.principal = principal;
        this.atividadeEndereco = atividadeEndereco;
    }

    public Atividades(int cnaeId, int empresaId) {
        this.atividadesPK = new AtividadesPK(cnaeId, empresaId);
    }

    public AtividadesPK getAtividadesPK() {
        return atividadesPK;
    }

    public void setAtividadesPK(AtividadesPK atividadesPK) {
        this.atividadesPK = atividadesPK;
    }

    public boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public boolean getAtividadeEndereco() {
        return atividadeEndereco;
    }

    public void setAtividadeEndereco(boolean atividadeEndereco) {
        this.atividadeEndereco = atividadeEndereco;
    }

    public Cnae getCnae() {
        return cnae;
    }

    public void setCnae(Cnae cnae) {
        this.cnae = cnae;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atividadesPK != null ? atividadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividades)) {
            return false;
        }
        Atividades other = (Atividades) object;
        if ((this.atividadesPK == null && other.atividadesPK != null) || (this.atividadesPK != null && !this.atividadesPK.equals(other.atividadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.Atividades[ atividadesPK=" + atividadesPK + " ]";
    }
    
}
