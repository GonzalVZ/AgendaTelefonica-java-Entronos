import java.io.*;
import java.util.*;

public class AgendaTelefonica {

    private static String fileName = "agenda.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nGestión de Agenda Telefónica");
            System.out.println("1. Añadir contacto");
            System.out.println("2. Eliminar contacto por nombre");
            System.out.println("3. Mostrar agenda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    deleteContact(scanner);
                    break;
                case 3:
                    showContacts();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    public static void setFileName(String fileName) {
        AgendaTelefonica.fileName = fileName;
    }

    public static void addContact(Scanner scanner) {
        System.out.print("Ingrese el nombre: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String phone = scanner.nextLine();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(new Contact(name, phone));
            System.out.println("Contacto añadido exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el contacto: " + e.getMessage());
        }
    }

    public static void deleteContact(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nameToDelete = scanner.nextLine();
        List<Contact> contacts = loadContacts();
        boolean removed = contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(nameToDelete));

        if (removed) {
            saveContacts(contacts);
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    public static void showContacts() {
        List<Contact> contacts = loadContacts();
        if (contacts.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("\nLista de contactos:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public static List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                contacts.add((Contact) ois.readObject());
            }
        } catch (EOFException e) {
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
        return contacts;
    }

    private static void saveContacts(List<Contact> contacts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Contact contact : contacts) {
                oos.writeObject(contact);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public static class Contact implements Serializable {
        private final String name;
        private final String phone;

        public Contact(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Nombre: " + name + ", Teléfono: " + phone;
        }
    }
}