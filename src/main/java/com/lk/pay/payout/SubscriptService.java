package com.lk.pay.payout;

import java.util.function.Function;

/**
 * 订阅服务
 */
public interface SubscriptService {
    /**
     * 创建订阅
     * @param createSubscriptVo
     * @return
     */
    public Object createSubscript(Object createSubscriptVo);

    /**
     * 支付订阅
     * @param paySubscriptVo
     * @return
     */
    public Object paySubscript(Object paySubscriptVo);

    /**
     * 取消订阅
     * @param cancelSubscriptVo
     * @return
     */
    public Object cancelSubscript(Object cancelSubscriptVo);

    /**
     * 取消订阅支付
     * @param cancelSubscriptTran
     * @return
     */
    public Object cancelSubscriptTran(Object cancelSubscriptTran);
}
