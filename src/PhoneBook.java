import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.reverse;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PhoneBook {
    private List<Contact> contacts = new ArrayList<>();
    Scanner input = new Scanner(System.in);

    /**
     * Returns {@code true} if the string is numeric.
     *
     * @param str the string to check
     * @return {@code true} if this list contains no elements
     */
    private static boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }

    /**
     * Returns boolean check if the string length is big or equals to 1 chars and small or equals to 20
     *
     * @param name the String for check {@code String}
     * @return {@code true} if the string is big or equals to 1 chars and small or equals to 20
     */
    private Boolean nameChecker(String name) {
        return name.length() <= 20 && name.length() >= 1;
    }

    /**
     * Returns boolean check if the phone number length is 9 or 10
     *
     * @param phone the String to check {@code String}
     * @return {@code true} if the string length is equals to 9 or equals to 10
     */
    private static Boolean phoneChecker(String phone) {
        return phone.length() == 10 || phone.length() == 9 && isNumeric(phone);
    }

    /**
     * Receive from user contact name and validate the incoming input
     *
     * @return Contact name
     */
    private String scanContactName() {
        String name = input.nextLine().trim();
        while (!nameChecker(name)) {
            System.out.println("Pay attention! you entered a wrong name...");
            System.out.print("Enter the contact name please (1-20 characters): ");
            name = input.nextLine().trim();
        }
        return name;
    }

    /**
     * Receive from user phone number and validate the incoming input
     *
     * @return Contact phone number
     */
    private String scanContactPhone() {
        String phone = input.nextLine().trim();
        while (!phoneChecker(phone)) {
            System.out.println("Pay attention! you entered a wrong phone number...");
            System.out.print("Enter the contact phone number please (9-10 numbers): ");
            phone = input.nextLine().trim();
        }
        return phone;
    }

    /**
     * Receive from user a new contact - name and phone
     *
     * @return new contact
     */
    private Contact scanContact() {
        System.out.print("Enter the contact name please (1-20 charters): ");
        String name = scanContactName();
        System.out.print("Enter the contact phone number please (9-10 numbers): ");
        String phone = scanContactPhone();
        return new Contact(name, phone);
    }

    /**
     * Receive contact from user and add to list
     */
    public void addContact() {
        contacts.add(scanContact());
        System.out.print("The contact has been added to the contact list!!!");
    }

    /**
     * Delete contact by name
     */
    public void deleteContact() {
        System.out.print("Please enter the name of the contact you want to delete (1-20 charters): ");
        String name = scanContactName();
        if (!findContactsByName(name).isEmpty()) {
            contacts.remove(findContactsByName(name).get(0));
            System.out.println("The contact: " + name + " has been deleted!!!");
        } else {
            System.out.println("Sorry! this contact doesn't exist...");
        }
    }

    /**
     * Print the given contact list
     *
     * @param contacts list to print
     */
    private static void printContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            printContact(contact);
        }
    }

    /**
     * Print the phone book contact list
     */
    public void printContactsList() {
        printContacts(contacts);
    }

    /**
     * Receive contact name from user find contact by name and print.
     * If contact is not found print error message
     */
    public void findContactByNameAndPrint() {
        System.out.print("Please enter the name of the contact that you want to find (1-20 characters): ");
        String name = scanContactName();
        List<Contact> contacts = findContactsByName(name);
        if (contacts.isEmpty()) {
            System.out.println("Contact wasn't found");
        } else {
            printContacts(contacts);
        }
    }

    /**
     * Sort contact by name - In ascending order
     */
    public void sortByNameAndPrintContacts() {
        contacts.sort(comparing(Contact::getName));
        printContacts(contacts);
    }

    /**
     * Sort contact by phone number - In descending order
     */
    public void sortByPhoneAndPrintContacts() {
        contacts.sort((o1, o2) -> o2.getPhone().compareTo(o1.getPhone()));
        printContacts(contacts);
    }

    /**
     * Revert and print the contact list
     */
    public void revertContactsAndPrint() {
        System.out.println("\nThis is the contact list's in its current order:\n");
        printContacts(contacts);
        reverse(contacts);
        System.out.println("\nAnd here it is in reverse order\n");
        printContacts(contacts);
    }

    /**
     * Remove duplicate contacts from list
     */
    public void distinctContacts() {
        List<Contact> deletedContacts = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = 0; j < contacts.size(); j++) {
                if (contacts.get(i).equals(contacts.get(j)) && i != j) {
                    deletedContacts.add(contacts.get(j));
                    contacts.remove(contacts.get(j));
                }
            }
        }
        System.out.println("******************************The removed contacts are:******************************");
        printContacts(deletedContacts);
        System.out.println("********************************************************************************");
    }

    /**
     * Receive contact from user and file name and write the contact to the file
     */
    public void saveContactListToNewFile() {
        if (contacts.isEmpty()) {
            System.out.println("Contact list is empty!!");
        } else {
            System.out.print("Please enter phone book's name that you want to save: ");
            String fileName = input.nextLine().trim();
            for (Contact contact : contacts) {
                try {
                    FileWriter writer = new FileWriter(fileName + ".txt", true);
                    writer.write(contact.getName());
                    writer.write("\r\n");
                    writer.write(contact.getPhone());
                    writer.write("\r\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Receive phone book name and initialize contacts to list
     */
    public void initContacts() {
        System.out.print("Please enter phone book name that you want to import: ");
        String fileName = input.nextLine().trim();
        contacts = new ArrayList<>();
        File file = new File(fileName + ".txt");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String name;
            String phone;
            while ((name = bufferedReader.readLine()) != null && (phone = bufferedReader.readLine()) != null) {
                contacts.add(new Contact(name, phone));
            }
            System.out.println("The phone book that is currently being use now is: " + fileName);
        } catch (Exception e) {
            System.out.println("the phone book is not found!!!");
        }
    }

    private List<Contact> findContactsByName(String name) {
        return contacts.stream()
                       .filter(contact -> contact.getName().equals(name))
                       .collect(toList());
    }

    /**
     * Print contact
     *
     * @param contact {@code Contact}
     */
    public static void printContact(Contact contact) {
        System.out.println("------------------");
        System.out.println("Name: " + contact.getName());
        System.out.println("Phone: " + contact.getPhone());
        System.out.println("------------------");
    }

}
