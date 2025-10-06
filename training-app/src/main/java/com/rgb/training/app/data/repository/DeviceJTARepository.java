package com.rgb.training.app.data.repository;

import com.rgb.training.app.data.model.Device;
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
public class DeviceJTARepository {

    @PersistenceContext(unitName = "testdb")
    private EntityManager entityManager;

    public DeviceJTARepository() {
        
    }
    

    /**
     * Busca un asiento contable por su identificador.
     *
     * @param entryId Identificador del asiento contable
     * @param secHeader
     *
     * @return Un asiento contable(Entity)
     */
    public Device get(Integer entryId) {
        Device result = null;
        try {
            result = (Device) entityManager.createQuery("SELECT d FROM Device d WHERE d.deviceId = :entryId")
                    .setParameter("entryId", entryId)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Device> getAll() {
        return getAll(null, null);
    }

    public List<Device> getAll(Integer offset, Integer maxResults) {
        List<Device> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery("SELECT d FROM Device d "
                    + "ORDER BY d.deviceId", Device.class)
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
    public Device create(Device entry) {
        try {
        
        boolean isEntity = entityManager.getMetamodel().getEntities().stream()
                .anyMatch(e -> e.getName().equals(Device.class.getSimpleName()));
        
            entityManager.persist(entry);
            entityManager.flush();
            entityManager.refresh(entry); 
                
            return entityManager.find(Device.class, entry.getDeviceId());  
        } catch (Exception ex) {
            System.err.println("ERROR creating device: " + ex.getMessage());
        ex.printStackTrace();
        throw new RuntimeException("Failed to create device: " + ex.getMessage(), ex);
        }
    }

    /**
     * Guarda(merge) en la base de datos.
     *
     * @param entry Datos (Entity)
     *
     * @return El registro guardado(Entity)
     */
    public Device update(Device entry) {
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
    public Device delete(Device entry) {
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
    public Integer delete(Integer entryId) {
        Integer result = -1;
        try {
            Device reference = entityManager.getReference(Device.class, entryId);
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
