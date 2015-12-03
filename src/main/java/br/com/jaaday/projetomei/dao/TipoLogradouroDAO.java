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
import br.com.jaaday.projetomei.modelo.Endereco;
import br.com.jaaday.projetomei.modelo.TipoLogradouro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class TipoLogradouroDAO implements Serializable {

    public TipoLogradouroDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoLogradouro tipoLogradouro) {
        if (tipoLogradouro.getEnderecoCollection() == null) {
            tipoLogradouro.setEnderecoCollection(new ArrayList<Endereco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : tipoLogradouro.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getId());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            tipoLogradouro.setEnderecoCollection(attachedEnderecoCollection);
            em.persist(tipoLogradouro);
            for (Endereco enderecoCollectionEndereco : tipoLogradouro.getEnderecoCollection()) {
                TipoLogradouro oldTipoLogradouroIdOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getTipoLogradouroId();
                enderecoCollectionEndereco.setTipoLogradouroId(tipoLogradouro);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldTipoLogradouroIdOfEnderecoCollectionEndereco != null) {
                    oldTipoLogradouroIdOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldTipoLogradouroIdOfEnderecoCollectionEndereco = em.merge(oldTipoLogradouroIdOfEnderecoCollectionEndereco);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoLogradouro tipoLogradouro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoLogradouro persistentTipoLogradouro = em.find(TipoLogradouro.class, tipoLogradouro.getId());
            Collection<Endereco> enderecoCollectionOld = persistentTipoLogradouro.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = tipoLogradouro.getEnderecoCollection();
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getId());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            tipoLogradouro.setEnderecoCollection(enderecoCollectionNew);
            tipoLogradouro = em.merge(tipoLogradouro);
            for (Endereco enderecoCollectionOldEndereco : enderecoCollectionOld) {
                if (!enderecoCollectionNew.contains(enderecoCollectionOldEndereco)) {
                    enderecoCollectionOldEndereco.setTipoLogradouroId(null);
                    enderecoCollectionOldEndereco = em.merge(enderecoCollectionOldEndereco);
                }
            }
            for (Endereco enderecoCollectionNewEndereco : enderecoCollectionNew) {
                if (!enderecoCollectionOld.contains(enderecoCollectionNewEndereco)) {
                    TipoLogradouro oldTipoLogradouroIdOfEnderecoCollectionNewEndereco = enderecoCollectionNewEndereco.getTipoLogradouroId();
                    enderecoCollectionNewEndereco.setTipoLogradouroId(tipoLogradouro);
                    enderecoCollectionNewEndereco = em.merge(enderecoCollectionNewEndereco);
                    if (oldTipoLogradouroIdOfEnderecoCollectionNewEndereco != null && !oldTipoLogradouroIdOfEnderecoCollectionNewEndereco.equals(tipoLogradouro)) {
                        oldTipoLogradouroIdOfEnderecoCollectionNewEndereco.getEnderecoCollection().remove(enderecoCollectionNewEndereco);
                        oldTipoLogradouroIdOfEnderecoCollectionNewEndereco = em.merge(oldTipoLogradouroIdOfEnderecoCollectionNewEndereco);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoLogradouro.getId();
                if (findTipoLogradouro(id) == null) {
                    throw new NonexistentEntityException("The tipoLogradouro with id " + id + " no longer exists.");
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
            TipoLogradouro tipoLogradouro;
            try {
                tipoLogradouro = em.getReference(TipoLogradouro.class, id);
                tipoLogradouro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoLogradouro with id " + id + " no longer exists.", enfe);
            }
            Collection<Endereco> enderecoCollection = tipoLogradouro.getEnderecoCollection();
            for (Endereco enderecoCollectionEndereco : enderecoCollection) {
                enderecoCollectionEndereco.setTipoLogradouroId(null);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
            }
            em.remove(tipoLogradouro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoLogradouro> findTipoLogradouroEntities() {
        return findTipoLogradouroEntities(true, -1, -1);
    }

    public List<TipoLogradouro> findTipoLogradouroEntities(int maxResults, int firstResult) {
        return findTipoLogradouroEntities(false, maxResults, firstResult);
    }

    private List<TipoLogradouro> findTipoLogradouroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoLogradouro.class));
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

    public TipoLogradouro findTipoLogradouro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoLogradouro.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoLogradouroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoLogradouro> rt = cq.from(TipoLogradouro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
