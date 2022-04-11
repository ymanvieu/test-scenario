package com.github.ymanvieu.test.scenario.given;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.ymanvieu.test.scenario.ScenarioContext;

public abstract class GivenParam<CTX extends ScenarioContext> implements Given {

    private static final Map<String, AtomicInteger> SEQUENCES = new HashMap<>();
    protected final Logger logger;
    private boolean created;

    protected GivenParam() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public static String nextId(String prefix) {
        AtomicInteger sequence = SEQUENCES.computeIfAbsent(prefix, k -> new AtomicInteger());

        return prefix + "~" + Integer.toString(sequence.incrementAndGet(), 36);
    }

    protected int nextId() {
        AtomicInteger sequence = SEQUENCES.computeIfAbsent(getClass().getSimpleName(), k -> new AtomicInteger());
        return sequence.incrementAndGet();
    }

    public void create(CTX scenarioContext) {
        if (created) {
            return;
        }
        logger.info("Start creating {}", this);
        try {
            internalCreate(scenarioContext);
            scenarioContext.register(this);
            created = true;
        } catch (Exception e) {
            throw new Error(getClass().getSimpleName() + ".create() failed", e);
        }
        logger.info("Done creating {}", this);
    }

    protected abstract void internalCreate(CTX ctx) throws Exception;


    public void reset(CTX ctx) {
    }
}
