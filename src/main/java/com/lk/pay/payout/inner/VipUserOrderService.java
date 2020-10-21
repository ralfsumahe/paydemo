package com.lk.pay.payout.inner;

import com.lk.pay.payout.out.OutPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * vip用户订单服务
 */
@Service
public class VipUserOrderService implements InnerOrderSubscriptService{
    @Autowired
    private OutPayService outPayService;


    /**
     * 创建订单
     *
     * @param creatOrderVo
     * @return
     */
    @Override
    public Object createOrder(Object creatOrderVo) {
        return outPayService.createOrder(creatOrderVo);
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
