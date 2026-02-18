package model;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private double balance; // Hesap bakiyesi
    private List<Transaction> transactions; // İşlem geçmişi

    public Account(double balance){
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public double getBalance(){
        return balance;
    }

    // Para yatırma işlemi
    public void deposit(double amount){
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    // Para çekme işlemi
    public boolean withdraw(double amount){
        if(amount > balance) {
            return false; // Yetersiz bakiye
        }
        balance -= amount;
        transactions.add(new Transaction("Withdraw", amount));
        return true;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }
}
