/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "unidade_forma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadeForma.findAll", query = "SELECT u FROM UnidadeForma u"),
    @NamedQuery(name = "UnidadeForma.findById", query = "SELECT u FROM UnidadeForma u WHERE u.id = :id"),
    @NamedQuery(name = "UnidadeForma.findByContem", query = "SELECT u FROM UnidadeForma u WHERE u.contem = :contem")})
public class UnidadeForma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contem")
    private boolean contem;
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresaId;
    @JoinColumn(name = "forma_atuacao_id", referencedColumnName = "id")
    @ManyToOne
    private FormaAtuacao formaAtuacaoId;
    @JoinColumn(name = "tipo_unidade_id", referencedColumnName = "id")
    @ManyToOne
    private TipoUnidade tipoUnidadeId;

    public UnidadeForma() {
    }

    public UnidadeForma(Integer id) {
        this.id = id;
    }

    public UnidadeForma(Integer id, boolean contem) {
        this.id = id;
        this.contem = contem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getContem() {
        return contem;
    }

    public void setContem(boolean contem) {
        this.contem = contem;
    }

    public Empresa getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Empresa empresaId) {
        this.empresaId = empresaId;
    }

    public FormaAtuacao getFormaAtuacaoId() {
        return formaAtuacaoId;
    }

    public void setFormaAtuacaoId(FormaAtuacao formaAtuacaoId) {
        this.formaAtuacaoId = formaAtuacaoId;
    }

    public TipoUnidade getTipoUnidadeId() {
        return tipoUnidadeId;
    }

    public void setTipoUnidadeId(TipoUnidade tipoUnidadeId) {
        this.tipoUnidadeId = tipoUnidadeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadeForma)) {
            return false;
        }
        UnidadeForma other = (UnidadeForma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.UnidadeForma[ id=" + id + " ]";
    }
    
}
