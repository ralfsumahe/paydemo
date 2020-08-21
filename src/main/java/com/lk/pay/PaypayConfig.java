package com.lk.pay;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypayConfig {
    @Bean
    PayPalEnvironment payPalEnvironment(){
        String clientId = "ATN8f09gmB9Njm0iuoyApCV04GXbhbfltRzQPMHtjEL3i2oIZO0ShB31nIczY_hK1W0bVbYRUQaKIer6";
        String clientSecret = "EGQzXV9F46OEBFBZHNkk04CAIXRwZW1r4K6X40anp2RKL4dYa4q-11qLxcuW1fyHZ-DA01Y9Oa_xC86j";
        return new PayPalEnvironment.Sandbox(clientId,clientSecret);
    }
    @Bean
    PayPalHttpClient payPalHttpClient(){
        return new PayPalHttpClient(payPalEnvironment());
    }
}
