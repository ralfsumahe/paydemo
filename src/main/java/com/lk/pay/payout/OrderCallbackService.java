package com.lk.pay.payout;

import java.util.function.Function;

/**
 * 订单回调服务
 */
public interface OrderCallbackService{
    /**
     * 回调完成订单
     * @param callbackFinishOrderVo
     * @return
     */
    public Object callbackFinishOrder(Object callbackFinishOrderVo);

    /**
     * 回调取消订单
     * @param callbackCancelOrderVo
     * @return
     */
    public Object callbackCancelOrder(Object callbackCancelOrderVo);
}
