package com.loyayz.simple.statemachine;

/**
 * 状态机
 *
 * @param <S>   状态
 * @param <EVT> 触发事件
 * @param <CTX> 执行上下文
 * @author loyayz (loyayz@foxmail.com)
 */
public interface Statemachine<S, EVT, CTX> {

    /**
     * 执行
     *
     * @param source  原状态
     * @param event   触发事件
     * @param context 执行上下文
     * @return 目标状态
     * @throws StatemachineException 当查无流转过程或不满足执行条件时抛异常
     */
    S execute(S source, EVT event, CTX context);

    /**
     * 注册状态机
     *
     * @param machineId id
     * @return 当前状态机
     */
    default Statemachine<S, EVT, CTX> register(String machineId) {
        StatemachineFactory.register(machineId, this);
        return this;
    }

}
