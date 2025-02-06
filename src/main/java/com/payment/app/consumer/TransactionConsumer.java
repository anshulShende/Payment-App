package com.payment.app.consumer;


import com.payment.app.dto.TransactionRequest;
import com.payment.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "transactions", groupId = "payment-group")
    public void processTransaction(TransactionRequest transaction) {
        transactionService.initiateTransaction(transaction);
    }
}