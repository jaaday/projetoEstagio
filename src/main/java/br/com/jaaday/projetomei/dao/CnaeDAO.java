/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao;

import br.com.jaaday.projetomei.dao.exceptions.IllegalOrphanException;
import br.com.jaaday.projetomei.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.jaaday.projetomei.modelo.Atividades;
import br.com.jaaday.projetomei.modelo.Cnae;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class CnaeDAO implements Serializable {

    public CnaeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cnae cnae) {
        if (cnae.getAtividadesCollection() == null) {
            cnae.setAtividadesCollection(new ArrayList<Atividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atividades> attachedAtividadesCollection = new ArrayList<Atividades>();
            for (Atividades atividadesCollectionAtividadesToAttach : cnae.getAtividadesCollection()) {
                atividadesCollectionAtividadesToAttach = em.getReference(atividadesCollectionAtividadesToAttach.getClass(), atividadesCollectionAtividadesToAttach.getAtividadesPK());
                attachedAtividadesCollection.add(atividadesCollectionAtividadesToAttach);
            }
            cnae.setAtividadesCollection(attachedAtividadesCollection);
            em.persist(cnae);
            for (Atividades atividadesCollectionAtividades : cnae.getAtividadesCollection()) {
                Cnae oldCnaeOfAtividadesCollectionAtividades = atividadesCollectionAtividades.getCnae();
                atividadesCollectionAtividades.setCnae(cnae);
                atividadesCollectionAtividades = em.merge(atividadesCollectionAtividades);
                if (oldCnaeOfAtividadesCollectionAtividades != null) {
                    oldCnaeOfAtividadesCollectionAtividades.getAtividadesCollection().remove(atividadesCollectionAtividades);
                    oldCnaeOfAtividadesCollectionAtividades = em.merge(oldCnaeOfAtividadesCollectionAtividades);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cnae cnae) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cnae persistentCnae = em.find(Cnae.class, cnae.getId());
            Collection<Atividades> atividadesCollectionOld = persistentCnae.getAtividadesCollection();
            Collection<Atividades> atividadesCollectionNew = cnae.getAtividadesCollection();
            List<String> illegalOrphanMessages = null;
            for (Atividades atividadesCollectionOldAtividades : atividadesCollectionOld) {
                if (!atividadesCollectionNew.contains(atividadesCollectionOldAtividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atividades " + atividadesCollectionOldAtividades + " since its cnae field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Atividades> attachedAtividadesCollectionNew = new ArrayList<Atividades>();
            for (Atividades atividadesCollectionNewAtividadesToAttach : atividadesCollectionNew) {
                atividadesCollectionNewAtividadesToAttach = em.getReference(atividadesCollectionNewAtividadesToAttach.getClass(), atividadesCollectionNewAtividadesToAttach.getAtividadesPK());
                attachedAtividadesCollectionNew.add(atividadesCollectionNewAtividadesToAttach);
            }
            atividadesCollectionNew = attachedAtividadesCollectionNew;
            cnae.setAtividadesCollection(atividadesCollectionNew);
            cnae = em.merge(cnae);
            for (Atividades atividadesCollectionNewAtividades : atividadesCollectionNew) {
                if (!atividadesCollectionOld.contains(atividadesCollectionNewAtividades)) {
                    Cnae oldCnaeOfAtividadesCollectionNewAtividades = atividadesCollectionNewAtividades.getCnae();
                    atividadesCollectionNewAtividades.setCnae(cnae);
                    atividadesCollectionNewAtividades = em.merge(atividadesCollectionNewAtividades);
                    if (oldCnaeOfAtividadesCollectionNewAtividades != null && !oldCnaeOfAtividadesCollectionNewAtividades.equals(cnae)) {
                        oldCnaeOfAtividadesCollectionNewAtividades.getAtividadesCollection().remove(atividadesCollectionNewAtividades);
                        oldCnaeOfAtividadesCollectionNewAtividades = em.merge(oldCnaeOfAtividadesCollectionNewAtividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cnae.getId();
                if (findCnae(id) == null) {
                    throw new NonexistentEntityException("The cnae with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cnae cnae;
            try {
                cnae = em.getReference(Cnae.class, id);
                cnae.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cnae with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Atividades> atividadesCollectionOrphanCheck = cnae.getAtividadesCollection();
            for (Atividades atividadesCollectionOrphanCheckAtividades : atividadesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cnae (" + cnae + ") cannot be destroyed since the Atividades " + atividadesCollectionOrphanCheckAtividades + " in its atividadesCollection field has a non-nullable cnae field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cnae);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cnae> findCnaeEntities() {
        return findCnaeEntities(true, -1, -1);
    }

    public List<Cnae> findCnaeEntities(int maxResults, int firstResult) {
        return findCnaeEntities(false, maxResults, firstResult);
    }

    private List<Cnae> findCnaeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cnae.class));
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

    public Cnae findCnae(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cnae.class, id);
        } finally {
            em.close();
        }
    }

    public int getCnaeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cnae> rt = cq.from(Cnae.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
