package com.rgb.training.app.data.repository;

import com.rgb.training.app.data.model.Reparation;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LuisCarlosGonzalez
 */
@Stateless
public class ReparationJTARepository {

    @PersistenceContext(unitName = "testdb")
    private EntityManager entityManager;

    public ReparationJTARepository() {
        
    }
    

    /**
     * Busca un asiento contable por su identificador.
     *
     * @param entryId Identificador del asiento contable
     * @param secHeader
     *
     * @return Un asiento contable(Entity)
     */
    public Reparation get(Integer entryId) {
        Reparation result = null;
        try {
            result = (Reparation) entityManager.createQuery("SELECT r FROM Reparation r LEFT JOIN FETCH r.deviceId WHERE r.reparationId = :entryId")
                    .setParameter("entryId", entryId)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Reparation> getAll() {
        return getAll(null, null);
    }
    
    public List<Reparation> findByDeviceId(Integer deviceId) {
    return entityManager.createQuery(
        "SELECT r FROM Reparation r WHERE r.deviceId.deviceId = :deviceId", 
        Reparation.class)
        .setParameter("deviceId", deviceId)
        .getResultList();
}

    public List<Reparation> getAll(Integer offset, Integer maxResults) {
        List<Reparation> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery("SELECT r FROM Reparation r LEFT JOIN FETCH r.deviceId ORDER BY r.reparationId")
                    .setFirstResult(offset)
                    .setMaxResults(maxResults)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     * Guarda(persist) en la base de datos.
     *
     * @param entry Datos (Entity)
     *
     * @return El registro guardado(Entity)
     */
    public Reparation create(Reparation entry) {
        try {
            entityManager.joinTransaction();
            entityManager.persist(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return entry;
    }

    /**
     * Guarda(merge) en la base de datos.
     *
     * @param entry Datos (Entity)
     *
     * @return El registro guardado(Entity)
     */
    public Reparation update(Reparation entry) {
        try {
            entityManager.joinTransaction();
            entry = entityManager.merge(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    /**
     * Elimina un registro por la entidad manejada.
     *
     * @param entry Datos del asiento contable(Entity)
     *
     * @return El asiento contable eliminado(Entity)
     */
    public Reparation delete(Reparation entry) {
        try {
            entityManager.remove(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    /**
     * Elimina un registro por su identificador
     *
     * @param entryId
     *
     * @return El resultado de la operación(LongEntity).
     */
    public Long delete(Long entryId) {
        Long result = -1L;
        try {
            Reparation reference = entityManager.getReference(Reparation.class, entryId);
            if (reference != null) {
                entityManager.remove(reference);
                result = entryId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @PreDestroy
    public void close() {
        if (this.entityManager != null) {
            this.entityManager.close();
        }
    }
}
