package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountDao dao;

    public BankAccount createAccount(String owner, double initialBalance) {
        BankAccount account = new BankAccount(owner, initialBalance);
        dao.save(account);
        return account;/*on fait retourner l'objet pour tester son creation */
    }

    public void deposit(Long accountId, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Montant invalide !");

        BankAccount account = dao.findById(accountId);
        if (account == null)
            throw new IllegalArgumentException("Compte introuvable !");

        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(Long accountId, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Montant invalide !");

        BankAccount account = dao.findById(accountId);
        if (account == null)
            throw new IllegalArgumentException("Compte introuvable !");

        if (amount > account.getBalance())
            throw new IllegalStateException("Solde insuffisant !");

        account.setBalance(account.getBalance() - amount);
    }

    public void transfer(Long fromId, Long toId, double amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    public double getBalance(Long accountId) {
        BankAccount account = dao.findById(accountId);
        if (account == null)
            throw new IllegalArgumentException("Compte introuvable !");
        return account.getBalance();
    }

    public List<BankAccount> getAllAccounts() {
        return dao.findAll();
    
    
}
}
