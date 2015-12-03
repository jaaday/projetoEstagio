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
import br.com.jaaday.projetomei.modelo.Empresa;
import br.com.jaaday.projetomei.modelo.Municipio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class MunicipioDAO implements Serializable {

    public MunicipioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) {
        if (municipio.getEmpresaCollection() == null) {
            municipio.setEmpresaCollection(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empresa> attachedEmpresaCollection = new ArrayList<Empresa>();
            for (Empresa empresaCollectionEmpresaToAttach : municipio.getEmpresaCollection()) {
                empresaCollectionEmpresaToAttach = em.getReference(empresaCollectionEmpresaToAttach.getClass(), empresaCollectionEmpresaToAttach.getId());
                attachedEmpresaCollection.add(empresaCollectionEmpresaToAttach);
            }
            municipio.setEmpresaCollection(attachedEmpresaCollection);
            em.persist(municipio);
            for (Empresa empresaCollectionEmpresa : municipio.getEmpresaCollection()) {
                Municipio oldMunicipioIdOfEmpresaCollectionEmpresa = empresaCollectionEmpresa.getMunicipioId();
                empresaCollectionEmpresa.setMunicipioId(municipio);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
                if (oldMunicipioIdOfEmpresaCollectionEmpresa != null) {
                    oldMunicipioIdOfEmpresaCollectionEmpresa.getEmpresaCollection().remove(empresaCollectionEmpresa);
                    oldMunicipioIdOfEmpresaCollectionEmpresa = em.merge(oldMunicipioIdOfEmpresaCollectionEmpresa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getId());
            Collection<Empresa> empresaCollectionOld = persistentMunicipio.getEmpresaCollection();
            Collection<Empresa> empresaCollectionNew = municipio.getEmpresaCollection();
            Collection<Empresa> attachedEmpresaCollectionNew = new ArrayList<Empresa>();
            for (Empresa empresaCollectionNewEmpresaToAttach : empresaCollectionNew) {
                empresaCollectionNewEmpresaToAttach = em.getReference(empresaCollectionNewEmpresaToAttach.getClass(), empresaCollectionNewEmpresaToAttach.getId());
                attachedEmpresaCollectionNew.add(empresaCollectionNewEmpresaToAttach);
            }
            empresaCollectionNew = attachedEmpresaCollectionNew;
            municipio.setEmpresaCollection(empresaCollectionNew);
            municipio = em.merge(municipio);
            for (Empresa empresaCollectionOldEmpresa : empresaCollectionOld) {
                if (!empresaCollectionNew.contains(empresaCollectionOldEmpresa)) {
                    empresaCollectionOldEmpresa.setMunicipioId(null);
                    empresaCollectionOldEmpresa = em.merge(empresaCollectionOldEmpresa);
                }
            }
            for (Empresa empresaCollectionNewEmpresa : empresaCollectionNew) {
                if (!empresaCollectionOld.contains(empresaCollectionNewEmpresa)) {
                    Municipio oldMunicipioIdOfEmpresaCollectionNewEmpresa = empresaCollectionNewEmpresa.getMunicipioId();
                    empresaCollectionNewEmpresa.setMunicipioId(municipio);
                    empresaCollectionNewEmpresa = em.merge(empresaCollectionNewEmpresa);
                    if (oldMunicipioIdOfEmpresaCollectionNewEmpresa != null && !oldMunicipioIdOfEmpresaCollectionNewEmpresa.equals(municipio)) {
                        oldMunicipioIdOfEmpresaCollectionNewEmpresa.getEmpresaCollection().remove(empresaCollectionNewEmpresa);
                        oldMunicipioIdOfEmpresaCollectionNewEmpresa = em.merge(oldMunicipioIdOfEmpresaCollectionNewEmpresa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getId();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            Collection<Empresa> empresaCollection = municipio.getEmpresaCollection();
            for (Empresa empresaCollectionEmpresa : empresaCollection) {
                empresaCollectionEmpresa.setMunicipioId(null);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
