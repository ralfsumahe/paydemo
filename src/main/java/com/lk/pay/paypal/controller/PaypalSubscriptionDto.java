package com.lk.pay.paypal.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalSubscriptionDto {
    private String paypalOrderId;
    private String amount;
    private String paypalCaptureId;
    private String paypalSubscriptionId;
}
