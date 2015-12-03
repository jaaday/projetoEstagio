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
import br.com.jaaday.projetomei.modelo.TipoImovel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class TipoImovelDAO implements Serializable {

    public TipoImovelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoImovel tipoImovel) {
        if (tipoImovel.getEnderecoCollection() == null) {
            tipoImovel.setEnderecoCollection(new ArrayList<Endereco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : tipoImovel.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getId());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            tipoImovel.setEnderecoCollection(attachedEnderecoCollection);
            em.persist(tipoImovel);
            for (Endereco enderecoCollectionEndereco : tipoImovel.getEnderecoCollection()) {
                TipoImovel oldTipoImovelIdOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getTipoImovelId();
                enderecoCollectionEndereco.setTipoImovelId(tipoImovel);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldTipoImovelIdOfEnderecoCollectionEndereco != null) {
                    oldTipoImovelIdOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldTipoImovelIdOfEnderecoCollectionEndereco = em.merge(oldTipoImovelIdOfEnderecoCollectionEndereco);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoImovel tipoImovel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoImovel persistentTipoImovel = em.find(TipoImovel.class, tipoImovel.getId());
            Collection<Endereco> enderecoCollectionOld = persistentTipoImovel.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = tipoImovel.getEnderecoCollection();
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getId());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            tipoImovel.setEnderecoCollection(enderecoCollectionNew);
            tipoImovel = em.merge(tipoImovel);
            for (Endereco enderecoCollectionOldEndereco : enderecoCollectionOld) {
                if (!enderecoCollectionNew.contains(enderecoCollectionOldEndereco)) {
                    enderecoCollectionOldEndereco.setTipoImovelId(null);
                    enderecoCollectionOldEndereco = em.merge(enderecoCollectionOldEndereco);
                }
            }
            for (Endereco enderecoCollectionNewEndereco : enderecoCollectionNew) {
                if (!enderecoCollectionOld.contains(enderecoCollectionNewEndereco)) {
                    TipoImovel oldTipoImovelIdOfEnderecoCollectionNewEndereco = enderecoCollectionNewEndereco.getTipoImovelId();
                    enderecoCollectionNewEndereco.setTipoImovelId(tipoImovel);
                    enderecoCollectionNewEndereco = em.merge(enderecoCollectionNewEndereco);
                    if (oldTipoImovelIdOfEnderecoCollectionNewEndereco != null && !oldTipoImovelIdOfEnderecoCollectionNewEndereco.equals(tipoImovel)) {
                        oldTipoImovelIdOfEnderecoCollectionNewEndereco.getEnderecoCollection().remove(enderecoCollectionNewEndereco);
                        oldTipoImovelIdOfEnderecoCollectionNewEndereco = em.merge(oldTipoImovelIdOfEnderecoCollectionNewEndereco);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoImovel.getId();
                if (findTipoImovel(id) == null) {
                    throw new NonexistentEntityException("The tipoImovel with id " + id + " no longer exists.");
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
            TipoImovel tipoImovel;
            try {
                tipoImovel = em.getReference(TipoImovel.class, id);
                tipoImovel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoImovel with id " + id + " no longer exists.", enfe);
            }
            Collection<Endereco> enderecoCollection = tipoImovel.getEnderecoCollection();
            for (Endereco enderecoCollectionEndereco : enderecoCollection) {
                enderecoCollectionEndereco.setTipoImovelId(null);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
            }
            em.remove(tipoImovel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoImovel> findTipoImovelEntities() {
        return findTipoImovelEntities(true, -1, -1);
    }

    public List<TipoImovel> findTipoImovelEntities(int maxResults, int firstResult) {
        return findTipoImovelEntities(false, maxResults, firstResult);
    }

    private List<TipoImovel> findTipoImovelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoImovel.class));
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

    public TipoImovel findTipoImovel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoImovel.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoImovelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoImovel> rt = cq.from(TipoImovel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
