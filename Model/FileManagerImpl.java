package Model;

import java.io.*;
import java.util.ArrayList;

public class FileManagerImpl implements FileManager {
    @Override
    public void saveToFile(String fileName, ContactList contactList) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(contactList.entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ContactList loadFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            ArrayList<ContactEntry> entries = (ArrayList<ContactEntry>) inputStream.readObject();
            SimpleContactList contactList = new SimpleContactList();
            contactList.entries = entries;
            return contactList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
