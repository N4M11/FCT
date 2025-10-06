package com.rgb.training.app.data.repository;

import com.rgb.training.app.data.model.Timesheet;
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
public class TimesheetJTARepository {

    @PersistenceContext(unitName = "testdb")
    private EntityManager entityManager;

    public TimesheetJTARepository() {
        
    }

    /**
     * Busca un asiento contable por su identificador.
     *
     * @param entryId Identificador del asiento contable
     * @param secHeader
     *
     * @return Un asiento contable(Entity)
     */
    public Timesheet get(Integer entryId) {
        Timesheet result = null;
        try {
            result = (Timesheet) entityManager.createQuery("SELECT ts FROM Timesheet ts WHERE ts.id = :entryId")
                    .setParameter("entryId", entryId)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Timesheet> getAll() {
        return getAll(null, null);
    }

    public List<Timesheet> getAll(Integer offset, Integer maxResults) {
        List<Timesheet> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery("SELECT ts FROM Timesheet ts "
                    + "ORDER BY ts.id")
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
    public Timesheet create(Timesheet entry) {
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
    public Timesheet update(Timesheet entry) {
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
    public Integer delete(Integer entry) {
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
            Timesheet reference = entityManager.getReference(Timesheet.class, entryId);
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
