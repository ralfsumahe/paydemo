package com.lk.pay.payout.out;

import java.util.function.Function;

/**
 * 外部支付服务
 */
public interface OutPayImplService<I extends Object,IN extends Object,O extends Object,OUT extends Object>{
    /**
     * 回调完成订单
     *
     * @param callbackFinishOrderVo
     * @return
     */
    public Object callbackFinishOrder(Object callbackFinishOrderVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 回调取消订单
     *
     * @param callbackCancelOrderVo
     * @return
     */
    public Object callbackCancelOrder(Object callbackCancelOrderVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 创建订单
     *
     * @param creatOrderVo
     * @return
     */
    public Object createOrder(Object creatOrderVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 支付订单
     *
     * @param payOrderVo
     * @return
     */
    public Object payOrder(Object payOrderVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 取消订单
     *
     * @param cancelOrderVo
     * @return
     */
    public Object cancelOrder(Object cancelOrderVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 回调完成订阅
     *
     * @param callbackFinishSubscriptVo
     * @return
     */
    public Object callbackFinishSubscript(Object callbackFinishSubscriptVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 回调取消订阅
     *
     * @param callbackCancelSubscriptVo
     * @return
     */
    public Object callbackCancelSubscript(Object callbackCancelSubscriptVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 回调完成订阅支付
     *
     * @param callbackFinishSubscriptTranVo
     * @return
     */
    public Object callbackFinishSubscriptTran(Object callbackFinishSubscriptTranVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 回调取消订阅支付
     *
     * @param callbackCancelSubscriptTranVo
     * @return
     */
    public Object callbackCancelSubscriptTran(Object callbackCancelSubscriptTranVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 创建订阅
     *
     * @param createSubscriptVo
     * @return
     */
    public Object createSubscript(Object createSubscriptVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 支付订阅
     *
     * @param paySubscriptVo
     * @return
     */
    public Object paySubscript(Object paySubscriptVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 取消订阅
     *
     * @param cancelSubscriptVo
     * @return
     */
    public Object cancelSubscript(Object cancelSubscriptVo, Function<I,IN> getIN,Function<O,OUT> getOut);

    /**
     * 取消订阅支付
     *
     * @param cancelSubscriptTran
     * @return
     */
    public Object cancelSubscriptTran(Object cancelSubscriptTran, Function<I,IN> getIN,Function<O,OUT> getOut);
}
