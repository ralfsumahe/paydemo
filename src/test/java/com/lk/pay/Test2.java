package com.lk.pay;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.junit.Test;

public class Test2 {
    private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
            "z3kj2246rsvttk8w","t9fmnr3mwjzyvrtd","76ca70624230236d62e98cf6f174d424");

    @Test
    public void getCustomer(){
        System.out.println(gateway.customer());
    }

}
