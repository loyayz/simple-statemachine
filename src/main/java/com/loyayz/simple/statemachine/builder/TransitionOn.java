package com.loyayz.simple.statemachine.builder;

import java.util.function.Predicate;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface TransitionOn<S, EVT, CTX> {

    /**
     * 定义执行条件
     *
     * @param condition      执行条件
     * @param throwException 执行条件不通过时是否抛异常
     * @return builder
     */
    TransitionWhen<S, EVT, CTX> when(Predicate<CTX> condition, boolean throwException);

    /**
     * 定义执行条件
     *
     * @param condition 执行条件
     * @return builder
     */
    default TransitionWhen<S, EVT, CTX> when(Predicate<CTX> condition) {
        return this.when(condition, false);
    }

    /**
     * 无执行条件
     *
     * @return builder
     */
    default TransitionWhen<S, EVT, CTX> always() {
        return this.when((ctx) -> true);
    }

}
