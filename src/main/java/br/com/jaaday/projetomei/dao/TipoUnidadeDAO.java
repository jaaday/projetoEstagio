/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao;

import br.com.jaaday.projetomei.dao.exceptions.NonexistentEntityException;
import br.com.jaaday.projetomei.modelo.TipoUnidade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.jaaday.projetomei.modelo.UnidadeForma;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class TipoUnidadeDAO implements Serializable {

    public TipoUnidadeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUnidade tipoUnidade) {
        if (tipoUnidade.getUnidadeFormaCollection() == null) {
            tipoUnidade.setUnidadeFormaCollection(new ArrayList<UnidadeForma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UnidadeForma> attachedUnidadeFormaCollection = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionUnidadeFormaToAttach : tipoUnidade.getUnidadeFormaCollection()) {
                unidadeFormaCollectionUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionUnidadeFormaToAttach.getClass(), unidadeFormaCollectionUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollection.add(unidadeFormaCollectionUnidadeFormaToAttach);
            }
            tipoUnidade.setUnidadeFormaCollection(attachedUnidadeFormaCollection);
            em.persist(tipoUnidade);
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : tipoUnidade.getUnidadeFormaCollection()) {
                TipoUnidade oldTipoUnidadeIdOfUnidadeFormaCollectionUnidadeForma = unidadeFormaCollectionUnidadeForma.getTipoUnidadeId();
                unidadeFormaCollectionUnidadeForma.setTipoUnidadeId(tipoUnidade);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
                if (oldTipoUnidadeIdOfUnidadeFormaCollectionUnidadeForma != null) {
                    oldTipoUnidadeIdOfUnidadeFormaCollectionUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionUnidadeForma);
                    oldTipoUnidadeIdOfUnidadeFormaCollectionUnidadeForma = em.merge(oldTipoUnidadeIdOfUnidadeFormaCollectionUnidadeForma);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUnidade tipoUnidade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUnidade persistentTipoUnidade = em.find(TipoUnidade.class, tipoUnidade.getId());
            Collection<UnidadeForma> unidadeFormaCollectionOld = persistentTipoUnidade.getUnidadeFormaCollection();
            Collection<UnidadeForma> unidadeFormaCollectionNew = tipoUnidade.getUnidadeFormaCollection();
            Collection<UnidadeForma> attachedUnidadeFormaCollectionNew = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionNewUnidadeFormaToAttach : unidadeFormaCollectionNew) {
                unidadeFormaCollectionNewUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionNewUnidadeFormaToAttach.getClass(), unidadeFormaCollectionNewUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollectionNew.add(unidadeFormaCollectionNewUnidadeFormaToAttach);
            }
            unidadeFormaCollectionNew = attachedUnidadeFormaCollectionNew;
            tipoUnidade.setUnidadeFormaCollection(unidadeFormaCollectionNew);
            tipoUnidade = em.merge(tipoUnidade);
            for (UnidadeForma unidadeFormaCollectionOldUnidadeForma : unidadeFormaCollectionOld) {
                if (!unidadeFormaCollectionNew.contains(unidadeFormaCollectionOldUnidadeForma)) {
                    unidadeFormaCollectionOldUnidadeForma.setTipoUnidadeId(null);
                    unidadeFormaCollectionOldUnidadeForma = em.merge(unidadeFormaCollectionOldUnidadeForma);
                }
            }
            for (UnidadeForma unidadeFormaCollectionNewUnidadeForma : unidadeFormaCollectionNew) {
                if (!unidadeFormaCollectionOld.contains(unidadeFormaCollectionNewUnidadeForma)) {
                    TipoUnidade oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma = unidadeFormaCollectionNewUnidadeForma.getTipoUnidadeId();
                    unidadeFormaCollectionNewUnidadeForma.setTipoUnidadeId(tipoUnidade);
                    unidadeFormaCollectionNewUnidadeForma = em.merge(unidadeFormaCollectionNewUnidadeForma);
                    if (oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma != null && !oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma.equals(tipoUnidade)) {
                        oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionNewUnidadeForma);
                        oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma = em.merge(oldTipoUnidadeIdOfUnidadeFormaCollectionNewUnidadeForma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoUnidade.getId();
                if (findTipoUnidade(id) == null) {
                    throw new NonexistentEntityException("The tipoUnidade with id " + id + " no longer exists.");
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
            TipoUnidade tipoUnidade;
            try {
                tipoUnidade = em.getReference(TipoUnidade.class, id);
                tipoUnidade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUnidade with id " + id + " no longer exists.", enfe);
            }
            Collection<UnidadeForma> unidadeFormaCollection = tipoUnidade.getUnidadeFormaCollection();
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : unidadeFormaCollection) {
                unidadeFormaCollectionUnidadeForma.setTipoUnidadeId(null);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
            }
            em.remove(tipoUnidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUnidade> findTipoUnidadeEntities() {
        return findTipoUnidadeEntities(true, -1, -1);
    }

    public List<TipoUnidade> findTipoUnidadeEntities(int maxResults, int firstResult) {
        return findTipoUnidadeEntities(false, maxResults, firstResult);
    }

    private List<TipoUnidade> findTipoUnidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUnidade.class));
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

    public TipoUnidade findTipoUnidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUnidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUnidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUnidade> rt = cq.from(TipoUnidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
