package com.sumit.javatechie.service;

import com.sumit.javatechie.common.Payment;
import com.sumit.javatechie.common.TransactionRequest;
import com.sumit.javatechie.common.TransactionResponse;
import com.sumit.javatechie.entity.Order;
import com.sumit.javatechie.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest request){

        String response="";

        Order order=request.getOrder();
        Payment payment=request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest call

        Payment res = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);

        response = res.getPaymentStatus().equals("SUCCESS") ? "payment processing successful and order placed " : "there is failure in payement api while order added to cart";


        repository.save(order);

        return new TransactionResponse(order,res.getAmount(),res.getTransactionId(),response);
    }

    public Order findByPaymentHistoryByOrderId(int orderId) {
        return repository.findById(orderId).get();
    }
}
