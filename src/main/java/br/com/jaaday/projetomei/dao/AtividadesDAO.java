/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao;

import br.com.jaaday.projetomei.dao.exceptions.NonexistentEntityException;
import br.com.jaaday.projetomei.dao.exceptions.PreexistingEntityException;
import br.com.jaaday.projetomei.modelo.Atividades;
import br.com.jaaday.projetomei.modelo.AtividadesPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.jaaday.projetomei.modelo.Cnae;
import br.com.jaaday.projetomei.modelo.Empresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class AtividadesDAO implements Serializable {

    public AtividadesDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividades atividades) throws PreexistingEntityException, Exception {
        if (atividades.getAtividadesPK() == null) {
            atividades.setAtividadesPK(new AtividadesPK());
        }
        atividades.getAtividadesPK().setCnaeId(atividades.getCnae().getId());
        atividades.getAtividadesPK().setEmpresaId(atividades.getEmpresa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cnae cnae = atividades.getCnae();
            if (cnae != null) {
                cnae = em.getReference(cnae.getClass(), cnae.getId());
                atividades.setCnae(cnae);
            }
            Empresa empresa = atividades.getEmpresa();
            if (empresa != null) {
                empresa = em.getReference(empresa.getClass(), empresa.getId());
                atividades.setEmpresa(empresa);
            }
            em.persist(atividades);
            if (cnae != null) {
                cnae.getAtividadesCollection().add(atividades);
                cnae = em.merge(cnae);
            }
            if (empresa != null) {
                empresa.getAtividadesCollection().add(atividades);
                empresa = em.merge(empresa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAtividades(atividades.getAtividadesPK()) != null) {
                throw new PreexistingEntityException("Atividades " + atividades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividades atividades) throws NonexistentEntityException, Exception {
        atividades.getAtividadesPK().setCnaeId(atividades.getCnae().getId());
        atividades.getAtividadesPK().setEmpresaId(atividades.getEmpresa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividades persistentAtividades = em.find(Atividades.class, atividades.getAtividadesPK());
            Cnae cnaeOld = persistentAtividades.getCnae();
            Cnae cnaeNew = atividades.getCnae();
            Empresa empresaOld = persistentAtividades.getEmpresa();
            Empresa empresaNew = atividades.getEmpresa();
            if (cnaeNew != null) {
                cnaeNew = em.getReference(cnaeNew.getClass(), cnaeNew.getId());
                atividades.setCnae(cnaeNew);
            }
            if (empresaNew != null) {
                empresaNew = em.getReference(empresaNew.getClass(), empresaNew.getId());
                atividades.setEmpresa(empresaNew);
            }
            atividades = em.merge(atividades);
            if (cnaeOld != null && !cnaeOld.equals(cnaeNew)) {
                cnaeOld.getAtividadesCollection().remove(atividades);
                cnaeOld = em.merge(cnaeOld);
            }
            if (cnaeNew != null && !cnaeNew.equals(cnaeOld)) {
                cnaeNew.getAtividadesCollection().add(atividades);
                cnaeNew = em.merge(cnaeNew);
            }
            if (empresaOld != null && !empresaOld.equals(empresaNew)) {
                empresaOld.getAtividadesCollection().remove(atividades);
                empresaOld = em.merge(empresaOld);
            }
            if (empresaNew != null && !empresaNew.equals(empresaOld)) {
                empresaNew.getAtividadesCollection().add(atividades);
                empresaNew = em.merge(empresaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AtividadesPK id = atividades.getAtividadesPK();
                if (findAtividades(id) == null) {
                    throw new NonexistentEntityException("The atividades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AtividadesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividades atividades;
            try {
                atividades = em.getReference(Atividades.class, id);
                atividades.getAtividadesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividades with id " + id + " no longer exists.", enfe);
            }
            Cnae cnae = atividades.getCnae();
            if (cnae != null) {
                cnae.getAtividadesCollection().remove(atividades);
                cnae = em.merge(cnae);
            }
            Empresa empresa = atividades.getEmpresa();
            if (empresa != null) {
                empresa.getAtividadesCollection().remove(atividades);
                empresa = em.merge(empresa);
            }
            em.remove(atividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividades> findAtividadesEntities() {
        return findAtividadesEntities(true, -1, -1);
    }

    public List<Atividades> findAtividadesEntities(int maxResults, int firstResult) {
        return findAtividadesEntities(false, maxResults, firstResult);
    }

    private List<Atividades> findAtividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividades.class));
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

    public Atividades findAtividades(AtividadesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividades> rt = cq.from(Atividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
