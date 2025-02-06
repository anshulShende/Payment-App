package com.payment.app.controller;

import com.payment.app.dto.TransactionRequest;
import com.payment.app.model.Transaction;
import com.payment.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;


    @Operation(summary = "Initiate a new transaction")
    @ApiResponse(responseCode = "200", description = "Transaction initiated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping("/initiate")
    public ResponseEntity<Transaction> initiateTransaction(@RequestBody TransactionRequest transaction) {
        Transaction savedTransaction = transactionService.initiateTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }
}

