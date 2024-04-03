package Model;

import java.util.ArrayList;

public abstract class ContactList {
    protected ArrayList<ContactEntry> entries;

    public ContactList() {
        entries = new ArrayList<>();
    }

    public abstract void addEntry(ContactEntry entry);

    public abstract void editEntry(String name, ContactEntry newEntry);

    public abstract void deleteEntry(String name);

    public abstract ContactEntry searchEntry(String name);
}
