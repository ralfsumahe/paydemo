package com.lk.pay;

import com.alibaba.fastjson.JSON;
import com.braintreegateway.*;
import com.lk.pay.utils.GatewayFactory;

public class Test {
    private static BraintreeGateway gateway = GatewayFactory.getlisiGateway();;

    @org.junit.Test
    public void customerTest(){
//        gateway.customer().all().getIds().forEach(
//                id -> System.out.println(JSON.toJSONString(gateway.customer().delete(id))));

        System.out.println(gateway.customer().all().getIds());

        CustomerRequest customerRequest = new CustomerRequest()
                .id("myid6")
                .firstName("li")
                .lastName("si")
                .company("lisi Co.")
                .email("lisi@163.com")
                .fax("429-555-1234")
                .phone("624-555-1234")
                .website("http://examplelisi.com");
        Result<Customer> customerResult = gateway.customer().create(customerRequest);

//        System.out.println(gateway.customer().all().getIds());
//
//        System.out.println(JSON.toJSONString(gateway.subscription().search(new SubscriptionSearchRequest().merchantAccountId().is("z3kj2246rsvttk8w"))));
    }

    @org.junit.Test
    public void getPlan(){
        gateway.plan().all().forEach(p -> System.out.println(p.getId()));
    }


    @org.junit.Test
    public void getCustomer(){
        gateway.customer().all().getIds().forEach(id -> gateway.customer().delete(id));
    }
}
