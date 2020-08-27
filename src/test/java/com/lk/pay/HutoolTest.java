package com.lk.pay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class HutoolTest {
    private String token = null;
    private Integer expires_in = null;


    @Before
    public void getToken(){
        HttpRequest request = HttpRequest.post("https://api.sandbox.paypal.com/v1/oauth2/token").basicAuth(
                "ATN8f09gmB9Njm0iuoyApCV04GXbhbfltRzQPMHtjEL3i2oIZO0ShB31nIczY_hK1W0bVbYRUQaKIer6",
                "EGQzXV9F46OEBFBZHNkk04CAIXRwZW1r4K6X40anp2RKL4dYa4q-11qLxcuW1fyHZ-DA01Y9Oa_xC86j")
                .form("grant_type", "client_credentials");
        HttpResponse response = request.execute();
        JSONObject result = (JSONObject)JSONObject.parse(response.body());
        token = result.getString("access_token");
        expires_in = result.getInteger("expires_in");
        System.out.println(token);
        System.out.println(expires_in);//秒
    }

    @Test
    public void getPlans(){
        JSONObject jo = new JSONObject();
        HttpRequest request = HttpRequest.get("https://api.sandbox.paypal.com/v1/billing/plans?product_id=200001&page_size=2&page=1&total_required=true")
                .header("Authorization", "Bearer "+token);
        System.out.println(request.execute());
    }

    @Test
    public void getSubscription(){
        //I-E15WYNNVPG7Y
        HttpRequest httpRequest = HttpRequest.get("https://api.sandbox.paypal.com/v1/billing/subscriptions/I-E15WYNNVPG7Y")
                .contentType("application/json")
                .header("Authorization", "Bearer "+token);
        System.out.println(httpRequest.execute());
    }

    @Test
    public void unSubscription(){
        JSONObject jo = new JSONObject();
        jo.put("reason", "Not satisfied with the service");

        HttpRequest httpRequest = HttpRequest.post("https://api.sandbox.paypal.com/v1/billing/subscriptions/I-E15WYNNVPG7Y/cancel")
                .contentType("application/json")
                .header("Authorization", "Bearer "+token)
                .body(jo.toJSONString());
        System.out.println(httpRequest.execute());
    }
    @Test
    public void testTime(){
        String str = "2020-08-25T08:19:33.585Z";
        Date date = DateUtil.parse(str,"yyyy-MM-ddHH:mm:ss.SSSS".replace("/T|Z/",""));
        System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss,SSS"));
    }

    @Test
    public void verifyWebhook(){
        String str = "{\n" +
                "\t\"id\": \"WH-4HF8086842566993R-8FT79107MB511194C\",\n" +
                "\t\"create_time\": \"2020-08-26T01:21:33.749Z\",\n" +
                "\t\"resource_type\": \"sale\",\n" +
                "\t\"event_type\": \"PAYMENT.SALE.COMPLETED\",\n" +
                "\t\"summary\": \"Payment completed for $ 29.0 USD\",\n" +
                "\t\"resource\": {\n" +
                "\t\t\"billing_agreement_id\": \"I-VDJL29L70DBS\",\n" +
                "\t\t\"amount\": {\n" +
                "\t\t\t\"total\": \"29.00\",\n" +
                "\t\t\t\"currency\": \"USD\",\n" +
                "\t\t\t\"details\": {\n" +
                "\t\t\t\t\"subtotal\": \"29.00\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"payment_mode\": \"INSTANT_TRANSFER\",\n" +
                "\t\t\"update_time\": \"2020-08-26T01:21:03Z\",\n" +
                "\t\t\"create_time\": \"2020-08-26T01:21:03Z\",\n" +
                "\t\t\"protection_eligibility_type\": \"ITEM_NOT_RECEIVED_ELIGIBLE,UNAUTHORIZED_PAYMENT_ELIGIBLE\",\n" +
                "\t\t\"transaction_fee\": {\n" +
                "\t\t\t\"currency\": \"USD\",\n" +
                "\t\t\t\"value\": \"1.14\"\n" +
                "\t\t},\n" +
                "\t\t\"protection_eligibility\": \"ELIGIBLE\",\n" +
                "\t\t\"links\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\t\"rel\": \"self\",\n" +
                "\t\t\t\t\"href\": \"https://api.sandbox.paypal.com/v1/payments/sale/3VK40080KA252640P\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"method\": \"POST\",\n" +
                "\t\t\t\t\"rel\": \"refund\",\n" +
                "\t\t\t\t\"href\": \"https://api.sandbox.paypal.com/v1/payments/sale/3VK40080KA252640P/refund\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"id\": \"3VK40080KA252640P\",\n" +
                "\t\t\"state\": \"completed\",\n" +
                "\t\t\"invoice_number\": \"\"\n" +
                "\t},\n" +
                "\t\"status\": \"SUCCESS\",\n" +
                "\t\"transmissions\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"webhook_url\": \"https://us-west.api.yesoulchina.cn/pay/paypalwebhook/handler\",\n" +
                "\t\t\t\"response_headers\": {\n" +
                "\t\t\t\t\"Server\": \"nginx\",\n" +
                "\t\t\t\t\"Connection\": \"keep-alive\",\n" +
                "\t\t\t\t\"Content-Length\": \"1228\",\n" +
                "\t\t\t\t\"Date\": \"Wed, 26 Aug 2020 01:22:13 GMT\",\n" +
                "\t\t\t\t\"Content-Type\": \"text/plain;charset=ISO-8859-1\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"transmission_id\": \"8c7022a0-e73a-11ea-9696-495bf873bcee\",\n" +
                "\t\t\t\"status\": \"SUCCESS\",\n" +
                "\t\t\t\"timestamp\": \"2020-08-26T01:22:03Z\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"links\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"href\": \"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-4HF8086842566993R-8FT79107MB511194C\",\n" +
                "\t\t\t\"rel\": \"self\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"encType\": \"application/json\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"href\": \"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-4HF8086842566993R-8FT79107MB511194C/resend\",\n" +
                "\t\t\t\"rel\": \"resend\",\n" +
                "\t\t\t\"method\": \"POST\",\n" +
                "\t\t\t\"encType\": \"application/json\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"event_version\": \"1.0\"\n" +
                "}";


        JSONObject jo = new JSONObject();
        jo.put("reason", "Not satisfied with the service");
        HttpRequest httpRequest = HttpRequest.post("https://api.sandbox.paypal.com/v1/notifications/verify-webhook-signature")
                .contentType("application/json")
                .header("Authorization","Bearer "+token)
                .body(jo.toJSONString());

        HttpResponse httpResponse = httpRequest.execute();
        System.out.println(httpResponse);
    }
}
