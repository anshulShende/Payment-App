package com.payment.app.service;

import com.payment.app.dto.TransactionRequest;
import com.payment.app.enums.TransactionStatus;
import com.payment.app.model.Transaction;
import com.payment.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction initiateTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID())
                .amount(transactionRequest.getAmount())
                .currency(transactionRequest.getCurrency())
                .senderId(transactionRequest.getSenderId())
                .receiverId(transactionRequest.getReceiverId())
                .transactionType(transactionRequest.getTransactionType())
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Send transaction to Kafka
        kafkaTemplate.send("transactions", transaction);

        // Save to database
        return transactionRepository.save(transaction);
    }
}

