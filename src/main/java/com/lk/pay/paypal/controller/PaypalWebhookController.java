package com.lk.pay.paypal.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/pay/paypalwebhook")
@Slf4j
public class PaypalWebhookController {


    @RequestMapping("/handler")
    public Object handle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String str = HttpUtil.getString(request.getInputStream(), Charset.forName("utf-8"),true);
        JSONObject jo = (JSONObject) JSONObject.parse(str);
        String event_type = jo.getString("event_type");
        if(event_type.equals("PAYMENT.CAPTURE.COMPLETED")){
            //订单完成

            log.info("付款捕获完成....");
            String captureId = jo.getJSONObject("resource").getString("id");
            String status = jo.getJSONObject("resource").getString("status");
            if("COMPLETED".equals(status)){
                //订单完成，前往完成订单操作


            }
        }else if(event_type.equals("CHECKOUT.ORDER.APPROVED")){
            //创建订单
            log.info("结帐订单已获买方批准....");

        }else if(event_type.equals("BILLING.SUBSCRIPTION.CREATED")){
            //订阅创建
            log.info("创建了计费协议....");

            String subscriptionId = jo.getJSONObject("resource").getString("id");
            String planId = jo.getJSONObject("resource").getString("plan_id");
            //APPROVAL_PENDING
            String status = jo.getJSONObject("resources").getString("status");

        }else if(event_type.equals("BILLING.SUBSCRIPTION.ACTIVATED")){
            //订阅生效
            log.info("订阅已激活.....");

            JSONObject resourceJo = jo.getJSONObject("resource");
            String subscriptionId = resourceJo.getString("id");
            String planId = resourceJo.getString("plan_id");
            //ACTIVE
            String status = resourceJo.getString("status");
            String status_update_time = resourceJo.getString("status_update_time");
        }else if(event_type.equals("PAYMENT.SALE.COMPLETED")){
            //订阅支付
            log.info("订阅时付款...");

            JSONObject resourceJo = jo.getJSONObject("resource");
            String subscriptionId = resourceJo.getString("billing_agreement_id");
            //completed
            String state = resourceJo.getString("state");
            String id = resourceJo.getString("id");
        }




        log.info(jo.getString("id"));
        response.setStatus(200);
        if(event_type.equals("CHECKOUT.ORDER.APPROVED")){
            response.setStatus(500);
        }
        return str;
    }

    @PostMapping("/verify")
    public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String str = HttpUtil.getString(request.getInputStream(), Charset.forName("utf-8"),true);
        JSONObject jo = (JSONObject) JSONObject.parse(str);

        HttpRequest httpRequest = HttpRequest.post("https://api.sandbox.paypal.com/v1/notifications/verify-webhook-signature")
                .contentType("application/json")
                .header("Authorization", "Bearer "+getToken())
                .body(jo.toJSONString());


        HttpResponse httpResponse = httpRequest.execute();
        System.out.println(httpResponse.body());
    }


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
