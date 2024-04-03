package Model;

import java.util.Iterator;

public class SimpleContactList extends ContactList {
    @Override
    public void addEntry(ContactEntry entry) {
        entries.add(entry);
    }

    @Override
    public void editEntry(String name, ContactEntry newEntry) {
        Iterator<ContactEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            ContactEntry existingEntry = iterator.next();
            if (existingEntry.getName().equals(name)) {
                iterator.remove();
                entries.add(newEntry);
                break;
            }
        }
    }

    @Override
    public void deleteEntry(String name) {
        entries.removeIf(entry -> entry.getName().equals(name));
    }

    @Override
    public ContactEntry searchEntry(String name) {
        for (ContactEntry entry : entries) {
            if (entry.getName().equals(name)) {
                return entry;
            }
        }
        return null;
    }
}
