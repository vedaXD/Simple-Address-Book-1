package Model;

public interface FileManager {
    void saveToFile(String fileName, ContactList contactList);
    ContactList loadFromFile(String fileName);
}
