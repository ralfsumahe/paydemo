package com.lk.pay;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypayConfig {
    @Value("${pay.paypal.client_id}")
    private String clientId;
    @Value("${pay.paypal.client_secret}")
    private String clientSecret;
    @Value("${pay.paypal.is_live}")
    private Boolean isLive;

    @Bean
    PayPalEnvironment payPalEnvironment(){
        if(isLive){
            return new PayPalEnvironment.Live(clientId,clientSecret);
        }else{
            return new PayPalEnvironment.Sandbox(clientId,clientSecret);
        }

    }
    @Bean
    PayPalHttpClient payPalHttpClient(){
        return new PayPalHttpClient(payPalEnvironment());
    }
}
