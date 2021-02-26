package com.loyayz.simple.statemachine.builder;

import com.loyayz.simple.statemachine.Action;
import com.loyayz.simple.statemachine.Statemachine;
import com.loyayz.simple.statemachine.StatemachineException;
import com.loyayz.simple.statemachine.Transition;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
class BuilderImpl<S, EVT, CTX> implements StatemachineBuilder<S, EVT, CTX>,
        TransitionRegister<S, EVT, CTX>,
        TransitionAt<S, EVT, CTX>,
        TransitionFrom<S, EVT, CTX>,
        TransitionTo<S, EVT, CTX>,
        TransitionOn<S, EVT, CTX>,
        TransitionWhen<S, EVT, CTX> {
    private final List<Transition<S, EVT, CTX>> transitions = new ArrayList<>();

    private Transition<S, EVT, CTX> currentTransition;
    private final List<S> currentSourceStates = new ArrayList<>();

    @Override
    public TransitionRegister<S, EVT, CTX> newTransition() {
        this.addTransition(currentTransition);
        currentTransition = new Transition<>();
        currentSourceStates.clear();
        return this;
    }

    @Override
    public TransitionAt<S, EVT, CTX> at(S state) {
        currentSourceStates.add(state);
        currentTransition.target(state);
        return this;
    }

    @Override
    public TransitionFrom<S, EVT, CTX> from(S... states) {
        if (states == null || states.length == 0) {
            throw new StatemachineException("原状态不可为空");
        }
        currentSourceStates.addAll(Arrays.asList(states));
        return this;
    }

    @Override
    public TransitionTo<S, EVT, CTX> to(S state) {
        currentTransition.target(state);
        return this;
    }

    @Override
    public TransitionOn<S, EVT, CTX> on(EVT event) {
        currentTransition.event(event);
        return this;
    }

    @Override
    public TransitionWhen<S, EVT, CTX> when(Predicate<CTX> condition) {
        currentTransition.condition(condition);
        return this;
    }

    @Override
    public StatemachineBuilder<S, EVT, CTX> then(Action<S, EVT, CTX> action) {
        currentTransition.action(action);
        return this;
    }

    @Override
    public Statemachine<S, EVT, CTX> build() {
        this.addTransition(currentTransition);
        if (transitions.isEmpty()) {
            throw new StatemachineException("未配置流转过程！请先调用 .newTransition()");
        }
        Map<S, Map<EVT, Transition<S, EVT, CTX>>> statEventTransitions = new HashMap<>(8);
        transitions.stream()
                .collect(Collectors.groupingBy(Transition::source))
                .forEach((source, eventTransitions) -> {
                    Map<EVT, Transition<S, EVT, CTX>> eventTransitionMap = new HashMap<>();
                    eventTransitions.forEach(transition -> {
                        EVT event = transition.event();
                        if (eventTransitionMap.containsKey(event)) {
                            throw new StatemachineException("重复配置组合：source [" + source + "] & event [" + event + "]");
                        }
                        eventTransitionMap.put(event, transition);
                    });
                    statEventTransitions.put(source, eventTransitionMap);
                });
        return new StatemachineImpl<>(statEventTransitions);
    }

    private void addTransition(Transition<S, EVT, CTX> transition) {
        if (transition == null) {
            return;
        }
        Transition<S, EVT, CTX> copied;
        for (S source : currentSourceStates) {
            copied = new Transition<>(transition);
            copied.source(source);
            transitions.add(copied);
        }
    }

}
