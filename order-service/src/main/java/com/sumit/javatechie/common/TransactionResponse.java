package com.sumit.javatechie.common;

import com.sumit.javatechie.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Order order;

    private double amount;
    private String transactionId;

    private String message;
}
