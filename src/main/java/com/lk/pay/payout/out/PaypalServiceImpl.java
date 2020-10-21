package com.lk.pay.payout.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * paypal支付服务实现
 */
@Service
public class PaypalServiceImpl implements OutPayService{
    @Autowired
    private PayServiceTemplate payServiceTemplate;


    /**
     * 回调完成订单
     *
     * @param callbackFinishOrderVo
     * @return
     */
    @Override
    public Object callbackFinishOrder(Object callbackFinishOrderVo) {
        return null;
    }

    /**
     * 回调取消订单
     *
     * @param callbackCancelOrderVo
     * @return
     */
    @Override
    public Object callbackCancelOrder(Object callbackCancelOrderVo) {
        return null;
    }

    /**
     * 创建订单
     *
     * @param creatOrderVo
     * @return
     */
    @Override
    public Object createOrder(Object creatOrderVo) {
        Object out = payServiceTemplate.exec(creatOrderVo,vo->{
          return "1";
        },in->{
            System.out.println(in);
            return "in";
        },o->{
            System.out.println(o);
            return "o";
        });
        return out;
    }

    /**
     * 支付订单
     *
     * @param payOrderVo
     * @return
     */
    @Override
    public Object payOrder(Object payOrderVo) {
        return null;
    }

    /**
     * 取消订单
     *
     * @param cancelOrderVo
     * @return
     */
    @Override
    public Object cancelOrder(Object cancelOrderVo) {
        return null;
    }

    /**
     * 回调完成订阅
     *
     * @param callbackFinishSubscriptVo
     * @return
     */
    @Override
    public Object callbackFinishSubscript(Object callbackFinishSubscriptVo) {
        return null;
    }

    /**
     * 回调取消订阅
     *
     * @param callbackCancelSubscriptVo
     * @return
     */
    @Override
    public Object callbackCancelSubscript(Object callbackCancelSubscriptVo) {
        return null;
    }

    /**
     * 回调完成订阅支付
     *
     * @param callbackFinishSubscriptTranVo
     * @return
     */
    @Override
    public Object callbackFinishSubscriptTran(Object callbackFinishSubscriptTranVo) {
        return null;
    }

    /**
     * 回调取消订阅支付
     *
     * @param callbackCancelSubscriptTranVo
     * @return
     */
    @Override
    public Object callbackCancelSubscriptTran(Object callbackCancelSubscriptTranVo) {
        return null;
    }

    /**
     * 创建订阅
     *
     * @param createSubscriptVo
     * @return
     */
    @Override
    public Object createSubscript(Object createSubscriptVo) {
        return null;
    }

    /**
     * 支付订阅
     *
     * @param paySubscriptVo
     * @return
     */
    @Override
    public Object paySubscript(Object paySubscriptVo) {
        return null;
    }

    /**
     * 取消订阅
     *
     * @param cancelSubscriptVo
     * @return
     */
    @Override
    public Object cancelSubscript(Object cancelSubscriptVo) {
        return null;
    }

    /**
     * 取消订阅支付
     *
     * @param cancelSubscriptTran
     * @return
     */
    @Override
    public Object cancelSubscriptTran(Object cancelSubscriptTran) {
        return null;
    }
}
