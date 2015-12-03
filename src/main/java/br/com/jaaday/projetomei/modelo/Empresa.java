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
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findById", query = "SELECT e FROM Empresa e WHERE e.id = :id"),
    @NamedQuery(name = "Empresa.findByDescricaoObjeto", query = "SELECT e FROM Empresa e WHERE e.descricaoObjeto = :descricaoObjeto"),
    @NamedQuery(name = "Empresa.findByCpfCnpj", query = "SELECT e FROM Empresa e WHERE e.cpfCnpj = :cpfCnpj"),
    @NamedQuery(name = "Empresa.findByNome", query = "SELECT e FROM Empresa e WHERE e.nome = :nome"),
    @NamedQuery(name = "Empresa.findByContador", query = "SELECT e FROM Empresa e WHERE e.contador = :contador"),
    @NamedQuery(name = "Empresa.findByDdd", query = "SELECT e FROM Empresa e WHERE e.ddd = :ddd"),
    @NamedQuery(name = "Empresa.findByTelefone", query = "SELECT e FROM Empresa e WHERE e.telefone = :telefone"),
    @NamedQuery(name = "Empresa.findByRamal", query = "SELECT e FROM Empresa e WHERE e.ramal = :ramal"),
    @NamedQuery(name = "Empresa.findByEmail", query = "SELECT e FROM Empresa e WHERE e.email = :email"),
    @NamedQuery(name = "Empresa.findByStatusSolicitacao", query = "SELECT e FROM Empresa e WHERE e.statusSolicitacao = :statusSolicitacao"),
    @NamedQuery(name = "Empresa.findByAtivo", query = "SELECT e FROM Empresa e WHERE e.ativo = :ativo")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descricao_objeto")
    private String descricaoObjeto;
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
    @Column(name = "contador")
    private boolean contador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ddd")
    private String ddd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "telefone")
    private String telefone;
    @Size(max = 2147483647)
    @Column(name = "ramal")
    private String ramal;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "status_solicitacao")
    private String statusSolicitacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ativo")
    private boolean ativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Atividades> atividadesCollection;
    @OneToMany(mappedBy = "empresaId")
    private Collection<UnidadeForma> unidadeFormaCollection;
    @JoinColumn(name = "empresario_id", referencedColumnName = "id")
    @ManyToOne
    private Empresario empresarioId;
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    @ManyToOne
    private Municipio municipioId;

    public Empresa() {
        empresarioId = new Empresario();
        municipioId = new Municipio();
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Empresa(Integer id, String descricaoObjeto, String cpfCnpj, String nome, boolean contador, String ddd, String telefone, String email, boolean ativo) {
        this.id = id;
        this.descricaoObjeto = descricaoObjeto;
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.contador = contador;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoObjeto() {
        return descricaoObjeto;
    }

    public void setDescricaoObjeto(String descricaoObjeto) {
        this.descricaoObjeto = descricaoObjeto;
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

    public boolean getContador() {
        return contador;
    }

    public void setContador(boolean contador) {
        this.contador = contador;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(String statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @XmlTransient
    public Collection<Atividades> getAtividadesCollection() {
        return atividadesCollection;
    }

    public void setAtividadesCollection(Collection<Atividades> atividadesCollection) {
        this.atividadesCollection = atividadesCollection;
    }

    @XmlTransient
    public Collection<UnidadeForma> getUnidadeFormaCollection() {
        return unidadeFormaCollection;
    }

    public void setUnidadeFormaCollection(Collection<UnidadeForma> unidadeFormaCollection) {
        this.unidadeFormaCollection = unidadeFormaCollection;
    }

    public Empresario getEmpresarioId() {
        return empresarioId;
    }

    public void setEmpresarioId(Empresario empresarioId) {
        this.empresarioId = empresarioId;
    }

    public Municipio getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Municipio municipioId) {
        this.municipioId = municipioId;
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
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.Empresa[ id=" + id + " ]";
    }
    
}
