package com.lk.pay.braintree.controller;


import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.WebhookNotification;
import com.lk.pay.utils.GatewayFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/webhook")
public class WebHookController {
    private static BraintreeGateway gateway = GatewayFactory.getlisiGateway();

    /**
     * 在网关中触发Webhook时，通知将作为POST请求发送到指定的目标URL。帖子正文包含两个x-www-form-urlencoded参数：
     * bt_signature
     * bt_payload
     * @param bt_signature
     * @param bt_payload
     */
    @RequestMapping("/handle")
    public Object handle(String bt_signature,String bt_payload){
        WebhookNotification webhookNotification = gateway.webhookNotification().parse(bt_signature,bt_payload);


        System.out.println(webhookNotification.getKind());
        System.out.println(webhookNotification.getTimestamp());

        if (webhookNotification.getKind().equals(WebhookNotification.Kind.SUBSCRIPTION_CHARGED_SUCCESSFULLY)) {
            //webhookNotification.getSubscription();
            System.out.println("订阅成功");
        }else if(webhookNotification.getKind().equals(WebhookNotification.Kind.SUBSCRIPTION_CHARGED_UNSUCCESSFULLY)){
            System.out.println("订阅失败");
        }else if(webhookNotification.getKind().equals(WebhookNotification.Kind.LOCAL_PAYMENT_COMPLETED)){
            //webhookNotification.getLocalPaymentCompleted();
            System.out.println("用户付款完成");
        }else if(webhookNotification.getKind().equals(WebhookNotification.Kind.TRANSACTION_SETTLED)){
            System.out.println("结账成功");
        }else if (webhookNotification.getKind().equals(WebhookNotification.Kind.TRANSACTION_SETTLEMENT_DECLINED)) {
            System.out.println("结账被拒绝");
        }

        return "";
    }





}
