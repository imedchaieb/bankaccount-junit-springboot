package com.example.demo;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BankAccountServiceTest {

    @Autowired
    BankAccountService service;

    @Test
    void createAccount_should_work() {
        BankAccount account = service.createAccount("Imed", 1000.0);

        assertNotNull(account.getId());
        assertEquals("Imed", account.getOwner());
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    void createAccount_negative_balance_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
            service.createAccount("Imed", -500.0)
        );
    }

    @Test
    void deposit_should_increase_balance() {
        BankAccount account = service.createAccount("Imed", 1000.0);
        service.deposit(account.getId(), 500.0);
        assertEquals(1500.0, service.getBalance(account.getId()));
    }

    @Test
    void deposit_negative_amount_should_fail() {
        BankAccount account = service.createAccount("Imed", 1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            service.deposit(account.getId(), -100.0)
        );
    }

    @Test
    void withdraw_should_decrease_balance() {
        BankAccount account = service.createAccount("Imed", 1000.0);
        service.withdraw(account.getId(), 300.0);
        assertEquals(700.0, service.getBalance(account.getId()));
    }

    @Test
    void withdraw_insufficient_balance_should_fail() {
        BankAccount account = service.createAccount("Imed", 1000.0);
        assertThrows(IllegalStateException.class, () ->
            service.withdraw(account.getId(), 9999.0)
        );
    }

    @Test
    void transfer_should_move_money_between_accounts() {
        BankAccount imed = service.createAccount("Imed", 1000.0);
        BankAccount ali  = service.createAccount("Ali", 500.0);

        service.transfer(imed.getId(), ali.getId(), 200.0);

        assertEquals(800.0, service.getBalance(imed.getId()));
        assertEquals(700.0, service.getBalance(ali.getId()));
    }
}