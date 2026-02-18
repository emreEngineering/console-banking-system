package ui;

import model.Transaction;
import model.User;
import service.BankService;
import service.UserProfileService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    public static final BankService bankService = new BankService();
    private static final UserProfileService profileService = new UserProfileService();

    // Hoş geldin menüsü
    public static void showWelcomeMenu() {
        System.out.println("=== BANKA SİSTEMİ ===");
        System.out.println("1. Kayıt Ol");
        System.out.println("2. Giriş Yap");
        System.out.println("x. Çıkış");
        System.out.print("Seçim: ");
        String secim = scanner.nextLine();

        switch (secim) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            case "x":
                System.out.println("Çıkılıyor...");
                System.exit(0);
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    // Kullanıcı girişi
    private static void login() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        if (bankService.login(id, password)) {
            User user = bankService.getCurrentUser();
            System.out.println("Hoş geldin, " + user.getName() + "!");
        } else {
            System.out.println("Giriş başarısız.");
        }
    }

    // Kullanıcı kaydı
    private static void register() {
        System.out.print("Ad Soyad: ");
        String name = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        System.out.print("e-posta: ");
        String email = scanner.nextLine();
        System.out.print("Telefon Numarası: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Adres: ");
        String address = scanner.nextLine();
        System.out.print("Doğum Tarihi (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();

        String userId = bankService.register(name, password, email, phoneNumber, address, birthDateStr);
        if (userId != null) {
            System.out.println("Kayıt başarılı. Lütfen giriş için ID'nizi not edin: " + userId);
        } else {
            System.out.println("Kayıt başarısız. Lütfen tekrar deneyin.");
        }
    }

    // Ana kullanıcı menüsü
    public static void showUserMenu() {
        System.out.println("\n=== ANA MENÜ ===");
        System.out.println("1. Bakiye Görüntüle");
        System.out.println("2. Para Yatır");
        System.out.println("3. Para Çek");
        System.out.println("4. İşlem Geçmişi");
        System.out.println("5. Para Transferi");
        System.out.println("6. Profil Güncelle");
        System.out.println("x. Çıkış Yap");
        System.out.print("Seçim: ");
        String secim = scanner.nextLine();

        switch (secim) {
            case "1":
                System.out.println("Bakiyeniz: " + bankService.getBalance() + "₺");
                break;
            case "2":
                System.out.print("Yatırılacak tutar: ");
                double yatir = Double.parseDouble(scanner.nextLine());
                if (bankService.deposit(yatir)) {
                    System.out.println("Para yatırıldı.");
                } else {
                    System.out.println("Hatalı işlem.");
                }
                break;
            case "3":
                System.out.print("Çekilecek tutar: ");
                double cek = Double.parseDouble(scanner.nextLine());
                if (bankService.withdraw(cek)) {
                    System.out.println("Para çekildi.");
                } else {
                    System.out.println("Yetersiz bakiye.");
                }
                break;
            case "4":
                List<Transaction> transactions = bankService.getTransactionHistory();
                if (transactions != null && !transactions.isEmpty()) {
                    System.out.println("=== İşlem Geçmişi ===");
                    for (Transaction t : transactions) {
                        System.out.println(t.getDate() + " - " + t.getType() + " - " + t.getAmount() + "₺");
                    }
                } else {
                    System.out.println("Henüz işlem yok.");
                }
                break;
            case "5":
                System.out.print("Alıcı ID: ");
                String recipientId = scanner.nextLine();
                System.out.print("Gönderilecek tutar: ");
                double amount = Double.parseDouble(scanner.nextLine());
                if (bankService.moneyTransfer(recipientId, amount)) {
                    System.out.println("Para transferi başarılı.");
                } else {
                    System.out.println("Para transferi başarısız. Lütfen tekrar deneyin.");
                }
                break;
            case "6":
                showProfileMenu();
                break;
            case "x":
                bankService.logout();
                System.out.println("Çıkış yapıldı.");
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    // Profil güncelleme menüsü
    public static void showProfileMenu() {
        System.out.println("\n=== PROFİL GÜNCELLEME ===");
        System.out.println("1. İsim Güncelle");
        System.out.println("2. Email Güncelle");
        System.out.println("3. Telefon Numarası Güncelle");
        System.out.println("4. Adres Güncelle");
        System.out.println("5. Doğum Tarihi Güncelle");
        System.out.println("6. Şifre Değiştir");
        System.out.println("7. Hesabı Sil");
        System.out.println("x. Ana Menüye Dön");
        System.out.print("Seçim: ");
        String secim = scanner.nextLine();

        switch (secim) {
            case "1":
                updateName();
                break;
            case "2":
                updateEmail();
                break;
            case "3":
                updatePhoneNumber();
                break;
            case "4":
                updateAddress();
                break;
            case "5":
                updateBirthDate();
                break;
            case "6":
                updatePassword();
                break;
            case "7":
                deleteAccount();
                break;
            case "x":
                return;
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    // Profil güncelleme metodları
    private static void updateName() {
        System.out.print("Yeni isim: ");
        String newName = scanner.nextLine();
        if (profileService.updateName(newName)) {
            System.out.println("İsim başarıyla güncellendi.");
        } else {
            System.out.println("İsim güncellenemedi.");
        }
    }

    private static void updateEmail() {
        System.out.print("Yeni email: ");
        String newEmail = scanner.nextLine();
        if (profileService.updateEmail(newEmail)) {
            System.out.println("Email başarıyla güncellendi.");
        } else {
            System.out.println("Email güncellenemedi.");
        }
    }

    private static void updatePhoneNumber() {
        System.out.print("Yeni telefon numarası: ");
        String newPhone = scanner.nextLine();
        if (profileService.updatePhoneNumber(newPhone)) {
            System.out.println("Telefon numarası başarıyla güncellendi.");
        } else {
            System.out.println("Telefon numarası güncellenemedi.");
        }
    }

    private static void updateAddress() {
        System.out.print("Yeni adres: ");
        String newAddress = scanner.nextLine();
        if (profileService.updateAddress(newAddress)) {
            System.out.println("Adres başarıyla güncellendi.");
        } else {
            System.out.println("Adres güncellenemedi.");
        }
    }

    private static void updateBirthDate() {
        System.out.print("Yeni doğum tarihi (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            if (profileService.updateBirthDate(birthDate)) {
                System.out.println("Doğum tarihi başarıyla güncellendi.");
            } else {
                System.out.println("Doğum tarihi güncellenemedi.");
            }
        } catch (Exception e) {
            System.out.println("Geçersiz tarih formatı.");
        }
    }

    private static void updatePassword() {
        System.out.print("Mevcut şifre: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Yeni şifre: ");
        String newPassword = scanner.nextLine();
        if (profileService.updatePassword(oldPassword, newPassword)) {
            System.out.println("Şifre başarıyla güncellendi.");
        } else {
            System.out.println("Şifre güncellenemedi.");
        }
    }

    private static void deleteAccount() {
        System.out.print("Şifrenizi girin (hesap silme işlemi için): ");
        String password = scanner.nextLine();
        if (profileService.deleteAccount(password)) {
            System.out.println("Hesabınız başarıyla silindi.");
        } else {
            System.out.println("Hesap silinemedi.");
        }
    }
}
