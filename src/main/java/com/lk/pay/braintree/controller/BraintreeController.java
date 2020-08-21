package com.lk.pay.braintree.controller;

import com.alibaba.fastjson.JSON;
import com.braintreegateway.*;
import com.lk.pay.Msg;
import com.lk.pay.utils.GatewayFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/braintree")
public class BraintreeController {
    private static BraintreeGateway gateway = GatewayFactory.getlisiGateway();

    @PostMapping("/getToken")
    public String getToken(HttpServletRequest request, HttpServletResponse response){
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = gateway.clientToken().generate(clientTokenRequest);
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        return clientToken;
    }

    @PostMapping("/pay")
    public Msg pay(String nonce, HttpServletRequest request1, HttpServletResponse response) {

//        CustomerRequest customerRequest = new CustomerRequest()
//                .firstName("Mark1")
//                .lastName("Jones1")
//                .company("Jones1 Co.")
//                .email("mark1.jones@example.com")
//                .fax("429-555-1234")
//                .phone("624-555-1234")
//                .website("http://example2.com");
//        Result<Customer> customerResult = gateway.customer().create(customerRequest);
//        System.out.println(customerResult.getTarget().getId());
//        System.out.println(customerResult.isSuccess());


        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("10.00"))
                .paymentMethodNonce(nonce)
                .deviceData("h5")
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);

        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin",request1.getHeader("Origin"));

        if(result.isSuccess()){
            return Msg.ok(result.getTarget().getStatus().toString());
        }else{
            return Msg.fail(result.getMessage());
        }
    }

    @PostMapping("/pay2")
    public Msg pay2(String nonce, HttpServletRequest request, HttpServletResponse response) {
        gateway.plan().all().stream().forEach(plan-> System.out.println("id:"+plan.getId()+";name:"+plan.getName()));

        CustomerRequest customerRequest = new CustomerRequest()
                .id(UUID.randomUUID().toString())
                .paymentMethodNonce(nonce)
                .firstName("Fred")
                .lastName("Jones")

                .creditCard().done();
        Result<Customer> customerResult = gateway.customer().create(customerRequest);

        Customer customer = gateway.customer().find(customerResult.getTarget().getId());
        String token = customer.getDefaultPaymentMethod().getToken();


        SubscriptionRequest subscriptionRequest = new SubscriptionRequest().planId("jhk2").paymentMethodToken(token);
        Result<Subscription> result = gateway.subscription().create(subscriptionRequest);

        System.out.println(JSON.toJSONString(result));

        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));

        if(result.isSuccess()){
            return Msg.ok(result.getTarget().getStatus().toString());
        }else{
            return Msg.fail(result.getMessage());
        }
    }
}
