package com.loyayz.simple.statemachine;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Predicate;

/**
 * 状态机流转过程
 *
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
public class Transition<S, EVT, CTX> {

    /**
     * 原状态
     */
    private S source;
    /**
     * 目标状态
     */
    private S target;
    /**
     * 触发事件
     */
    private EVT event;
    /**
     * 执行条件
     */
    private Predicate<CTX> condition;
    /**
     * 执行方法
     */
    private Action<S, EVT, CTX> action;

    /**
     * 流转
     *
     * @param context 执行上下文
     * @return 目标状态
     * @throws StatemachineException 当不满足执行条件时抛异常
     */
    public S transit(CTX context) {
        if (condition == null || condition.test(context)) {
            if (action != null) {
                action.execute(source, target, event, context);
            }
            return target;
        }
        throw new StatemachineException("Transition condition is not satisfied.");
    }

    public Transition(Transition<S, EVT, CTX> other) {
        this.source = other.source();
        this.target = other.target();
        this.event = other.event();
        this.condition = other.condition();
        this.action = other.action();
    }

}
