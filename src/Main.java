import java.util.Scanner;

public class Main {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        PhoneBook phoneBook = new PhoneBook();
        System.out.println("");
        System.out.println("===========================");
        System.out.println("PHONE BOOK");
        System.out.println("===========================");
        System.out.println("");
        System.out.println("Type a command or 'exit' to quit:");
        System.out.println("---------------------------");

        listCommands();

        String line;

        do {
            System.out.print("please enter your selection > ");
            line = input.nextLine().trim();
            switch (line) {
                case "add" -> phoneBook.addContact();
                case "delete" -> phoneBook.deleteContact();
                case "contact list" -> phoneBook.printContactsList();
                case "find" -> phoneBook.findContactByNameAndPrint();
                case "sort by name" -> phoneBook.sortByNameAndPrintContacts();
                case "sort by phone" -> phoneBook.sortByPhoneAndPrintContacts();
                case "reverse" -> phoneBook.revertContactsAndPrint();
                case "distinct" -> phoneBook.distinctContacts();
                case "save" -> phoneBook.saveContactListToNewFile();
                case "import" -> phoneBook.initContacts();
                case "help" -> listCommands();
                default -> System.out.println("Invalid command!");
            }
        } while (!line.equals("exit"));

        System.out.println("'Phone Book' is terminated.");
    }

    /**
     * Print menu
     */
    private static void listCommands() {
        System.out.println("1. add ------------ saves a new contact entry into the contact list");
        System.out.println("2. delete --------- removes a contact from the contact list");
        System.out.println("3. contact list --- see all of the contacts");
        System.out.println("4. find ----------- finds a contact by name");
        System.out.println("5. sort by name --- sorts the contact list by name");
        System.out.println("6. sort by phone -- sorts the contact list by phone");
        System.out.println("7. revers --------- reverses the contact list");
        System.out.println("8. distinct ------- removes duplicates from the contact list");
        System.out.println("9. save ----------- saves the contact list");
        System.out.println("10. import -------- imports the contact list");
        System.out.println("");
        System.out.println("11. help ---------- lists all valid commands");
        System.out.println("---------------------------");
        System.out.println();
    }
}
