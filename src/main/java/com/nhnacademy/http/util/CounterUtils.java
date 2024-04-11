package com.nhnacademy.http.util;

import com.nhnacademy.http.context.Context;
import com.nhnacademy.http.context.ContextHolder;

public class CounterUtils {

    public final static String CONTEXT_COUNTER_NAME="Global-Counter";
    private CounterUtils(){}

    public static synchronized long increaseAndGet(){
        /*TODO#7 context에 등록된 CONTEXT_COUNTER_NAME 값을 +1 증가시키고 증가된 값을 반환 합니다.
        * context에 증가된 값을 저장 합니다.
        * */

        return 0l;
    }
}