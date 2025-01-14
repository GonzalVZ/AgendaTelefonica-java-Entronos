import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

public class AgendaTelefonicaTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddContact() {
        Scanner scanner = new Scanner("John Doe\n123456789\n");
        AgendaTelefonica.addContact(scanner);
        AgendaTelefonica.showContacts();
        String output = outContent.toString();
        Assertions.assertTrue(output.contains("John Doe"));
        Assertions.assertTrue(output.contains("123456789"));
    }

    @Test
    public void testDeleteContact() {
        Scanner scannerAdd = new Scanner("John Doe\n123456789\n");
        AgendaTelefonica.addContact(scannerAdd);
        Scanner scannerDelete = new Scanner("John Doe\n");
        AgendaTelefonica.deleteContact(scannerDelete);
        AgendaTelefonica.showContacts();
        String output = outContent.toString();
        Assertions.assertFalse(output.contains("John Doe"));
        Assertions.assertFalse(output.contains("123456789"));
    }

    @Test
    public void testShowContacts() {
        Scanner scanner = new Scanner("John Doe\n123456789\n");
        AgendaTelefonica.addContact(scanner);
        AgendaTelefonica.showContacts();
        String output = outContent.toString();
        Assertions.assertTrue(output.contains("John Doe"));
        Assertions.assertTrue(output.contains("123456789"));
    }
}
