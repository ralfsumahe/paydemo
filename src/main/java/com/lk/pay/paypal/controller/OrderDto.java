package com.lk.pay.paypal.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
class OrderDto{
    private String orderId;
    private String orderStatus;
    private String amount;
    private String captureId;
    private String subscriptionId;
}
