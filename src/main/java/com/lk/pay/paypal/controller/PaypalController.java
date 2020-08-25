package com.lk.pay.paypal.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.lk.pay.Msg;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param paypalCreateOrderVo
     * @return
     * @throws IOException
     */
    @PostMapping("/createOrder")
    public Msg<OrderDto> createOrder(@RequestBody PaypalCreateOrderVo paypalCreateOrderVo, HttpServletRequest httpServletRequest, HttpServletResponse httpresponse) throws IOException {
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        httpresponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");


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
                    .orderStatus(response.result().status())
                    .amount(response.result().purchaseUnits().get(0).amountWithBreakdown().value())
                    .build();
            System.out.println(dto);

            return Msg.ok(dto);
        }else{
            throw new RuntimeException("createOrder出现异常");
        }


    }

    /**
     * 捕获订单
     * @param paypalCaptureOrderVo
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/captureOrder")
    public Msg<OrderDto> captureOrder(@RequestBody PaypalCaptureOrderVo paypalCaptureOrderVo, HttpServletRequest httpServletRequest, HttpServletResponse httpresponse) throws IOException, InterruptedException {
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        httpresponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));


        System.out.println("订单id:"+paypalCaptureOrderVo.getPaypalOrderId());

        OrdersCaptureRequest request = new OrdersCaptureRequest(paypalCaptureOrderVo.getPaypalOrderId());
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalHttpClient.execute(request);


        if (response.statusCode() == 201) {
            PurchaseUnit purchaseUnit = response.result().purchaseUnits().get(0);
            OrderDto dto = OrderDto.builder()
                    .orderId(response.result().id())
                    .orderStatus(response.result().status())
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
     * @param paypalRefundOrderVo
     * @return
     * @throws IOException
     */
    @PostMapping("/refundOrder")
    public Msg<Boolean> refundOrder(@RequestBody PaypalRefundOrderVo paypalRefundOrderVo, HttpServletRequest httpServletRequest, HttpServletResponse httpresponse) throws IOException {
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        httpresponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));

        OrdersGetRequest ordersGetRequest = new OrdersGetRequest(paypalRefundOrderVo.getPaypalOrderId());
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
     * 订阅
     * @return
     */
    @PostMapping("/createSubscription")
    public Msg<Boolean> createSubscription(@RequestBody PaypalCreateSubscriptionVo paypalCreateSubscriptionVo, HttpServletRequest httpServletRequest, HttpServletResponse httpresponse){
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        httpresponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));

        //P-7DU86670Y0757653ML5ATFMA


        JSONObject jo = new JSONObject();
        jo.put("plan_id","P-7DU86670Y0757653ML5ATFMA");
        HttpRequest request = HttpRequest.post("https://api.sandbox.paypal.com/v1/billing/subscriptions")
                .contentType("application/json")
                .header("Authorization","Bearer "+getToken())
                .header("PayPal-Request-Id","SUBSCRIPTION-21092019-001")
                .body(jo.toJSONString());

        cn.hutool.http.HttpResponse httpResponse = request.execute();
        JSONObject result = (JSONObject)JSONObject.parse(httpResponse.body());
        String subscriptionId = result.getString("id");
        OrderDto dto = OrderDto.builder().subscriptionId(subscriptionId).build();

        return Msg.ok(dto);
    }

    /**
     * 取消订阅
     * @return
     */
    @PostMapping("/unSubscription")
    public Msg<Boolean> unSubscription(HttpServletRequest httpServletRequest, HttpServletResponse httpresponse){
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        httpresponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));

       return null;
    }

    /**
     * 查询用户订阅列表
     */

    /**
     * 查询用户订阅详细交易
     */



    private String getToken(){
        String token = null;
        HttpRequest request = HttpRequest.post("https://api.sandbox.paypal.com/v1/oauth2/token").basicAuth(
                "ATN8f09gmB9Njm0iuoyApCV04GXbhbfltRzQPMHtjEL3i2oIZO0ShB31nIczY_hK1W0bVbYRUQaKIer6",
                "EGQzXV9F46OEBFBZHNkk04CAIXRwZW1r4K6X40anp2RKL4dYa4q-11qLxcuW1fyHZ-DA01Y9Oa_xC86j")
                .form("grant_type", "client_credentials");
        cn.hutool.http.HttpResponse response = request.execute();
        JSONObject result = (JSONObject)JSONObject.parse(response.body());
        token = result.getString("access_token");
        return token;
    }



}
