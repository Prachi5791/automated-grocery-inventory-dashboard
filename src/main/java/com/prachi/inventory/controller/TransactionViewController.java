package com.prachi.inventory.controller;

import com.prachi.inventory.service.ItemService;
import com.prachi.inventory.service.TransactionService;
import jakarta.servlet.http.HttpSession;
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
    public String showTransactions(
            Model model,
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        // First visit defaults to Admin.
        if (role == null) {
            role = "ADMIN";
            session.setAttribute("role", role);
        }

        // Customer cannot access Transactions.
        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        loadPage(model, null);

        model.addAttribute("role", role);

        return "transactions";
    }

    @PostMapping("/stock-in")
    public String stockIn(
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String note,
            Model model,
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            role = "ADMIN";
            session.setAttribute("role", role);
        }

        // Customer cannot perform Stock In.
        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        try {

            transactionService.stockIn(
                    itemId,
                    quantity,
                    note
            );

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {

            loadPage(model, e.getMessage());

            model.addAttribute("role", role);

            return "transactions";
        }
    }

    @PostMapping("/stock-out")
    public String stockOut(
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String note,
            Model model,
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            role = "ADMIN";
            session.setAttribute("role", role);
        }

        // Customer cannot perform Stock Out.
        if (!role.equals("ADMIN")) {
            return "redirect:/items";
        }

        try {

            transactionService.stockOut(
                    itemId,
                    quantity,
                    note
            );

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {

            loadPage(model, e.getMessage());

            model.addAttribute("role", role);

            return "transactions";
        }
    }

    private void loadPage(Model model, String error) {

        if (error != null) {
            model.addAttribute("error", error);
        }

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