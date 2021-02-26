package com.loyayz.simple.statemachine.builder;

import com.loyayz.simple.statemachine.Statemachine;
import com.loyayz.simple.statemachine.StatemachineException;
import com.loyayz.simple.statemachine.Transition;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class StatemachineImpl<S, EVT, CTX> implements Statemachine<S, EVT, CTX> {
    private final Map<S, Map<EVT, Transition<S, EVT, CTX>>> transitions;

    @Override
    public S execute(S source, EVT event, CTX context) {
        Transition<S, EVT, CTX> transition = this.findTransition(source, event);
        if (transition == null) {
            throw new StatemachineException("Transition not found: source [" + source + "] & event [" + event + "]");
        }
        return transition.transit(context);
    }

    private Transition<S, EVT, CTX> findTransition(S source, EVT event) {
        Map<EVT, Transition<S, EVT, CTX>> eventTransitions = transitions.get(source);
        return eventTransitions == null ? null : eventTransitions.get(event);
    }

}
