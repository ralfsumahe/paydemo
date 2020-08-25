package com.lk.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Msg<T> {
    private Integer code;
    private T data;

    public static <T> Msg ok(T msg){
        return Msg.builder().code(0).data(msg).build();
    }
    public static <T> Msg fail(T msg){
        return Msg.builder().code(-1).data(msg).build();
    }
}
