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
import br.com.jaaday.projetomei.modelo.Empresa;
import br.com.jaaday.projetomei.modelo.Empresario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class EmpresarioDAO implements Serializable {

    public EmpresarioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresario empresario) {
        if (empresario.getEmpresaCollection() == null) {
            empresario.setEmpresaCollection(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco enderecoId = empresario.getEnderecoId();
            if (enderecoId != null) {
                enderecoId = em.getReference(enderecoId.getClass(), enderecoId.getId());
                empresario.setEnderecoId(enderecoId);
            }
            Collection<Empresa> attachedEmpresaCollection = new ArrayList<Empresa>();
            for (Empresa empresaCollectionEmpresaToAttach : empresario.getEmpresaCollection()) {
                empresaCollectionEmpresaToAttach = em.getReference(empresaCollectionEmpresaToAttach.getClass(), empresaCollectionEmpresaToAttach.getId());
                attachedEmpresaCollection.add(empresaCollectionEmpresaToAttach);
            }
            empresario.setEmpresaCollection(attachedEmpresaCollection);
            em.persist(empresario);
            if (enderecoId != null) {
                enderecoId.getEmpresarioCollection().add(empresario);
                enderecoId = em.merge(enderecoId);
            }
            for (Empresa empresaCollectionEmpresa : empresario.getEmpresaCollection()) {
                Empresario oldEmpresarioIdOfEmpresaCollectionEmpresa = empresaCollectionEmpresa.getEmpresarioId();
                empresaCollectionEmpresa.setEmpresarioId(empresario);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
                if (oldEmpresarioIdOfEmpresaCollectionEmpresa != null) {
                    oldEmpresarioIdOfEmpresaCollectionEmpresa.getEmpresaCollection().remove(empresaCollectionEmpresa);
                    oldEmpresarioIdOfEmpresaCollectionEmpresa = em.merge(oldEmpresarioIdOfEmpresaCollectionEmpresa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresario empresario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresario persistentEmpresario = em.find(Empresario.class, empresario.getId());
            Endereco enderecoIdOld = persistentEmpresario.getEnderecoId();
            Endereco enderecoIdNew = empresario.getEnderecoId();
            Collection<Empresa> empresaCollectionOld = persistentEmpresario.getEmpresaCollection();
            Collection<Empresa> empresaCollectionNew = empresario.getEmpresaCollection();
            if (enderecoIdNew != null) {
                enderecoIdNew = em.getReference(enderecoIdNew.getClass(), enderecoIdNew.getId());
                empresario.setEnderecoId(enderecoIdNew);
            }
            Collection<Empresa> attachedEmpresaCollectionNew = new ArrayList<Empresa>();
            for (Empresa empresaCollectionNewEmpresaToAttach : empresaCollectionNew) {
                empresaCollectionNewEmpresaToAttach = em.getReference(empresaCollectionNewEmpresaToAttach.getClass(), empresaCollectionNewEmpresaToAttach.getId());
                attachedEmpresaCollectionNew.add(empresaCollectionNewEmpresaToAttach);
            }
            empresaCollectionNew = attachedEmpresaCollectionNew;
            empresario.setEmpresaCollection(empresaCollectionNew);
            empresario = em.merge(empresario);
            if (enderecoIdOld != null && !enderecoIdOld.equals(enderecoIdNew)) {
                enderecoIdOld.getEmpresarioCollection().remove(empresario);
                enderecoIdOld = em.merge(enderecoIdOld);
            }
            if (enderecoIdNew != null && !enderecoIdNew.equals(enderecoIdOld)) {
                enderecoIdNew.getEmpresarioCollection().add(empresario);
                enderecoIdNew = em.merge(enderecoIdNew);
            }
            for (Empresa empresaCollectionOldEmpresa : empresaCollectionOld) {
                if (!empresaCollectionNew.contains(empresaCollectionOldEmpresa)) {
                    empresaCollectionOldEmpresa.setEmpresarioId(null);
                    empresaCollectionOldEmpresa = em.merge(empresaCollectionOldEmpresa);
                }
            }
            for (Empresa empresaCollectionNewEmpresa : empresaCollectionNew) {
                if (!empresaCollectionOld.contains(empresaCollectionNewEmpresa)) {
                    Empresario oldEmpresarioIdOfEmpresaCollectionNewEmpresa = empresaCollectionNewEmpresa.getEmpresarioId();
                    empresaCollectionNewEmpresa.setEmpresarioId(empresario);
                    empresaCollectionNewEmpresa = em.merge(empresaCollectionNewEmpresa);
                    if (oldEmpresarioIdOfEmpresaCollectionNewEmpresa != null && !oldEmpresarioIdOfEmpresaCollectionNewEmpresa.equals(empresario)) {
                        oldEmpresarioIdOfEmpresaCollectionNewEmpresa.getEmpresaCollection().remove(empresaCollectionNewEmpresa);
                        oldEmpresarioIdOfEmpresaCollectionNewEmpresa = em.merge(oldEmpresarioIdOfEmpresaCollectionNewEmpresa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresario.getId();
                if (findEmpresario(id) == null) {
                    throw new NonexistentEntityException("The empresario with id " + id + " no longer exists.");
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
            Empresario empresario;
            try {
                empresario = em.getReference(Empresario.class, id);
                empresario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresario with id " + id + " no longer exists.", enfe);
            }
            Endereco enderecoId = empresario.getEnderecoId();
            if (enderecoId != null) {
                enderecoId.getEmpresarioCollection().remove(empresario);
                enderecoId = em.merge(enderecoId);
            }
            Collection<Empresa> empresaCollection = empresario.getEmpresaCollection();
            for (Empresa empresaCollectionEmpresa : empresaCollection) {
                empresaCollectionEmpresa.setEmpresarioId(null);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
            }
            em.remove(empresario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresario> findEmpresarioEntities() {
        return findEmpresarioEntities(true, -1, -1);
    }

    public List<Empresario> findEmpresarioEntities(int maxResults, int firstResult) {
        return findEmpresarioEntities(false, maxResults, firstResult);
    }

    private List<Empresario> findEmpresarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresario.class));
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

    public Empresario findEmpresario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresario.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresario> rt = cq.from(Empresario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
