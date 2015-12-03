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
import br.com.jaaday.projetomei.modelo.FormaAtuacao;
import br.com.jaaday.projetomei.modelo.TipoUnidade;
import br.com.jaaday.projetomei.modelo.UnidadeForma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class UnidadeFormaDAO implements Serializable {

    public UnidadeFormaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadeForma unidadeForma) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresaId = unidadeForma.getEmpresaId();
            if (empresaId != null) {
                empresaId = em.getReference(empresaId.getClass(), empresaId.getId());
                unidadeForma.setEmpresaId(empresaId);
            }
            FormaAtuacao formaAtuacaoId = unidadeForma.getFormaAtuacaoId();
            if (formaAtuacaoId != null) {
                formaAtuacaoId = em.getReference(formaAtuacaoId.getClass(), formaAtuacaoId.getId());
                unidadeForma.setFormaAtuacaoId(formaAtuacaoId);
            }
            TipoUnidade tipoUnidadeId = unidadeForma.getTipoUnidadeId();
            if (tipoUnidadeId != null) {
                tipoUnidadeId = em.getReference(tipoUnidadeId.getClass(), tipoUnidadeId.getId());
                unidadeForma.setTipoUnidadeId(tipoUnidadeId);
            }
            em.persist(unidadeForma);
            if (empresaId != null) {
                empresaId.getUnidadeFormaCollection().add(unidadeForma);
                empresaId = em.merge(empresaId);
            }
            if (formaAtuacaoId != null) {
                formaAtuacaoId.getUnidadeFormaCollection().add(unidadeForma);
                formaAtuacaoId = em.merge(formaAtuacaoId);
            }
            if (tipoUnidadeId != null) {
                tipoUnidadeId.getUnidadeFormaCollection().add(unidadeForma);
                tipoUnidadeId = em.merge(tipoUnidadeId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadeForma unidadeForma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadeForma persistentUnidadeForma = em.find(UnidadeForma.class, unidadeForma.getId());
            Empresa empresaIdOld = persistentUnidadeForma.getEmpresaId();
            Empresa empresaIdNew = unidadeForma.getEmpresaId();
            FormaAtuacao formaAtuacaoIdOld = persistentUnidadeForma.getFormaAtuacaoId();
            FormaAtuacao formaAtuacaoIdNew = unidadeForma.getFormaAtuacaoId();
            TipoUnidade tipoUnidadeIdOld = persistentUnidadeForma.getTipoUnidadeId();
            TipoUnidade tipoUnidadeIdNew = unidadeForma.getTipoUnidadeId();
            if (empresaIdNew != null) {
                empresaIdNew = em.getReference(empresaIdNew.getClass(), empresaIdNew.getId());
                unidadeForma.setEmpresaId(empresaIdNew);
            }
            if (formaAtuacaoIdNew != null) {
                formaAtuacaoIdNew = em.getReference(formaAtuacaoIdNew.getClass(), formaAtuacaoIdNew.getId());
                unidadeForma.setFormaAtuacaoId(formaAtuacaoIdNew);
            }
            if (tipoUnidadeIdNew != null) {
                tipoUnidadeIdNew = em.getReference(tipoUnidadeIdNew.getClass(), tipoUnidadeIdNew.getId());
                unidadeForma.setTipoUnidadeId(tipoUnidadeIdNew);
            }
            unidadeForma = em.merge(unidadeForma);
            if (empresaIdOld != null && !empresaIdOld.equals(empresaIdNew)) {
                empresaIdOld.getUnidadeFormaCollection().remove(unidadeForma);
                empresaIdOld = em.merge(empresaIdOld);
            }
            if (empresaIdNew != null && !empresaIdNew.equals(empresaIdOld)) {
                empresaIdNew.getUnidadeFormaCollection().add(unidadeForma);
                empresaIdNew = em.merge(empresaIdNew);
            }
            if (formaAtuacaoIdOld != null && !formaAtuacaoIdOld.equals(formaAtuacaoIdNew)) {
                formaAtuacaoIdOld.getUnidadeFormaCollection().remove(unidadeForma);
                formaAtuacaoIdOld = em.merge(formaAtuacaoIdOld);
            }
            if (formaAtuacaoIdNew != null && !formaAtuacaoIdNew.equals(formaAtuacaoIdOld)) {
                formaAtuacaoIdNew.getUnidadeFormaCollection().add(unidadeForma);
                formaAtuacaoIdNew = em.merge(formaAtuacaoIdNew);
            }
            if (tipoUnidadeIdOld != null && !tipoUnidadeIdOld.equals(tipoUnidadeIdNew)) {
                tipoUnidadeIdOld.getUnidadeFormaCollection().remove(unidadeForma);
                tipoUnidadeIdOld = em.merge(tipoUnidadeIdOld);
            }
            if (tipoUnidadeIdNew != null && !tipoUnidadeIdNew.equals(tipoUnidadeIdOld)) {
                tipoUnidadeIdNew.getUnidadeFormaCollection().add(unidadeForma);
                tipoUnidadeIdNew = em.merge(tipoUnidadeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadeForma.getId();
                if (findUnidadeForma(id) == null) {
                    throw new NonexistentEntityException("The unidadeForma with id " + id + " no longer exists.");
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
            UnidadeForma unidadeForma;
            try {
                unidadeForma = em.getReference(UnidadeForma.class, id);
                unidadeForma.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadeForma with id " + id + " no longer exists.", enfe);
            }
            Empresa empresaId = unidadeForma.getEmpresaId();
            if (empresaId != null) {
                empresaId.getUnidadeFormaCollection().remove(unidadeForma);
                empresaId = em.merge(empresaId);
            }
            FormaAtuacao formaAtuacaoId = unidadeForma.getFormaAtuacaoId();
            if (formaAtuacaoId != null) {
                formaAtuacaoId.getUnidadeFormaCollection().remove(unidadeForma);
                formaAtuacaoId = em.merge(formaAtuacaoId);
            }
            TipoUnidade tipoUnidadeId = unidadeForma.getTipoUnidadeId();
            if (tipoUnidadeId != null) {
                tipoUnidadeId.getUnidadeFormaCollection().remove(unidadeForma);
                tipoUnidadeId = em.merge(tipoUnidadeId);
            }
            em.remove(unidadeForma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadeForma> findUnidadeFormaEntities() {
        return findUnidadeFormaEntities(true, -1, -1);
    }

    public List<UnidadeForma> findUnidadeFormaEntities(int maxResults, int firstResult) {
        return findUnidadeFormaEntities(false, maxResults, firstResult);
    }

    private List<UnidadeForma> findUnidadeFormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadeForma.class));
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

    public UnidadeForma findUnidadeForma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadeForma.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadeFormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadeForma> rt = cq.from(UnidadeForma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
