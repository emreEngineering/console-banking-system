package service;

import model.Account;
import model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FileService {
    private static final String FILE_PATH = "data/users.csv"; // Kullanıcı verilerinin saklandığı dosya

    // Kullanıcıları CSV dosyasından yükle
    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return users; // Dosya yoksa boş liste döndür
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String parts[] = line.split(";");
                if (parts.length == 8) { // Doğru format kontrolü
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String password = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim().replace(",",".")); // Virgülü nokta ile değiştir
                    String email = parts[4].trim();
                    String phoneNumber = parts[5].trim();
                    String address = parts[6].trim();
                    String birthDateStr = parts[7].trim();
                    LocalDate birthDate = LocalDate.parse(birthDateStr);
                    User user = new User(id, name, password, email, phoneNumber, address, birthDate, new Account(balance));
                    users.put(id, user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Kullanıcıları CSV dosyasına kaydet
    public void saveUsers(Map<String, User> users) {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users.values()) {
                String line = String.format("%s;%s;%s;%.2f;%s;%s;%s;%s",
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        user.getAccount().getBalance(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getAddress(),
                        user.getBirthDate().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
