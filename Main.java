import Model.ContactBookUI;
import Model.ContactEntry;
import Model.ContactList;
import Model.FileManager;
import Model.FileManagerImpl;
import Model.SimpleContactList;

public class Main {
    public static void main(String[] args) {
        ContactList contactList = new SimpleContactList();
        FileManager fileManager = new FileManagerImpl();
        ContactBookUI contactUI = new ContactBookUI(contactList, fileManager);
    }
}
