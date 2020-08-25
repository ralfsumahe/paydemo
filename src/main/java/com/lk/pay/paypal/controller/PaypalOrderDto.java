package com.lk.pay.paypal.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalOrderDto {
    /**
     * paypal订单id
     */
    private String paypalOrderId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 金额
     */
    private String amount;
    /**
     * paypal捕获id
     */
    private String paypalCaptureId;
    /**
     * 是否完成交易
     */
    private Boolean isComplete;
    /**
     * 状态
     */
    private String status;
}
