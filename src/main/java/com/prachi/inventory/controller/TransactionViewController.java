package com.prachi.inventory.controller;

import com.prachi.inventory.service.ItemService;
import com.prachi.inventory.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionViewController {

    private final TransactionService transactionService;
    private final ItemService itemService;

    public TransactionViewController(
            TransactionService transactionService,
            ItemService itemService) {
        this.transactionService = transactionService;
        this.itemService = itemService;
    }

    @GetMapping
    public String showTransactions(Model model) {

        model.addAttribute(
                "transactions",
                transactionService.getAllTransactions()
        );

        model.addAttribute(
                "items",
                itemService.getAllItems()
        );

        return "transactions";
    }

    @PostMapping("/stock-in")
    public String stockIn(
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String note,
            Model model) {

        try {

            transactionService.stockIn(
                    itemId,
                    quantity,
                    note
            );

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {

            loadPage(model, e.getMessage());

            return "transactions";
        }
    }

    @PostMapping("/stock-out")
    public String stockOut(
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String note,
            Model model) {

        try {

            transactionService.stockOut(
                    itemId,
                    quantity,
                    note
            );

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {

            loadPage(model, e.getMessage());

            return "transactions";
        }
    }

    private void loadPage(Model model, String error) {

        model.addAttribute("error", error);

        model.addAttribute(
                "transactions",
                transactionService.getAllTransactions()
        );

        model.addAttribute(
                "items",
                itemService.getAllItems()
        );
    }
}