package com.lk.pay.braintree.controller;

import com.braintreegateway.*;
import com.lk.pay.Msg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/braintree")
public class BraintreeController {
    private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
            "z3kj2246rsvttk8w","t9fmnr3mwjzyvrtd","76ca70624230236d62e98cf6f174d424");

    @PostMapping("/getToken")
    public String getToken(){
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = gateway.clientToken().generate(clientTokenRequest);
        return clientToken;
    }

    @PostMapping("/pay")
    public Msg pay(String nonce) {

        CustomerRequest customerRequest = new CustomerRequest()
                .firstName("Mark1")
                .lastName("Jones1")
                .company("Jones1 Co.")
                .email("mark1.jones@example.com")
                .fax("429-555-1234")
                .phone("624-555-1234")
                .website("http://example2.com");
        Result<Customer> customerResult = gateway.customer().create(customerRequest);
        System.out.println(customerResult.getTarget().getId());
        System.out.println(customerResult.isSuccess());


        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("10.00"))
                .paymentMethodNonce(nonce)
                .deviceData("h5")
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);
        if(result.isSuccess()){
            gateway.transaction().submitForSettlement(result.getTarget().getId());

            return Msg.ok(result.getTarget().getStatus().toString());
        }else{
            return Msg.fail(result.getMessage());
        }
    }
}
