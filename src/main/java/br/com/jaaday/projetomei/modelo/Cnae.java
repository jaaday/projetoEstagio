/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "cnae")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cnae.findAll", query = "SELECT c FROM Cnae c"),
    @NamedQuery(name = "Cnae.findById", query = "SELECT c FROM Cnae c WHERE c.id = :id"),
    @NamedQuery(name = "Cnae.findByCodigo", query = "SELECT c FROM Cnae c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cnae.findByDescricao", query = "SELECT c FROM Cnae c WHERE c.descricao = :descricao")})
public class Cnae implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cnae")
    private Collection<Atividades> atividadesCollection;

    public Cnae() {
    }

    public Cnae(Integer id) {
        this.id = id;
    }

    public Cnae(Integer id, String codigo, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Atividades> getAtividadesCollection() {
        return atividadesCollection;
    }

    public void setAtividadesCollection(Collection<Atividades> atividadesCollection) {
        this.atividadesCollection = atividadesCollection;
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
        if (!(object instanceof Cnae)) {
            return false;
        }
        Cnae other = (Cnae) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.Cnae[ id=" + id + " ]";
    }
    
}
