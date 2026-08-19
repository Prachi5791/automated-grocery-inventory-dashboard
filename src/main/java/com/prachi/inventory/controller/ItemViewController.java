//package com.prachi.inventory.controller;
//
//import com.prachi.inventory.model.Item;
//import com.prachi.inventory.service.ItemService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/items")
//public class ItemViewController {
//
//    private final ItemService itemService;
//
//    public ItemViewController(ItemService itemService) {
//        this.itemService = itemService;
//    }
//
//    // Display inventory page
//    @GetMapping
//    public String showItems(Model model) {
//
//        // List of existing inventory items
//        model.addAttribute("items", itemService.getAllItems());
//
//        // Object used by the Add Item form in items.html
//        model.addAttribute("item", new Item());
//
//        return "items";
//    }
//
//    // Add item from UI
//    @PostMapping
//    public String addItem(@ModelAttribute("item") Item item) {
//
//        itemService.createItem(item);
//
//        return "redirect:/items";
//    }
//
//    // Delete item from UI
//    @GetMapping("/delete/{id}")
//    public String deleteItem(@PathVariable Long id) {
//
//        itemService.deleteItem(id);
//
//        return "redirect:/items";
//    }
//
//    // Edit item page
//    @GetMapping("/edit/{id}")
//    public String editItem(
//            @PathVariable Long id,
//            Model model) {
//
//        return itemService.getItemById(id)
//                .map(existingItem -> {
//
//                    model.addAttribute("item", existingItem);
//
//                    return "edit-item";
//                })
//                .orElse("redirect:/items");
//    }
//
//    // Update item from UI
//    @PostMapping("/update/{id}")
//    public String updateItem(
//            @PathVariable Long id,
//            @ModelAttribute("item") Item item) {
//
//        itemService.updateItem(id, item);
//
//        return "redirect:/items";
//    }
//}

package com.prachi.inventory.controller;

import com.prachi.inventory.model.Item;
import com.prachi.inventory.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemViewController {

    private final ItemService itemService;

    public ItemViewController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Display inventory
    @GetMapping
    public String showItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            Model model) {

        List<Item> items;

        // Search
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = itemService.searchItems(keyword);

        // Category filter
        } else if (category != null &&
                !category.trim().isEmpty() &&
                !category.equalsIgnoreCase("All")) {

            items = itemService.getItemsByCategory(category);

        // No filter
        } else {
            items = itemService.getAllItems();
        }

        // Get categories dynamically from database
        List<String> categories = itemService.getAllItems()
                .stream()
                .map(Item::getCategory)
                .filter(categoryName ->
                        categoryName != null &&
                        !categoryName.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("items", items);
        model.addAttribute("item", new Item());
        model.addAttribute("categories", categories);

        // Summary cards
        model.addAttribute("totalItems", itemService.getTotalItems());
        model.addAttribute("inStockCount", itemService.getInStockCount());
        model.addAttribute("lowStockCount", itemService.getLowStockCount());
        model.addAttribute("outOfStockCount", itemService.getOutOfStockCount());

        // Preserve search/filter values
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);

        return "items";
    }

    // Add item
    @PostMapping
    public String addItem(@ModelAttribute("item") Item item) {

        itemService.createItem(item);

        return "redirect:/items";
    }

    // Delete item
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {

        itemService.deleteItem(id);

        return "redirect:/items";
    }

    // Edit page
    @GetMapping("/edit/{id}")
    public String editItem(
            @PathVariable Long id,
            Model model) {

        return itemService.getItemById(id)
                .map(existingItem -> {

                    model.addAttribute("item", existingItem);

                    return "edit-item";

                })
                .orElse("redirect:/items");
    }

    // Update item
    @PostMapping("/update/{id}")
    public String updateItem(
            @PathVariable Long id,
            @ModelAttribute("item") Item item) {

        itemService.updateItem(id, item);

        return "redirect:/items";
    }
}