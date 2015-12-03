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
import br.com.jaaday.projetomei.modelo.Empresario;
import br.com.jaaday.projetomei.modelo.Municipio;
import br.com.jaaday.projetomei.modelo.Atividades;
import br.com.jaaday.projetomei.modelo.Empresa;
import java.util.ArrayList;
import java.util.Collection;
import br.com.jaaday.projetomei.modelo.UnidadeForma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class EmpresaDAO implements Serializable {

    public EmpresaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getAtividadesCollection() == null) {
            empresa.setAtividadesCollection(new ArrayList<Atividades>());
        }
        if (empresa.getUnidadeFormaCollection() == null) {
            empresa.setUnidadeFormaCollection(new ArrayList<UnidadeForma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresario empresarioId = empresa.getEmpresarioId();
            if (empresarioId != null) {
                empresarioId = em.getReference(empresarioId.getClass(), empresarioId.getId());
                empresa.setEmpresarioId(empresarioId);
            }
            Municipio municipioId = empresa.getMunicipioId();
            if (municipioId != null) {
                municipioId = em.getReference(municipioId.getClass(), municipioId.getId());
                empresa.setMunicipioId(municipioId);
            }
            Collection<Atividades> attachedAtividadesCollection = new ArrayList<Atividades>();
            for (Atividades atividadesCollectionAtividadesToAttach : empresa.getAtividadesCollection()) {
                atividadesCollectionAtividadesToAttach = em.getReference(atividadesCollectionAtividadesToAttach.getClass(), atividadesCollectionAtividadesToAttach.getAtividadesPK());
                attachedAtividadesCollection.add(atividadesCollectionAtividadesToAttach);
            }
            empresa.setAtividadesCollection(attachedAtividadesCollection);
            Collection<UnidadeForma> attachedUnidadeFormaCollection = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionUnidadeFormaToAttach : empresa.getUnidadeFormaCollection()) {
                unidadeFormaCollectionUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionUnidadeFormaToAttach.getClass(), unidadeFormaCollectionUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollection.add(unidadeFormaCollectionUnidadeFormaToAttach);
            }
            empresa.setUnidadeFormaCollection(attachedUnidadeFormaCollection);
            em.persist(empresa);
            if (empresarioId != null) {
                empresarioId.getEmpresaCollection().add(empresa);
                empresarioId = em.merge(empresarioId);
            }
            if (municipioId != null) {
                municipioId.getEmpresaCollection().add(empresa);
                municipioId = em.merge(municipioId);
            }
            for (Atividades atividadesCollectionAtividades : empresa.getAtividadesCollection()) {
                Empresa oldEmpresaOfAtividadesCollectionAtividades = atividadesCollectionAtividades.getEmpresa();
                atividadesCollectionAtividades.setEmpresa(empresa);
                atividadesCollectionAtividades = em.merge(atividadesCollectionAtividades);
                if (oldEmpresaOfAtividadesCollectionAtividades != null) {
                    oldEmpresaOfAtividadesCollectionAtividades.getAtividadesCollection().remove(atividadesCollectionAtividades);
                    oldEmpresaOfAtividadesCollectionAtividades = em.merge(oldEmpresaOfAtividadesCollectionAtividades);
                }
            }
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : empresa.getUnidadeFormaCollection()) {
                Empresa oldEmpresaIdOfUnidadeFormaCollectionUnidadeForma = unidadeFormaCollectionUnidadeForma.getEmpresaId();
                unidadeFormaCollectionUnidadeForma.setEmpresaId(empresa);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
                if (oldEmpresaIdOfUnidadeFormaCollectionUnidadeForma != null) {
                    oldEmpresaIdOfUnidadeFormaCollectionUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionUnidadeForma);
                    oldEmpresaIdOfUnidadeFormaCollectionUnidadeForma = em.merge(oldEmpresaIdOfUnidadeFormaCollectionUnidadeForma);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            Empresario empresarioIdOld = persistentEmpresa.getEmpresarioId();
            Empresario empresarioIdNew = empresa.getEmpresarioId();
            Municipio municipioIdOld = persistentEmpresa.getMunicipioId();
            Municipio municipioIdNew = empresa.getMunicipioId();
            Collection<Atividades> atividadesCollectionOld = persistentEmpresa.getAtividadesCollection();
            Collection<Atividades> atividadesCollectionNew = empresa.getAtividadesCollection();
            Collection<UnidadeForma> unidadeFormaCollectionOld = persistentEmpresa.getUnidadeFormaCollection();
            Collection<UnidadeForma> unidadeFormaCollectionNew = empresa.getUnidadeFormaCollection();
            List<String> illegalOrphanMessages = null;
            for (Atividades atividadesCollectionOldAtividades : atividadesCollectionOld) {
                if (!atividadesCollectionNew.contains(atividadesCollectionOldAtividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atividades " + atividadesCollectionOldAtividades + " since its empresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresarioIdNew != null) {
                empresarioIdNew = em.getReference(empresarioIdNew.getClass(), empresarioIdNew.getId());
                empresa.setEmpresarioId(empresarioIdNew);
            }
            if (municipioIdNew != null) {
                municipioIdNew = em.getReference(municipioIdNew.getClass(), municipioIdNew.getId());
                empresa.setMunicipioId(municipioIdNew);
            }
            Collection<Atividades> attachedAtividadesCollectionNew = new ArrayList<Atividades>();
            for (Atividades atividadesCollectionNewAtividadesToAttach : atividadesCollectionNew) {
                atividadesCollectionNewAtividadesToAttach = em.getReference(atividadesCollectionNewAtividadesToAttach.getClass(), atividadesCollectionNewAtividadesToAttach.getAtividadesPK());
                attachedAtividadesCollectionNew.add(atividadesCollectionNewAtividadesToAttach);
            }
            atividadesCollectionNew = attachedAtividadesCollectionNew;
            empresa.setAtividadesCollection(atividadesCollectionNew);
            Collection<UnidadeForma> attachedUnidadeFormaCollectionNew = new ArrayList<UnidadeForma>();
            for (UnidadeForma unidadeFormaCollectionNewUnidadeFormaToAttach : unidadeFormaCollectionNew) {
                unidadeFormaCollectionNewUnidadeFormaToAttach = em.getReference(unidadeFormaCollectionNewUnidadeFormaToAttach.getClass(), unidadeFormaCollectionNewUnidadeFormaToAttach.getId());
                attachedUnidadeFormaCollectionNew.add(unidadeFormaCollectionNewUnidadeFormaToAttach);
            }
            unidadeFormaCollectionNew = attachedUnidadeFormaCollectionNew;
            empresa.setUnidadeFormaCollection(unidadeFormaCollectionNew);
            empresa = em.merge(empresa);
            if (empresarioIdOld != null && !empresarioIdOld.equals(empresarioIdNew)) {
                empresarioIdOld.getEmpresaCollection().remove(empresa);
                empresarioIdOld = em.merge(empresarioIdOld);
            }
            if (empresarioIdNew != null && !empresarioIdNew.equals(empresarioIdOld)) {
                empresarioIdNew.getEmpresaCollection().add(empresa);
                empresarioIdNew = em.merge(empresarioIdNew);
            }
            if (municipioIdOld != null && !municipioIdOld.equals(municipioIdNew)) {
                municipioIdOld.getEmpresaCollection().remove(empresa);
                municipioIdOld = em.merge(municipioIdOld);
            }
            if (municipioIdNew != null && !municipioIdNew.equals(municipioIdOld)) {
                municipioIdNew.getEmpresaCollection().add(empresa);
                municipioIdNew = em.merge(municipioIdNew);
            }
            for (Atividades atividadesCollectionNewAtividades : atividadesCollectionNew) {
                if (!atividadesCollectionOld.contains(atividadesCollectionNewAtividades)) {
                    Empresa oldEmpresaOfAtividadesCollectionNewAtividades = atividadesCollectionNewAtividades.getEmpresa();
                    atividadesCollectionNewAtividades.setEmpresa(empresa);
                    atividadesCollectionNewAtividades = em.merge(atividadesCollectionNewAtividades);
                    if (oldEmpresaOfAtividadesCollectionNewAtividades != null && !oldEmpresaOfAtividadesCollectionNewAtividades.equals(empresa)) {
                        oldEmpresaOfAtividadesCollectionNewAtividades.getAtividadesCollection().remove(atividadesCollectionNewAtividades);
                        oldEmpresaOfAtividadesCollectionNewAtividades = em.merge(oldEmpresaOfAtividadesCollectionNewAtividades);
                    }
                }
            }
            for (UnidadeForma unidadeFormaCollectionOldUnidadeForma : unidadeFormaCollectionOld) {
                if (!unidadeFormaCollectionNew.contains(unidadeFormaCollectionOldUnidadeForma)) {
                    unidadeFormaCollectionOldUnidadeForma.setEmpresaId(null);
                    unidadeFormaCollectionOldUnidadeForma = em.merge(unidadeFormaCollectionOldUnidadeForma);
                }
            }
            for (UnidadeForma unidadeFormaCollectionNewUnidadeForma : unidadeFormaCollectionNew) {
                if (!unidadeFormaCollectionOld.contains(unidadeFormaCollectionNewUnidadeForma)) {
                    Empresa oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma = unidadeFormaCollectionNewUnidadeForma.getEmpresaId();
                    unidadeFormaCollectionNewUnidadeForma.setEmpresaId(empresa);
                    unidadeFormaCollectionNewUnidadeForma = em.merge(unidadeFormaCollectionNewUnidadeForma);
                    if (oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma != null && !oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma.equals(empresa)) {
                        oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma.getUnidadeFormaCollection().remove(unidadeFormaCollectionNewUnidadeForma);
                        oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma = em.merge(oldEmpresaIdOfUnidadeFormaCollectionNewUnidadeForma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Atividades> atividadesCollectionOrphanCheck = empresa.getAtividadesCollection();
            for (Atividades atividadesCollectionOrphanCheckAtividades : atividadesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Atividades " + atividadesCollectionOrphanCheckAtividades + " in its atividadesCollection field has a non-nullable empresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresario empresarioId = empresa.getEmpresarioId();
            if (empresarioId != null) {
                empresarioId.getEmpresaCollection().remove(empresa);
                empresarioId = em.merge(empresarioId);
            }
            Municipio municipioId = empresa.getMunicipioId();
            if (municipioId != null) {
                municipioId.getEmpresaCollection().remove(empresa);
                municipioId = em.merge(municipioId);
            }
            Collection<UnidadeForma> unidadeFormaCollection = empresa.getUnidadeFormaCollection();
            for (UnidadeForma unidadeFormaCollectionUnidadeForma : unidadeFormaCollection) {
                unidadeFormaCollectionUnidadeForma.setEmpresaId(null);
                unidadeFormaCollectionUnidadeForma = em.merge(unidadeFormaCollectionUnidadeForma);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
