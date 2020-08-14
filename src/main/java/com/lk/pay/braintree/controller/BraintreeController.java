package com.lk.pay.braintree.controller;

import com.braintreegateway.*;
import com.lk.pay.Msg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
