package com.lk.pay.utils;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

public class GatewayFactory {

    public static BraintreeGateway getlisiGateway() {
        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
                "f6tw2cm2c24yfbs7", "ds8szsps6fc9tjky", "bf4a3e7971a8e4be2dada2f7ca19573a");
        return gateway;
    }

    public static BraintreeGateway getBluerulerGateway(){
        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
                "z3kj2246rsvttk8w", "t9fmnr3mwjzyvrtd", "76ca70624230236d62e98cf6f174d424");
        return gateway;
    }

}
