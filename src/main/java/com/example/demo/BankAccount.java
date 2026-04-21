package com.example.demo;
import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {/*bankaccount joue le role d'un tableau dans la bd */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;
    private double balance;

    public BankAccount() {}

    public BankAccount(String owner, double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Solde initial négatif !");
        this.owner = owner;
        this.balance = balance;
    }

    public Long getId() {
         return id; 
    }
    public String getOwner() { 
        return owner;
    }
    public double getBalance() { 
        return balance; 
    }
    public void setBalance(double balance) { 
        this.balance = balance; 
    }
    
    
}
