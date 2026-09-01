package com.prachi.inventory.controller;

import com.prachi.inventory.model.Item;
import com.prachi.inventory.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/alerts")
public class AlertViewController {

    private final ItemService itemService;

    public AlertViewController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showAlerts(Model model) {

        List<Item> items = itemService.getAllItems();

        List<Item> outOfStockItems = items.stream()
                .filter(item ->
                        item.getQuantity() != null
                                && item.getQuantity() == 0)
                .collect(Collectors.toList());

        List<Item> lowStockItems = items.stream()
                .filter(item ->
                        item.getQuantity() != null
                                && item.getQuantity() > 0
                                && item.getReorderLevel() != null
                                && item.getQuantity() <= item.getReorderLevel())
                .collect(Collectors.toList());

        model.addAttribute("outOfStockItems", outOfStockItems);
        model.addAttribute("lowStockItems", lowStockItems);

        model.addAttribute(
                "totalAlerts",
                outOfStockItems.size() + lowStockItems.size()
        );

        return "alerts";
    }
}
