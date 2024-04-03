package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ContactBookUI {
    private ContactList contactList;
    private FileManager fileManager;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public ContactBookUI(ContactList contactList, FileManager fileManager) {
        this.contactList = contactList;
        this.fileManager = fileManager;

        frame = new JFrame("Simple Contact Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        String[] columnNames = {"Name", "Phone Number", "Email", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton loadButton = new JButton("Load");
        loadButton.setBackground(new Color(52, 152, 219));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFocusPainted(false);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadContactBook();
            }
        });
        buttonPanel.add(loadButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveContactBook();
            }
        });
        buttonPanel.add(saveButton);

        JButton addButton = new JButton("Add Contact");
        addButton.setBackground(new Color(241, 196, 15));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Contact");
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });
        buttonPanel.add(editButton);

        JButton removeButton = new JButton("Remove Contact");
        removeButton.setBackground(new Color(231, 76, 60));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeContact();
            }
        });
        buttonPanel.add(removeButton);

        JButton searchButton = new JButton("Search Contact");
        searchButton.setBackground(new Color(52, 73, 94));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        buttonPanel.add(searchButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void loadContactBook() {
        ContactList loadedContactList = fileManager.loadFromFile("contactbook.dat");
        if (loadedContactList != null) {
            contactList = loadedContactList;
            displayContacts();
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to load contact book!");
        }
    }

    private void saveContactBook() {
        fileManager.saveToFile("contactbook.dat", contactList);
        JOptionPane.showMessageDialog(frame, "Contact book saved successfully!");
    }

    private void displayContacts() {
        tableModel.setRowCount(0);
        for (ContactEntry entry : contactList.entries) {
            Object[] rowData = {entry.getName(), entry.getPhoneNumber(), entry.getEmail(),
                    entry.getAddress()};
            tableModel.addRow(rowData);
        }
    }

    private void addContact() {
        JTextField nameField = new JTextField(15);
        JTextField phoneNumberField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField addressField = new JTextField(15);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            ContactEntry entry = new ContactEntry(name, phoneNumber, email, address);
            contactList.addEntry(entry);
            displayContacts();
        }
    }

    private void removeContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) table.getValueAt(selectedRow, 0);
            contactList.deleteEntry(name);
            displayContacts();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact to remove.");
        }
    }

    private void editContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) table.getValueAt(selectedRow, 0);
            String phoneNumber = (String) table.getValueAt(selectedRow, 1);
            String email = (String) table.getValueAt(selectedRow, 2);
            String address = (String) table.getValueAt(selectedRow, 3);

            JTextField nameField = new JTextField(name, 15);
            JTextField phoneNumberField = new JTextField(phoneNumber, 15);
            JTextField emailField = new JTextField(email, 15);
            JTextField addressField = new JTextField(address, 15);

            JPanel inputPanel = new JPanel(new GridLayout(4, 2));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Phone Number:"));
            inputPanel.add(phoneNumberField);
            inputPanel.add(new JLabel("Email:"));
            inputPanel.add(emailField);
            inputPanel.add(new JLabel("Address:"));
            inputPanel.add(addressField);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Contact", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newName = nameField.getText();
                String newPhoneNumber = phoneNumberField.getText();
                String newEmail = emailField.getText();
                String newAddress = addressField.getText();
                ContactEntry newEntry = new ContactEntry(newName, newPhoneNumber, newEmail, newAddress);
                contactList.editEntry(name, newEntry);
                displayContacts();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact to edit.");
        }
    }
}
