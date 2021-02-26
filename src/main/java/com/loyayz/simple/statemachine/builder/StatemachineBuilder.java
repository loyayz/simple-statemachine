package com.loyayz.simple.statemachine.builder;

import com.loyayz.simple.statemachine.Statemachine;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface StatemachineBuilder<S, EVT, CTX> {

    /**
     * 构建新的流转过程
     *
     * @return builder
     */
    TransitionRegister<S, EVT, CTX> newTransition();

    /**
     * 构建状态机
     *
     * @return 状态机
     */
    Statemachine<S, EVT, CTX> build();

    /**
     * 创建一个新的 builder
     *
     * @param <S>   状态
     * @param <EVT> 触发事件
     * @param <CTX> 执行上下文
     * @return builder
     */
    static <S, EVT, CTX> StatemachineBuilder<S, EVT, CTX> create() {
        return new BuilderImpl<>();
    }

}
