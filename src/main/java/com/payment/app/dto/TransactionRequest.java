package com.payment.app.dto;

import com.payment.app.enums.Currency;
import com.payment.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private BigDecimal amount;
    private Currency currency;
    private String senderId;
    private String receiverId;
    private TransactionType transactionType;
}

