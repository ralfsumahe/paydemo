package com.lk.pay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
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
    public void listTrans4Subscript(){
        HttpRequest request = HttpRequest.get("https://api.sandbox.paypal.com/v1/billing/subscriptions/I-TMJY77SNCUA7/transactions?start_time=2020-09-01T07:50:20.940Z&end_time=2020-09-15T07:50:20.940Z")
                .contentType("application/json")
                .header("Authorization", "Bearer "+token);
        HttpResponse response = request.execute();
        System.out.println(response.body());
    }
}
