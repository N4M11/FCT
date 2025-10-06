package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.data.model.MyTable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Objectdb {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:devices.odb");
        EntityManager em = emf.createEntityManager();

        MyTable mt;
        Date date = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse("17/01/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mt = new MyTable(
                "Juan Pérez",
                date,
                "Calle Principal 123",
                true
        );
        
        try {

            em.getTransaction().begin();
            em.persist(mt);
            em.getTransaction().commit();

            TypedQuery<MyTable> query = em.createQuery("SELECT d FROM MyTable d", MyTable.class);
            List<MyTable> mts = query.getResultList();

            System.out.println("OBJECTDB DEVICES:");
            for (MyTable myTable : mts) {
                System.out.println("---------------"
                        + "\n\tID: " + myTable.getId()
                        + ",\n\tName: " + myTable.getName()
                        + ",\n\tAddress: " + myTable.getAddress()
                        + ",\n\tBirthdate: " + myTable.getBirthdate()
                        + "---------------");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            em.close();
            emf.close();

        }
    }
}
