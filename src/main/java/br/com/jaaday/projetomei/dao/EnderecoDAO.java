/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao;

import br.com.jaaday.projetomei.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.jaaday.projetomei.modelo.TipoImovel;
import br.com.jaaday.projetomei.modelo.TipoLogradouro;
import br.com.jaaday.projetomei.modelo.Empresario;
import br.com.jaaday.projetomei.modelo.Endereco;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class EnderecoDAO implements Serializable {

    public EnderecoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) {
        if (endereco.getEmpresarioCollection() == null) {
            endereco.setEmpresarioCollection(new ArrayList<Empresario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoImovel tipoImovelId = endereco.getTipoImovelId();
            if (tipoImovelId != null) {
                tipoImovelId = em.getReference(tipoImovelId.getClass(), tipoImovelId.getId());
                endereco.setTipoImovelId(tipoImovelId);
            }
            TipoLogradouro tipoLogradouroId = endereco.getTipoLogradouroId();
            if (tipoLogradouroId != null) {
                tipoLogradouroId = em.getReference(tipoLogradouroId.getClass(), tipoLogradouroId.getId());
                endereco.setTipoLogradouroId(tipoLogradouroId);
            }
            Collection<Empresario> attachedEmpresarioCollection = new ArrayList<Empresario>();
            for (Empresario empresarioCollectionEmpresarioToAttach : endereco.getEmpresarioCollection()) {
                empresarioCollectionEmpresarioToAttach = em.getReference(empresarioCollectionEmpresarioToAttach.getClass(), empresarioCollectionEmpresarioToAttach.getId());
                attachedEmpresarioCollection.add(empresarioCollectionEmpresarioToAttach);
            }
            endereco.setEmpresarioCollection(attachedEmpresarioCollection);
            em.persist(endereco);
            if (tipoImovelId != null) {
                tipoImovelId.getEnderecoCollection().add(endereco);
                tipoImovelId = em.merge(tipoImovelId);
            }
            if (tipoLogradouroId != null) {
                tipoLogradouroId.getEnderecoCollection().add(endereco);
                tipoLogradouroId = em.merge(tipoLogradouroId);
            }
            for (Empresario empresarioCollectionEmpresario : endereco.getEmpresarioCollection()) {
                Endereco oldEnderecoIdOfEmpresarioCollectionEmpresario = empresarioCollectionEmpresario.getEnderecoId();
                empresarioCollectionEmpresario.setEnderecoId(endereco);
                empresarioCollectionEmpresario = em.merge(empresarioCollectionEmpresario);
                if (oldEnderecoIdOfEmpresarioCollectionEmpresario != null) {
                    oldEnderecoIdOfEmpresarioCollectionEmpresario.getEmpresarioCollection().remove(empresarioCollectionEmpresario);
                    oldEnderecoIdOfEmpresarioCollectionEmpresario = em.merge(oldEnderecoIdOfEmpresarioCollectionEmpresario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco endereco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            TipoImovel tipoImovelIdOld = persistentEndereco.getTipoImovelId();
            TipoImovel tipoImovelIdNew = endereco.getTipoImovelId();
            TipoLogradouro tipoLogradouroIdOld = persistentEndereco.getTipoLogradouroId();
            TipoLogradouro tipoLogradouroIdNew = endereco.getTipoLogradouroId();
            Collection<Empresario> empresarioCollectionOld = persistentEndereco.getEmpresarioCollection();
            Collection<Empresario> empresarioCollectionNew = endereco.getEmpresarioCollection();
            if (tipoImovelIdNew != null) {
                tipoImovelIdNew = em.getReference(tipoImovelIdNew.getClass(), tipoImovelIdNew.getId());
                endereco.setTipoImovelId(tipoImovelIdNew);
            }
            if (tipoLogradouroIdNew != null) {
                tipoLogradouroIdNew = em.getReference(tipoLogradouroIdNew.getClass(), tipoLogradouroIdNew.getId());
                endereco.setTipoLogradouroId(tipoLogradouroIdNew);
            }
            Collection<Empresario> attachedEmpresarioCollectionNew = new ArrayList<Empresario>();
            for (Empresario empresarioCollectionNewEmpresarioToAttach : empresarioCollectionNew) {
                empresarioCollectionNewEmpresarioToAttach = em.getReference(empresarioCollectionNewEmpresarioToAttach.getClass(), empresarioCollectionNewEmpresarioToAttach.getId());
                attachedEmpresarioCollectionNew.add(empresarioCollectionNewEmpresarioToAttach);
            }
            empresarioCollectionNew = attachedEmpresarioCollectionNew;
            endereco.setEmpresarioCollection(empresarioCollectionNew);
            endereco = em.merge(endereco);
            if (tipoImovelIdOld != null && !tipoImovelIdOld.equals(tipoImovelIdNew)) {
                tipoImovelIdOld.getEnderecoCollection().remove(endereco);
                tipoImovelIdOld = em.merge(tipoImovelIdOld);
            }
            if (tipoImovelIdNew != null && !tipoImovelIdNew.equals(tipoImovelIdOld)) {
                tipoImovelIdNew.getEnderecoCollection().add(endereco);
                tipoImovelIdNew = em.merge(tipoImovelIdNew);
            }
            if (tipoLogradouroIdOld != null && !tipoLogradouroIdOld.equals(tipoLogradouroIdNew)) {
                tipoLogradouroIdOld.getEnderecoCollection().remove(endereco);
                tipoLogradouroIdOld = em.merge(tipoLogradouroIdOld);
            }
            if (tipoLogradouroIdNew != null && !tipoLogradouroIdNew.equals(tipoLogradouroIdOld)) {
                tipoLogradouroIdNew.getEnderecoCollection().add(endereco);
                tipoLogradouroIdNew = em.merge(tipoLogradouroIdNew);
            }
            for (Empresario empresarioCollectionOldEmpresario : empresarioCollectionOld) {
                if (!empresarioCollectionNew.contains(empresarioCollectionOldEmpresario)) {
                    empresarioCollectionOldEmpresario.setEnderecoId(null);
                    empresarioCollectionOldEmpresario = em.merge(empresarioCollectionOldEmpresario);
                }
            }
            for (Empresario empresarioCollectionNewEmpresario : empresarioCollectionNew) {
                if (!empresarioCollectionOld.contains(empresarioCollectionNewEmpresario)) {
                    Endereco oldEnderecoIdOfEmpresarioCollectionNewEmpresario = empresarioCollectionNewEmpresario.getEnderecoId();
                    empresarioCollectionNewEmpresario.setEnderecoId(endereco);
                    empresarioCollectionNewEmpresario = em.merge(empresarioCollectionNewEmpresario);
                    if (oldEnderecoIdOfEmpresarioCollectionNewEmpresario != null && !oldEnderecoIdOfEmpresarioCollectionNewEmpresario.equals(endereco)) {
                        oldEnderecoIdOfEmpresarioCollectionNewEmpresario.getEmpresarioCollection().remove(empresarioCollectionNewEmpresario);
                        oldEnderecoIdOfEmpresarioCollectionNewEmpresario = em.merge(oldEnderecoIdOfEmpresarioCollectionNewEmpresario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            TipoImovel tipoImovelId = endereco.getTipoImovelId();
            if (tipoImovelId != null) {
                tipoImovelId.getEnderecoCollection().remove(endereco);
                tipoImovelId = em.merge(tipoImovelId);
            }
            TipoLogradouro tipoLogradouroId = endereco.getTipoLogradouroId();
            if (tipoLogradouroId != null) {
                tipoLogradouroId.getEnderecoCollection().remove(endereco);
                tipoLogradouroId = em.merge(tipoLogradouroId);
            }
            Collection<Empresario> empresarioCollection = endereco.getEmpresarioCollection();
            for (Empresario empresarioCollectionEmpresario : empresarioCollection) {
                empresarioCollectionEmpresario.setEnderecoId(null);
                empresarioCollectionEmpresario = em.merge(empresarioCollectionEmpresario);
            }
            em.remove(endereco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
