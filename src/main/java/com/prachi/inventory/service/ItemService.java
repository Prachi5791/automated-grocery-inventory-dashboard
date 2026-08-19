//package com.prachi.inventory.service;
//
//import com.prachi.inventory.model.Item;
//import com.prachi.inventory.repository.ItemRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ItemService {
//
//    private final ItemRepository itemRepository;
//
//    public ItemService(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//    // Get all items
//    public List<Item> getAllItems() {
//        return itemRepository.findAll();
//    }
//
//    // Get one item by ID
//    public Optional<Item> getItemById(Long id) {
//        return itemRepository.findById(id);
//    }
//
//    // Add new item
//    public Item createItem(Item item) {
//        return itemRepository.save(item);
//    }
//
//    // Update existing item
//    public Optional<Item> updateItem(Long id, Item updatedItem) {
//
//        return itemRepository.findById(id).map(existingItem -> {
//
//            existingItem.setName(updatedItem.getName());
//            existingItem.setCategory(updatedItem.getCategory());
//            existingItem.setQuantity(updatedItem.getQuantity());
//            existingItem.setUnit(updatedItem.getUnit());
//            existingItem.setPrice(updatedItem.getPrice());
//            existingItem.setReorderLevel(updatedItem.getReorderLevel());
//
//            return itemRepository.save(existingItem);
//        });
//    }
//
//    // Delete item
//    public boolean deleteItem(Long id) {
//
//        if (!itemRepository.existsById(id)) {
//            return false;
//        }
//
//        itemRepository.deleteById(id);
//        return true;
//    }
//}

package com.prachi.inventory.service;

import com.prachi.inventory.model.Item;
import com.prachi.inventory.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Get all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Search by name or category
    public List<Item> searchItems(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }

        String search = keyword.toLowerCase().trim();

        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        (item.getName() != null &&
                                item.getName().toLowerCase().contains(search))
                        ||
                        (item.getCategory() != null &&
                                item.getCategory().toLowerCase().contains(search))
                )
                .collect(Collectors.toList());
    }

    // Filter by category
    public List<Item> getItemsByCategory(String category) {

        if (category == null || category.trim().isEmpty()
                || category.equalsIgnoreCase("All")) {
            return getAllItems();
        }

        String selectedCategory = category.trim();

        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getCategory() != null &&
                        item.getCategory().equalsIgnoreCase(selectedCategory)
                )
                .collect(Collectors.toList());
    }

    // Get one item
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    // Create
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    // Update
    public Optional<Item> updateItem(Long id, Item updatedItem) {

        return itemRepository.findById(id).map(existingItem -> {

            existingItem.setName(updatedItem.getName());
            existingItem.setCategory(updatedItem.getCategory());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setUnit(updatedItem.getUnit());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setReorderLevel(updatedItem.getReorderLevel());

            return itemRepository.save(existingItem);
        });
    }

    // Delete
    public boolean deleteItem(Long id) {

        if (!itemRepository.existsById(id)) {
            return false;
        }

        itemRepository.deleteById(id);
        return true;
    }

    // Count total items
    public long getTotalItems() {
        return itemRepository.count();
    }

    // Count in-stock items
    public long getInStockCount() {

        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getQuantity() != null &&
                        item.getReorderLevel() != null &&
                        item.getQuantity() > item.getReorderLevel()
                )
                .count();
    }

    // Count low-stock items
    public long getLowStockCount() {

        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getQuantity() != null &&
                        item.getReorderLevel() != null &&
                        item.getQuantity() > 0 &&
                        item.getQuantity() <= item.getReorderLevel()
                )
                .count();
    }

    // Count out-of-stock items
    public long getOutOfStockCount() {

        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getQuantity() != null &&
                        item.getQuantity() == 0
                )
                .count();
    }
}