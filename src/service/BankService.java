package service;

import model.Account;
import model.Transaction;
import model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BankService {
    private FileService fileService = new FileService();
    private Map<String, User> users = new HashMap<>(); // Tüm kullanıcılar
    private User currentUser = null; // Giriş yapmış kullanıcı

    public BankService() {
        users = fileService.loadUsers(); // Kullanıcıları dosyadan yükle
    }

    // Yeni kullanıcı kaydı
    public String register(String name, String password, String email, String phoneNumber, String address, String birthDateStr) {
        String id = UUID.randomUUID().toString().substring(0, 8); // Benzersiz ID oluştur
        if (users.containsKey(id)) {
            return null; // ID çakışması
        }
        Account account = new Account(0); // Başlangıç bakiyesi 0
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr);
        } catch (Exception e) {
            System.out.println("Geçersiz doğum tarihi formatı. Lütfen YYYY-MM-DD formatında giriniz.");
            return null;
        }
        User user = new User(id, name, password, email, phoneNumber, address, birthDate, account);
        users.put(id, user);
        fileService.saveUsers(users);
        return id;
    }

    // Kullanıcı girişi
    public boolean login(String id, String password) {
        User user = users.get(id);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    // Kullanıcı çıkışı
    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Para yatırma
    public boolean deposit(double amount) {
        if (currentUser == null) return false;
        if (amount <= 0) return false;
        currentUser.getAccount().deposit(amount);
        fileService.saveUsers(users);
        return true;
    }

    // Para çekme
    public boolean withdraw(double amount) {
        if (currentUser == null) return false;
        if (amount <= 0) return false;
        fileService.saveUsers(users);
        return currentUser.getAccount().withdraw(amount);
    }

    // Bakiye görüntüleme
    public double getBalance() {
        if (currentUser == null) return 0.0;
        return currentUser.getAccount().getBalance();
    }

    // İşlem geçmişi
    public List<Transaction> getTransactionHistory() {
        if (currentUser == null) return null;
        return currentUser.getAccount().getTransactions();
    }

    // Para transferi
    public boolean moneyTransfer(String recipidientId, double amount) {
        if (currentUser == null) return false;
        if (amount <= 0) return false;

        User recipient = users.get(recipidientId);
        if (recipient == null) return false; // Alıcı bulunamadı

        if (currentUser.getAccount().withdraw(amount)) {
            recipient.getAccount().deposit(amount);
            fileService.saveUsers(users);
            return true;
        }
        return false; // Yetersiz bakiye
    }
}