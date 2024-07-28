import java.io.*;
import java.util.*;
class ContactManager 
{
    private static final String FILE_NAME = "contacts.txt";
    private List<Contact> contacts = new ArrayList<>();
    public static void main(String[] args) 
    {
        ContactManager manager = new ContactManager();
        manager.loadContacts();
        manager.showMenu();
    }
    private void showMenu() 
    {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) 
        {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) 
            {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    saveContacts();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void addContact() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        Contact newContact = new Contact(name, phone, email);
        contacts.add(newContact);
        System.out.println("Contact added successfully.");
    }
    public void viewContacts() 
    {
        if (contacts.isEmpty()) 
            System.out.println("No contacts to display.");
        else 
        {
            System.out.println("Contacts:");
            for (Contact contact : contacts) 
                System.out.println(contact);
        }
    }
    private void editContact() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact to edit: ");
        String name = scanner.nextLine();
        for (Contact contact : contacts) 
        {
            if (contact.getName().equals(name)) 
            {
                System.out.print("Enter new phone number: ");
                String newPhone = scanner.nextLine();
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                contact.setPhone(newPhone);
                contact.setEmail(newEmail);
                System.out.println("Contact updated successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }
    private void deleteContact() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) 
        {
            Contact contact = iterator.next();
            if (contact.getName().equals(name)) 
            {
                iterator.remove();
                System.out.println("Contact deleted successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }
    private void loadContacts() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 3) 
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
            }
        } 
        catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
    private void saveContacts() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) 
        {
            for (Contact contact : contacts) 
            {
                writer.write(contact.toFileString());
                writer.newLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
    private static class Contact 
    {
        private String name;
        private String phone;
        private String email;
        public Contact(String name, String phone, String email) 
        {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
        public String getName() 
        {
            return name;
        }
        public void setPhone(String phone) 
        {
            this.phone = phone;
        }
        public void setEmail(String email) 
        {
            this.email = email;
        }
        @Override
        public String toString() 
        {
            return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
        }
        
        public String toFileString() 
        {
            return name + "," + phone + "," + email;
        }
    }
}
