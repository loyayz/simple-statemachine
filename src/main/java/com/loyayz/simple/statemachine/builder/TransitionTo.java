package com.loyayz.simple.statemachine.builder;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionTo<S, EVT, CTX> {

    /**
     * 定义触发事件
     *
     * @param event 触发事件
     * @return builder
     */
    TransitionOn<S, EVT, CTX> on(EVT event);

}
