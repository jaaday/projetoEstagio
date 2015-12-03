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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "empresario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresario.findAll", query = "SELECT e FROM Empresario e"),
    @NamedQuery(name = "Empresario.findById", query = "SELECT e FROM Empresario e WHERE e.id = :id"),
    @NamedQuery(name = "Empresario.findByCpfCnpj", query = "SELECT e FROM Empresario e WHERE e.cpfCnpj = :cpfCnpj"),
    @NamedQuery(name = "Empresario.findByNome", query = "SELECT e FROM Empresario e WHERE e.nome = :nome"),
    @NamedQuery(name = "Empresario.findByNomeMae", query = "SELECT e FROM Empresario e WHERE e.nomeMae = :nomeMae")})
public class Empresario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome_mae")
    private String nomeMae;
    @OneToMany(mappedBy = "empresarioId")
    private Collection<Empresa> empresaCollection;
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @ManyToOne
    private Endereco enderecoId;

    public Empresario() {
    }

    public Empresario(Integer id) {
        this.id = id;
    }

    public Empresario(Integer id, String cpfCnpj, String nome, String nomeMae) {
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.nomeMae = nomeMae;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    @XmlTransient
    public Collection<Empresa> getEmpresaCollection() {
        return empresaCollection;
    }

    public void setEmpresaCollection(Collection<Empresa> empresaCollection) {
        this.empresaCollection = empresaCollection;
    }

    public Endereco getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Endereco enderecoId) {
        this.enderecoId = enderecoId;
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
        if (!(object instanceof Empresario)) {
            return false;
        }
        Empresario other = (Empresario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.Empresario[ id=" + id + " ]";
    }
    
}
