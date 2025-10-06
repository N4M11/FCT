/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Startup
@Singleton

/**
 *
 * @author nami
 */
public class JpaChecker {
     @PersistenceContext(unitName = "testdb")
    private EntityManager em;
    
    @PostConstruct
    public void init() {
        System.out.println("=== Entidades JPA Reconocidas ===");
        em.getMetamodel().getEntities()
          .forEach(e -> System.out.println("- " + e.getName()));
        System.out.println("=================================");
    }
    
}
