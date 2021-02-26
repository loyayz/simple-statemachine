package com.loyayz.simple.statemachine.builder;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionFrom<S, EVT, CTX> {

    /**
     * 定义目标状态
     *
     * @param state 目标状态
     * @return builder
     */
    TransitionTo<S, EVT, CTX> to(S state);

}
