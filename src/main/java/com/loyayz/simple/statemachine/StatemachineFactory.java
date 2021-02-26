package com.loyayz.simple.statemachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@SuppressWarnings("all")
public final class StatemachineFactory {
    private final static Map<String, Statemachine> MACHINE_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取状态机
     */
    public static <S, EVT, CTX> Statemachine<S, EVT, CTX> get(String machineId) {
        Statemachine machine = MACHINE_CACHE.get(machineId);
        if (machine == null) {
            throw new IllegalArgumentException("查无状态机：" + machineId);
        }
        return machine;
    }

    /**
     * 删除状态机
     */
    public static void remove(String machineId) {
        MACHINE_CACHE.remove(machineId);
    }

    /**
     * 注册状态机
     */
    static <S, EVT, CTX> void register(String machineId, Statemachine<S, EVT, CTX> machine) {
        if (MACHINE_CACHE.get(machineId) != null) {
            throw new IllegalArgumentException("已存在状态机id：" + machineId);
        }
        MACHINE_CACHE.put(machineId, machine);
    }

}
