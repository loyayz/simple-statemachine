package com.loyayz.simple.statemachine.builder;

import com.loyayz.simple.statemachine.Action;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionWhen<S, EVT, CTX> {

    /**
     * 定义执行方法
     *
     * @param action 执行方法
     * @return builder
     */
    StatemachineBuilder<S, EVT, CTX> perform(Action<S, EVT, CTX> action);

}
