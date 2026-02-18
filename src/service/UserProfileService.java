package service;

import model.User;
import ui.Menu;
import java.time.LocalDate;
import java.util.Map;

public class UserProfileService {
    private FileService fileService;
    private BankService bankService;

    public UserProfileService() {
        this.fileService = new FileService();
        this.bankService = Menu.bankService; // Menu'daki BankService örneğini kullan
    }

    // İsim güncelleme
    public boolean updateName(String name) {
        if (bankService.getCurrentUser() == null) return false;
        if (name == null || name.trim().isEmpty()) return false;
        
        bankService.getCurrentUser().setName(name.trim());
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Email güncelleme
    public boolean updateEmail(String email) {
        if (bankService.getCurrentUser() == null) return false;
        if (email == null || email.trim().isEmpty()) return false;
        
        bankService.getCurrentUser().setEmail(email.trim());
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Telefon numarası güncelleme
    public boolean updatePhoneNumber(String phoneNumber) {
        if (bankService.getCurrentUser() == null) return false;
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) return false;
        
        bankService.getCurrentUser().setPhoneNumber(phoneNumber.trim());
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Adres güncelleme
    public boolean updateAddress(String address) {
        if (bankService.getCurrentUser() == null) return false;
        if (address == null || address.trim().isEmpty()) return false;
        
        bankService.getCurrentUser().setAddress(address.trim());
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Doğum tarihi güncelleme
    public boolean updateBirthDate(LocalDate birthDate) {
        if (bankService.getCurrentUser() == null) return false;
        if (birthDate == null) return false;
        
        bankService.getCurrentUser().setBirthDate(birthDate);
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Şifre güncelleme
    public boolean updatePassword(String oldPassword, String newPassword) {
        if (bankService.getCurrentUser() == null) return false;
        if (newPassword == null || newPassword.trim().isEmpty()) return false;
        if (!bankService.getCurrentUser().getPassword().equals(oldPassword)) return false;

        bankService.getCurrentUser().setPassword(newPassword.trim());
        Map<String, User> users = fileService.loadUsers();
        users.put(bankService.getCurrentUser().getId(), bankService.getCurrentUser());
        fileService.saveUsers(users);
        return true;
    }

    // Hesap silme
    public boolean deleteAccount(String password) {
        if (bankService.getCurrentUser() == null) return false;
        if (!bankService.getCurrentUser().getPassword().equals(password)) return false;

        Map<String, User> users = fileService.loadUsers();
        users.remove(bankService.getCurrentUser().getId());
        fileService.saveUsers(users);
        bankService.logout();
        return true;
    }

    public User getCurrentUser() {
        return bankService.getCurrentUser();
    }
}
