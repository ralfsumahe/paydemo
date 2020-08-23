package com.lk.pay.paypal.controller;

import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import com.braintreegateway.SubscriptionRequest;
import com.lk.pay.Msg;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    private PayPalHttpClient payPalHttpClient;

    //==================================================直接支付=========================================================

    /**
     * 创建订单
     * @param product
     * @return
     * @throws IOException
     */
    @PostMapping("/createOrder/{product}")
    public Msg<OrderDto> createOrder(@PathVariable("product") Integer product) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");

        /**
         * {
         *   "intent": "CAPTURE",
         *   "purchase_units": [
         *     {
         *       "amount": {
         *         "currency_code": "USD",
         *         "value": "0.01"
         *       }
         *     }
         *   ]
         * }'
         */

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        orderRequest.purchaseUnits(new ArrayList<PurchaseUnitRequest>());
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest();
        purchaseUnit.amountWithBreakdown(new AmountWithBreakdown().value("0.01").currencyCode("USD"));
        orderRequest.purchaseUnits().add(purchaseUnit);
        request.requestBody(orderRequest);

        HttpResponse<Order> response = payPalHttpClient.execute(request);


        if (response.statusCode() == 201) {
            OrderDto dto = OrderDto.builder()
                    .orderId(response.result().id())
                    .amount(response.result().purchaseUnits().get(0).amountWithBreakdown().value())
                    .build();

            return Msg.ok(dto);
        }else{
            throw new RuntimeException("createOrder出现异常");
        }


    }

    /**
     * 捕获订单
     * @param orderId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/captureOrder")
    public Msg<OrderDto> captureOrder(String orderId) throws IOException, InterruptedException {

        System.out.println("订单id:"+orderId);

        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalHttpClient.execute(request);

        if (response.statusCode() == 201) {
            PurchaseUnit purchaseUnit = response.result().purchaseUnits().get(0);
            OrderDto dto = OrderDto.builder()
                    .orderId(response.result().id())
                    .captureId(response.result().purchaseUnits().get(0).payments().captures().get(0).id()).build();

            System.out.println("dto:"+dto);
            return Msg.ok(dto);
        }else{
            throw new RuntimeException("captureOrder出现异常");
        }
    }

    /**
     * 查询用户订单列表
     */

    /**
     * 订单退款
     * @param orderId
     * @return
     * @throws IOException
     */
    @PostMapping("/refundOrder/{orderId}")
    public Msg<Boolean> refundOrder(@PathVariable("orderId") String orderId) throws IOException {
        OrdersGetRequest ordersGetRequest = new OrdersGetRequest(orderId);
        HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersGetRequest);
        String captureId  = orderHttpResponse.result().purchaseUnits().get(0).payments().captures().get(0).id();

        CapturesRefundRequest capturesRefundRequest = new CapturesRefundRequest(captureId);
        HttpResponse<Refund> refundHttpResponse = payPalHttpClient.execute(capturesRefundRequest);
        if ((refundHttpResponse.statusCode()>=200 && refundHttpResponse.statusCode() < 300)) {
            return Msg.ok(true);
        }else{
            throw new RuntimeException("refundOrder出现异常");
        }

    }



    //=======================================================订阅=======================================================

    /**
     * 获取计划id
     * @param product
     * @return
     */
    @GetMapping("/getPlanId/{product}")
    public Msg<String> getPlanId(@PathVariable("product") Integer product){
        return Msg.ok("P-7DU86670Y0757653ML5ATFMA");
    }

    /**
     * 订阅
     * @return
     */
    @PostMapping("/subscription")
    public Msg<Boolean> subscription(){
        return Msg.ok(true);
    }

    /**
     * 取消订阅
     * @return
     */
    @PostMapping("/unSubscription")
    public Msg<Boolean> unSubscription(){
       return null;
    }

    /**
     * 查询用户订阅列表
     */

    /**
     * 查询用户订阅详细交易
     */




}
