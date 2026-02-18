package model;

import java.time.LocalDateTime;

public class Transaction {
    private String type; // İşlem türü (Deposit/Withdraw)
    private double amount; // İşlem tutarı
    private LocalDateTime date; // İşlem tarihi

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now(); // Şu anki tarih
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
