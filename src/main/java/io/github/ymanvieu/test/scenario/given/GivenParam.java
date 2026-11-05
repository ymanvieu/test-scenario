package io.github.ymanvieu.test.scenario.given;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.ymanvieu.test.scenario.ScenarioContext;

/**
 * Creates and registers initial scenario state once per step instance.
 * @param <CTX> scenario context type
 */
public abstract class GivenParam<CTX extends ScenarioContext> implements Given {

    private static final Map<String, AtomicInteger> SEQUENCES = new ConcurrentHashMap<>();
    /** Logger named after the concrete step class. */
    protected final Logger logger;
    private boolean created;

    /** Initializes the step logger. */
    protected GivenParam() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Generates an ID using a process-wide sequence for the prefix.
     * @param prefix sequence key and ID prefix
     * @return prefix followed by {@code ~} and a base-36 sequence number
     */
    public static String nextId(String prefix) {
        AtomicInteger sequence = SEQUENCES.computeIfAbsent(prefix, k -> new AtomicInteger());

        return prefix + "~" + Integer.toString(sequence.incrementAndGet(), 36);
    }

    /**
     * Returns the next process-wide sequence number for this class's simple name.
     * @return incremented sequence number
     */
    protected int nextId() {
        AtomicInteger sequence = SEQUENCES.computeIfAbsent(getClass().getSimpleName(), k -> new AtomicInteger());
        return sequence.incrementAndGet();
    }

    /**
     * Creates and registers this step unless it has already succeeded.
     * @param scenarioContext context to initialize
     * @throws Error if creation throws an exception
     */
    public void create(CTX scenarioContext) {
        if (created) {
            return;
        }
        logger.info("Start creating {}", getClass().getSimpleName());
        try {
            internalCreate(scenarioContext);
            scenarioContext.register(this);
            created = true;
        } catch (Exception e) {
            throw new Error(getClass().getSimpleName() + ".create() failed", e);
        }
        logger.info("Done creating {}", getClass().getSimpleName());
    }

    /**
     * Implements application-specific setup.
     * @param ctx scenario context
     * @throws Exception if setup fails
     */
    protected abstract void internalCreate(CTX ctx) throws Exception;


    /**
     * Optional reset hook; does nothing by default, including leaving the created flag unchanged.
     * @param ctx scenario context
     */
    public void reset(CTX ctx) {
    }
}
