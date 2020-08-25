package com.lk.pay.paypal.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalSubscriptionDto {
    private String amount;
    private String productName;
    private String paypalSubscriptionId;
    private Boolean isComplete;
    private String status;
}
