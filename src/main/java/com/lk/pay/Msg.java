package com.lk.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Msg {
    private Integer code;
    private String msg;

    public static Msg ok(String msg){
        return Msg.builder().code(0).msg(msg).build();
    }
    public static Msg fail(String msg){
        return Msg.builder().code(-1).msg(msg).build();
    }
}
