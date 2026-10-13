package com.prachi.inventory.controller;

import com.prachi.inventory.model.Item;
import com.prachi.inventory.service.ItemService;
import jakarta.servlet.http.HttpSession;
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
            @RequestParam(required = false) String role,
            Model model,
            HttpSession session) {

        // Update session role only when the profile selector sends a role.
        if (role != null) {
            if (role.equalsIgnoreCase("CUSTOMER")) {
                session.setAttribute("role", "CUSTOMER");
            } else {
                session.setAttribute("role", "ADMIN");
            }
        }

        // Get current role from session.
        String currentRole =
                (String) session.getAttribute("role");

        // First visit defaults to Admin.
        if (currentRole == null) {
            currentRole = "ADMIN";
            session.setAttribute("role", currentRole);
        }

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
        model.addAttribute(
                "totalItems",
                itemService.getTotalItems()
        );

        model.addAttribute(
                "inStockCount",
                itemService.getInStockCount()
        );

        model.addAttribute(
                "lowStockCount",
                itemService.getLowStockCount()
        );

        model.addAttribute(
                "outOfStockCount",
                itemService.getOutOfStockCount()
        );

        // Preserve search/filter values
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);

        // Current profile
        model.addAttribute("role", currentRole);

        return "items";
    }

    // Add item - Admin only
    @PostMapping
    public String addItem(
            @ModelAttribute("item") Item item,
            HttpSession session) {

        String role = getRole(session);

        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        itemService.createItem(item);

        return "redirect:/items";
    }

    // Delete item - Admin only
    @GetMapping("/delete/{id}")
    public String deleteItem(
            @PathVariable Long id,
            HttpSession session) {

        String role = getRole(session);

        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        itemService.deleteItem(id);

        return "redirect:/items";
    }

    // Edit item page - Admin only
    @GetMapping("/edit/{id}")
    public String editItem(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        String role = getRole(session);

        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        return itemService.getItemById(id)
                .map(existingItem -> {

                    model.addAttribute("item", existingItem);
                    model.addAttribute("role", role);

                    return "edit-item";
                })
                .orElse("redirect:/items");
    }

    // Update item - Admin only
    @PostMapping("/update/{id}")
    public String updateItem(
            @PathVariable Long id,
            @ModelAttribute("item") Item item,
            HttpSession session) {

        String role = getRole(session);

        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        itemService.updateItem(id, item);

        return "redirect:/items";
    }

    // Get current session role.
    private String getRole(HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            role = "ADMIN";
            session.setAttribute("role", role);
        }

        return role;
    }
}