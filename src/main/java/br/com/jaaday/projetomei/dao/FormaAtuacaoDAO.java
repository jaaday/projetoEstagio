/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao;

import br.com.jaaday.projetomei.dao.exceptions.NonexistentEntityException;
import br.com.jaaday.projetomei.modelo.FormaAtuacao;
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
public class FormaAtuacaoDAO implements Serializable {

    public FormaAtuacaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaAtuacao formaAtuacao) {
        if (formaAtuacao.getUnidadeFormaCollection() == null) {
            formaAtuacao.setUnidadeFormaCollection(new ArrayList<UnidadeForma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UnidadeForma> attachedUnidadeFormaCollection = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionUnidadeFormaToAttach : formaAtuacao.getUnidadeFormaCollection()) {
                unidadeFormaCollectionUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionUnidadeFormaToAttach.getClass(), unidadeFormaCollectionUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollection.add(unidadeFormaCollectionUnidadeFormaToAttach);
            }
            formaAtuacao.setUnidadeFormaCollection(attachedUnidadeFormaCollection);
            em.persist(formaAtuacao);
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : formaAtuacao.getUnidadeFormaCollection()) {
                FormaAtuacao oldFormaAtuacaoIdOfUnidadeFormaCollectionUnidadeForma = unidadeFormaCollectionUnidadeForma.getFormaAtuacaoId();
                unidadeFormaCollectionUnidadeForma.setFormaAtuacaoId(formaAtuacao);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
                if (oldFormaAtuacaoIdOfUnidadeFormaCollectionUnidadeForma != null) {
                    oldFormaAtuacaoIdOfUnidadeFormaCollectionUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionUnidadeForma);
                    oldFormaAtuacaoIdOfUnidadeFormaCollectionUnidadeForma = em.merge(oldFormaAtuacaoIdOfUnidadeFormaCollectionUnidadeForma);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaAtuacao formaAtuacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaAtuacao persistentFormaAtuacao = em.find(FormaAtuacao.class, formaAtuacao.getId());
            Collection<UnidadeForma> unidadeFormaCollectionOld = persistentFormaAtuacao.getUnidadeFormaCollection();
            Collection<UnidadeForma> unidadeFormaCollectionNew = formaAtuacao.getUnidadeFormaCollection();
            Collection<UnidadeForma> attachedUnidadeFormaCollectionNew = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionNewUnidadeFormaToAttach : unidadeFormaCollectionNew) {
                unidadeFormaCollectionNewUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionNewUnidadeFormaToAttach.getClass(), unidadeFormaCollectionNewUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollectionNew.add(unidadeFormaCollectionNewUnidadeFormaToAttach);
            }
            unidadeFormaCollectionNew = attachedUnidadeFormaCollectionNew;
            formaAtuacao.setUnidadeFormaCollection(unidadeFormaCollectionNew);
            formaAtuacao = em.merge(formaAtuacao);
            for (UnidadeForma unidadeFormaCollectionOldUnidadeForma : unidadeFormaCollectionOld) {
                if (!unidadeFormaCollectionNew.contains(unidadeFormaCollectionOldUnidadeForma)) {
                    unidadeFormaCollectionOldUnidadeForma.setFormaAtuacaoId(null);
                    unidadeFormaCollectionOldUnidadeForma = em.merge(unidadeFormaCollectionOldUnidadeForma);
                }
            }
            for (UnidadeForma unidadeFormaCollectionNewUnidadeForma : unidadeFormaCollectionNew) {
                if (!unidadeFormaCollectionOld.contains(unidadeFormaCollectionNewUnidadeForma)) {
                    FormaAtuacao oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma = unidadeFormaCollectionNewUnidadeForma.getFormaAtuacaoId();
                    unidadeFormaCollectionNewUnidadeForma.setFormaAtuacaoId(formaAtuacao);
                    unidadeFormaCollectionNewUnidadeForma = em.merge(unidadeFormaCollectionNewUnidadeForma);
                    if (oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma != null && !oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma.equals(formaAtuacao)) {
                        oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionNewUnidadeForma);
                        oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma = em.merge(oldFormaAtuacaoIdOfUnidadeFormaCollectionNewUnidadeForma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaAtuacao.getId();
                if (findFormaAtuacao(id) == null) {
                    throw new NonexistentEntityException("The formaAtuacao with id " + id + " no longer exists.");
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
            FormaAtuacao formaAtuacao;
            try {
                formaAtuacao = em.getReference(FormaAtuacao.class, id);
                formaAtuacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaAtuacao with id " + id + " no longer exists.", enfe);
            }
            Collection<UnidadeForma> unidadeFormaCollection = formaAtuacao.getUnidadeFormaCollection();
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : unidadeFormaCollection) {
                unidadeFormaCollectionUnidadeForma.setFormaAtuacaoId(null);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
            }
            em.remove(formaAtuacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaAtuacao> findFormaAtuacaoEntities() {
        return findFormaAtuacaoEntities(true, -1, -1);
    }

    public List<FormaAtuacao> findFormaAtuacaoEntities(int maxResults, int firstResult) {
        return findFormaAtuacaoEntities(false, maxResults, firstResult);
    }

    private List<FormaAtuacao> findFormaAtuacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaAtuacao.class));
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

    public FormaAtuacao findFormaAtuacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaAtuacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaAtuacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaAtuacao> rt = cq.from(FormaAtuacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
