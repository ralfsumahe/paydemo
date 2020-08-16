package com.lk.pay;

import com.alibaba.fastjson.JSON;
import com.braintreegateway.*;

public class Test {
    private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
            "z3kj2246rsvttk8w","t9fmnr3mwjzyvrtd","76ca70624230236d62e98cf6f174d424");

    @org.junit.Test
    public void customerTest(){
//        gateway.customer().all().getIds().forEach(
//                id -> System.out.println(JSON.toJSONString(gateway.customer().delete(id))));

        System.out.println(gateway.customer().all().getIds());

        CustomerRequest customerRequest = new CustomerRequest()
                .id("myid5")
                .firstName("Mark5")
                .lastName("Jones5")
                .company("Jones5 Co.")
                .email("mark5.jones@example.com")
                .fax("429-555-1234")
                .phone("624-555-1234")
                .website("http://example5.com");
        Result<Customer> customerResult = gateway.customer().create(customerRequest);

        System.out.println(gateway.customer().all().getIds());

        System.out.println(JSON.toJSONString(gateway.subscription().search(new SubscriptionSearchRequest().merchantAccountId().is("z3kj2246rsvttk8w"))));
    }
}
