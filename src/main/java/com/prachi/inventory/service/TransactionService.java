package com.prachi.inventory.service;

import com.prachi.inventory.model.Item;
import com.prachi.inventory.model.Transaction;
import com.prachi.inventory.repository.ItemRepository;
import com.prachi.inventory.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            ItemRepository itemRepository) {

        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Transaction stockIn(Long itemId, Integer quantity, String note) {

        validateQuantity(quantity);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Item not found"));

        int currentQuantity =
                item.getQuantity() == null ? 0 : item.getQuantity();

        item.setQuantity(currentQuantity + quantity);

        itemRepository.save(item);

        Transaction transaction = new Transaction();

        transaction.setItem(item);
        transaction.setType("IN");
        transaction.setQuantity(quantity);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setNote(note);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction stockOut(Long itemId, Integer quantity, String note) {

        validateQuantity(quantity);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Item not found"));

        int currentQuantity =
                item.getQuantity() == null ? 0 : item.getQuantity();

        if (quantity > currentQuantity) {
            throw new IllegalArgumentException(
                    "Insufficient stock. Available quantity: "
                            + currentQuantity);
        }

        item.setQuantity(currentQuantity - quantity);

        itemRepository.save(item);

        Transaction transaction = new Transaction();

        transaction.setItem(item);
        transaction.setType("OUT");
        transaction.setQuantity(quantity);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setNote(note);

        return transactionRepository.save(transaction);
    }

    private void validateQuantity(Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0");
        }
    }
}