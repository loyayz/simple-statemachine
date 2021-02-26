package com.loyayz.simple.statemachine.builder;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionRegister<S, EVT, CTX> {

    /**
     * 定义原状态
     * 状态在内部流转，即执行流转后停留在原状态
     *
     * @param state 原状态
     * @return builder
     */
    TransitionAt<S, EVT, CTX> at(S state);

    /**
     * 定义原状态
     *
     * @param states 原状态
     * @return builder
     */
    TransitionFrom<S, EVT, CTX> from(S... states);

}
