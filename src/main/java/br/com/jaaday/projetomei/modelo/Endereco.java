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
@Table(name = "endereco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findById", query = "SELECT e FROM Endereco e WHERE e.id = :id"),
    @NamedQuery(name = "Endereco.findByCep", query = "SELECT e FROM Endereco e WHERE e.cep = :cep"),
    @NamedQuery(name = "Endereco.findByLogradouro", query = "SELECT e FROM Endereco e WHERE e.logradouro = :logradouro"),
    @NamedQuery(name = "Endereco.findByBairro", query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endereco.findByComplemento", query = "SELECT e FROM Endereco e WHERE e.complemento = :complemento"),
    @NamedQuery(name = "Endereco.findByPontoReferencia", query = "SELECT e FROM Endereco e WHERE e.pontoReferencia = :pontoReferencia"),
    @NamedQuery(name = "Endereco.findByPossuiAutorizacao", query = "SELECT e FROM Endereco e WHERE e.possuiAutorizacao = :possuiAutorizacao"),
    @NamedQuery(name = "Endereco.findByNumero", query = "SELECT e FROM Endereco e WHERE e.numero = :numero"),
    @NamedQuery(name = "Endereco.findByMetragem", query = "SELECT e FROM Endereco e WHERE e.metragem = :metragem"),
    @NamedQuery(name = "Endereco.findByNaturezaImovel", query = "SELECT e FROM Endereco e WHERE e.naturezaImovel = :naturezaImovel")})
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cep")
    private String cep;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "logradouro")
    private String logradouro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 2147483647)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 2147483647)
    @Column(name = "ponto_referencia")
    private String pontoReferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "possui_autorizacao")
    private boolean possuiAutorizacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "metragem")
    private double metragem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "natureza_imovel")
    private String naturezaImovel;
    @JoinColumn(name = "tipo_imovel_id", referencedColumnName = "id")
    @ManyToOne
    private TipoImovel tipoImovelId;
    @JoinColumn(name = "tipo_logradouro_id", referencedColumnName = "id")
    @ManyToOne
    private TipoLogradouro tipoLogradouroId;
    @OneToMany(mappedBy = "enderecoId")
    private Collection<Empresario> empresarioCollection;

    public Endereco() {
    }

    public Endereco(Integer id) {
        this.id = id;
    }

    public Endereco(Integer id, String cep, String logradouro, String bairro, boolean possuiAutorizacao, String numero, double metragem, String naturezaImovel) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.possuiAutorizacao = possuiAutorizacao;
        this.numero = numero;
        this.metragem = metragem;
        this.naturezaImovel = naturezaImovel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public boolean getPossuiAutorizacao() {
        return possuiAutorizacao;
    }

    public void setPossuiAutorizacao(boolean possuiAutorizacao) {
        this.possuiAutorizacao = possuiAutorizacao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMetragem() {
        return metragem;
    }

    public void setMetragem(double metragem) {
        this.metragem = metragem;
    }

    public String getNaturezaImovel() {
        return naturezaImovel;
    }

    public void setNaturezaImovel(String naturezaImovel) {
        this.naturezaImovel = naturezaImovel;
    }

    public TipoImovel getTipoImovelId() {
        return tipoImovelId;
    }

    public void setTipoImovelId(TipoImovel tipoImovelId) {
        this.tipoImovelId = tipoImovelId;
    }

    public TipoLogradouro getTipoLogradouroId() {
        return tipoLogradouroId;
    }

    public void setTipoLogradouroId(TipoLogradouro tipoLogradouroId) {
        this.tipoLogradouroId = tipoLogradouroId;
    }

    @XmlTransient
    public Collection<Empresario> getEmpresarioCollection() {
        return empresarioCollection;
    }

    public void setEmpresarioCollection(Collection<Empresario> empresarioCollection) {
        this.empresarioCollection = empresarioCollection;
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
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.jaaday.projetomei.modelo.Endereco[ id=" + id + " ]";
    }
    
}
