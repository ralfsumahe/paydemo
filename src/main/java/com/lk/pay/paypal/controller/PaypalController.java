package com.lk.pay.paypal.controller;

import com.lk.pay.Msg;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    private PayPalHttpClient payPalHttpClient;


    @PostMapping("/createOrder")
    public Msg<OrderDto> createOrder() throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");

        /**
         * {
         *   "intent": "CAPTURE",
         *   "purchase_units": [
         *     {
         *       "amount": {
         *         "currency_code": "USD",
         *         "value": "100.00"
         *       }
         *     }
         *   ]
         * }'
         */

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        orderRequest.purchaseUnits(new ArrayList<PurchaseUnitRequest>());
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest();
        purchaseUnit.amountWithBreakdown(new AmountWithBreakdown().value("100.00").currencyCode("USD"));
        orderRequest.purchaseUnits().add(purchaseUnit);
        request.requestBody(orderRequest);

        HttpResponse<Order> response = payPalHttpClient.execute(request);

        //响应编码
        System.out.println(response.statusCode());

        OrderDto dto = OrderDto.builder()
                .orderId(response.result().id())
                .amount(response.result().purchaseUnits().get(0).amountWithBreakdown().value())
                .build();

        System.out.println("订单id:"+dto.getOrderId());

        return Msg.ok(dto);

    }

    @PostMapping("/captureOrder")
    public Msg<OrderDto> captureOrder(String orderId) throws IOException, InterruptedException {

        System.out.println("订单id:"+orderId);

        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalHttpClient.execute(request);

        //响应返回编码
        System.out.println(response.statusCode());

        System.out.println(response.result().id());

        PurchaseUnit purchaseUnit = response.result().purchaseUnits().get(0);
        OrderDto dto = OrderDto.builder()
                .orderId(response.result().id()).build();

        return Msg.ok(dto);
    }


}
