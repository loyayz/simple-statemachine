package com.loyayz.simple.statemachine;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@FunctionalInterface
public interface Action<S, EVT, CTX> {

    /**
     * 执行方法
     *
     * @param source  原状态
     * @param target  目标状态
     * @param event   触发事件
     * @param context 执行上下文
     */
    void execute(S source, S target, EVT event, CTX context);

}
