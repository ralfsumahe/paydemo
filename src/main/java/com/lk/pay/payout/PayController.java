package com.lk.pay.payout;

import com.lk.pay.payout.inner.VipUserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private VipUserOrderService vipUserOrderService;

    @RequestMapping("createOrder")
    public void createOrder(){
        vipUserOrderService.createOrder("createOrderVo");
    }
}
