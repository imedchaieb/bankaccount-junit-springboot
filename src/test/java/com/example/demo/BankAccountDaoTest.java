package com.example.demo;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BankAccountDaoTest {

    @Autowired
    BankAccountDao dao;

    @Test
    void save_and_findById_should_work() {
        BankAccount account = new BankAccount("Imed", 1000.0);
        dao.save(account);

        BankAccount found = dao.findById(account.getId());

        assertNotNull(found);
        assertEquals("Imed", found.getOwner());
        assertEquals(1000.0, found.getBalance());
    }

    @Test
    void findByOwner_should_return_correct_accounts() {
        dao.save(new BankAccount("Imed", 1000.0));
        dao.save(new BankAccount("Imed", 2000.0));
        dao.save(new BankAccount("Ali",  500.0));

        List<BankAccount> imedAccounts = dao.findByOwner("Imed");

        assertEquals(2, imedAccounts.size());
    }

    @Test
    void delete_should_remove_account() {
        BankAccount account = new BankAccount("Imed", 1000.0);
        dao.save(account);

        dao.delete(account);
        BankAccount found = dao.findById(account.getId());

        assertNull(found);
    }
}