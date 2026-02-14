package com.inventory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private HashMap<String, Product> inventory = new HashMap<>();
    private final String FILE_NAME = "inventory.dat";

    public InventoryManager() {
        loadFromFile();
    }

    // Add Product
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println("Product ID already exists!");
            return;
        }
        inventory.put(product.getId(), product);
        saveToFile();
        System.out.println("Product added successfully!");
    }

    // Update Product
    public void updateProduct(String id, int quantity, double price) {
        if (!inventory.containsKey(id)) {
            System.out.println("Product not found!");
            return;
        }
        Product product = inventory.get(id);
        product.setQuantity(quantity);
        product.setPrice(price);
        saveToFile();
        System.out.println("Product updated successfully!");
    }

    // Delete Product
    public void deleteProduct(String id) {
        if (inventory.remove(id) != null) {
            saveToFile();
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    // View All Products
    public void viewProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }

    // Inventory Summary
    public void showSummary() {
        int totalItems = 0;
        double totalValue = 0;

        for (Product product : inventory.values()) {
            totalItems += product.getQuantity();
            totalValue += product.getQuantity() * product.getPrice();
        }

        System.out.println("Total Items: " + totalItems);
        System.out.println("Total Inventory Value: " + totalValue);
    }

    // Save to file
    private void saveToFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load from file
    private void loadFromFile() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (HashMap<String, Product>) ois.readObject();
        } catch (Exception e) {
            inventory = new HashMap<>();
        }
    }
}

