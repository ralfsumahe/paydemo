package com.lk.pay.payout;

import java.util.function.Function;

/**
 * 订阅回调
 */
public interface SubscriptCallbackService{
    /**
     * 回调完成订阅
     * @param callbackFinishSubscriptVo
     * @return
     */
    public Object callbackFinishSubscript(Object callbackFinishSubscriptVo);

    /**
     * 回调取消订阅
     * @param callbackCancelSubscriptVo
     * @return
     */
    public Object callbackCancelSubscript(Object callbackCancelSubscriptVo);

    /**
     * 回调完成订阅支付
     * @param callbackFinishSubscriptTranVo
     * @return
     */
    public Object callbackFinishSubscriptTran(Object callbackFinishSubscriptTranVo);

    /**
     * 回调取消订阅支付
     * @param callbackCancelSubscriptTranVo
     * @return
     */
    public Object callbackCancelSubscriptTran(Object callbackCancelSubscriptTranVo);
}
