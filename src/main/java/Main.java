import creations.*;

public class Main {
    public static void main(String[] args) {
        CreateDatabaseAndTables.createDatabaseAndTables();
        while (true) {
            try {
                DisplayMenu.runMenu();
            } catch (NullPointerException ex) {
                System.out.println("Please write a correct command");
            } catch (NumberFormatException ex) {
                System.out.println("Please write a correct command");
            }
        }

    }
}
