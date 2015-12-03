/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "forma_atuacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormaAtuacao.findAll", query = "SELECT f FROM FormaAtuacao f"),
    @NamedQuery(name = "FormaAtuacao.findById", query = "SELECT f FROM FormaAtuacao f WHERE f.id = :id"),
    @NamedQuery(name = "FormaAtuacao.findByDescricao", query = "SELECT f FROM FormaAtuacao f WHERE f.descricao = :descricao")})
public class FormaAtuacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "formaAtuacaoId")
    private Collection<UnidadeForma> unidadeFormaCollection;

    public FormaAtuacao() {
    }

    public FormaAtuacao(Integer id) {
        this.id = id;
    }

    public FormaAtuacao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<UnidadeForma> getUnidadeFormaCollection() {
        return unidadeFormaCollection;
    }

    public void setUnidadeFormaCollection(Collection<UnidadeForma> unidadeFormaCollection) {
        this.unidadeFormaCollection = unidadeFormaCollection;
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
        if (!(object instanceof FormaAtuacao)) {
            return false;
        }
        FormaAtuacao other = (FormaAtuacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.FormaAtuacao[ id=" + id + " ]";
    }
    
}
