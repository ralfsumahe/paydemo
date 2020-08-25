package com.lk.pay.paypal.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalOrderDto {
    private String paypalOrderId;
    private String amount;
    private String paypalCaptureId;
    private String subscriptionId;
}
