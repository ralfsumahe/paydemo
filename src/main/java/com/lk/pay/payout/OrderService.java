package com.lk.pay.payout;

import java.util.function.Function;

/**
 * 订单服务
 */
public interface OrderService {
    /**
     * 创建订单
     * @param creatOrderVo
     * @return
     */
    public Object createOrder(Object creatOrderVo);

    /**
     * 支付订单
     * @param payOrderVo
     * @return
     */
    public Object payOrder(Object payOrderVo);

    /**
     * 取消订单
     * @param cancelOrderVo
     * @return
     */
    public Object cancelOrder(Object cancelOrderVo);
}
