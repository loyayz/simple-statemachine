package com.loyayz.simple.statemachine.builder;

import java.util.function.Predicate;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionOn<S, EVT, CTX> {

    /**
     * 定义执行条件
     *
     * @param condition 执行条件
     * @return builder
     */
    TransitionWhen<S, EVT, CTX> when(Predicate<CTX> condition);

}
