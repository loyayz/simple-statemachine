package com.loyayz.simple.statemachine;

import com.loyayz.simple.statemachine.builder.StatemachineBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public class StatemachineTest {
    enum States {
        STATE1, STATE2, STATE3,STATE4
    }

    enum Events {
        EVENT1, EVENT2, EVENT3
    }

    @Test
    public void testNoop() {
        Assert.assertThrows(StatemachineException.class, () ->
                StatemachineBuilder.<States, Events, String>create().build()
        );
    }

    @Test
    public void testRepeat() {
        Assert.assertThrows(StatemachineException.class,
                () -> StatemachineBuilder.<States, Events, String>create()
                        // one
                        .newTransition()
                        .from(States.STATE1)
                        .to(States.STATE2)
                        .on(Events.EVENT1)
                        .when(condition())
                        .perform(action())
                        // two
                        .newTransition()
                        .from(States.STATE1)
                        .to(States.STATE2)
                        .on(Events.EVENT1)
                        .when(condition())
                        .perform(action())
                        .build()
        );
    }

    @Test
    public void testTransitions() {
        String machineId = UUID.randomUUID().toString();
        Statemachine<States, Events, String> machine = StatemachineBuilder
                .<States, Events, String>create()
                // STATE1 -> STATE2 on EVENT1
                .newTransition()
                .from(States.STATE1)
                .to(States.STATE2)
                .on(Events.EVENT1)
                .when(condition())
                .perform(action())

                // STATE2 -> STATE3 on EVENT2
                .newTransition()
                .from(States.STATE2)
                .to(States.STATE3)
                .on(Events.EVENT2)
                .when(condition())
                .perform(action())

                // STATE3 -> STATE3 on EVENT2
                .newTransition()
                .at(States.STATE3)
                .on(Events.EVENT2)
                .when(condition())
                .perform(action())

                // STATE1,STATE2,STATE3 -> STATE4 on EVENT3
                .newTransition()
                .from(States.STATE1,States.STATE2,States.STATE3)
                .to(States.STATE4)
                .on(Events.EVENT3)
                .always()
                .perform(action())

                .build()
                .register(machineId);

        Assert.assertEquals(States.STATE2, machine.execute(States.STATE1, Events.EVENT1, "1"));
        Assert.assertEquals(States.STATE3, machine.execute(States.STATE2, Events.EVENT2, "2"));
        Assert.assertEquals(States.STATE3, machine.execute(States.STATE3, Events.EVENT2, "3"));
        Assert.assertEquals(States.STATE4, machine.execute(States.STATE1, Events.EVENT3, "4"));
        Assert.assertEquals(States.STATE4, machine.execute(States.STATE2, Events.EVENT3, "5"));
        Assert.assertEquals(States.STATE4, machine.execute(States.STATE3, Events.EVENT3, "6"));
        Assert.assertThrows(StatemachineException.class, () -> machine.execute(States.STATE1, Events.EVENT2, ""));
        Assert.assertThrows(StatemachineException.class, () -> machine.execute(States.STATE2, Events.EVENT1, ""));
    }

    private static Predicate<String> condition() {
        return (ctx) -> true;
    }

    private static Action<States, Events, String> action() {
        return (from, to, event, ctx) -> {
            System.out.println("ctx [" + ctx + "], " + from + " -> " + to + " on " + event);
        };
    }

}
