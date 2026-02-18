import ui.Menu;
import static ui.Menu.bankService;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        // Ana program döngüsü
        while (true) {
            if (bankService.getCurrentUser() == null) {
                menu.showWelcomeMenu(); // Giriş yapmamış kullanıcı için hoş geldin menüsü
            } else {
                menu.showUserMenu(); // Giriş yapmış kullanıcı için ana menü
            }
        }
    }
}
