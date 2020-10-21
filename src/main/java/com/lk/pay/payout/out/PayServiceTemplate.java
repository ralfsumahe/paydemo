package com.lk.pay.payout.out;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PayServiceTemplate<I extends Object,IN extends Object,O extends Object,OUT extends Object>{
    public Object exec(Object vo, Function<I,IN> getIn,Function<IN,O> exec,Function<O,OUT> getOut){
        System.out.println("输入参数为："+vo);
        Object params = getIn.apply((I) vo);
        System.out.println("修改参数为："+params);
        Object result = exec.apply((IN) params);
        System.out.println("执行结果："+result);
        Object out = getOut.apply((O) result);
        System.out.println("修改结果后："+out);
        return out;
    }
}
