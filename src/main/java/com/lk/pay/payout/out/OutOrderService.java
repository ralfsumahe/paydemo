package com.lk.pay.payout.out;

import com.lk.pay.payout.OrderCallbackService;
import com.lk.pay.payout.OrderService;

/**
 * 外部订单服务
 */
public interface OutOrderService extends OrderService, OrderCallbackService {
}
