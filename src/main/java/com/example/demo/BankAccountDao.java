package com.example.demo;


import org.springframework.stereotype.Repository;
import jakarta.persistence.*;
import java.util.List;

@Repository
public class BankAccountDao {/*dao= data access object */
    @PersistenceContext
    private EntityManager em;/*acts like a bridge between java entites and the database */

    public void save(BankAccount account) {
        em.persist(account);
    }

    public BankAccount findById(Long id) {
        return em.find(BankAccount.class, id);
    }

    public List<BankAccount> findByOwner(String owner) {
        return em.createQuery(
            "SELECT b FROM BankAccount b WHERE b.owner = :owner",
            BankAccount.class)
            .setParameter("owner", owner)/*cette methode utiliser pour eviter sql injection vuernability */
            .getResultList();
    }

    public List<BankAccount> findAll() {
        return em.createQuery(
            "SELECT b FROM BankAccount b",
            BankAccount.class)
            .getResultList();
    }

    public void delete(BankAccount account) {
        em.remove(em.merge(account));
    }
    
}
