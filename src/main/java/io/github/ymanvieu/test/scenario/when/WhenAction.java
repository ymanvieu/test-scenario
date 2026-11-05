package io.github.ymanvieu.test.scenario.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.ymanvieu.test.scenario.ScenarioContext;

/**
 * Registers and attempts an application action once per step instance.
 * @param <CTX> scenario context type
 */
public abstract class WhenAction<CTX extends ScenarioContext> implements When {

    /** Creates an action that has not yet been attempted. */
    public WhenAction() {
    }

    private boolean executed = false;

    /** Logger named after the concrete action class. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Registers and executes this action; subsequent calls do nothing, even after failure.
     * @param ctx scenario context
     * @throws Error if execution throws an exception
     */
    public void execute(CTX ctx) {
        if (executed) {
            return;
        }
        ctx.register(this);
        executed = true;
        logger.info("Start execution {}", getClass().getSimpleName());

        try {
            internalExecute(ctx);
        } catch (Exception e) {
            logger.error("Exception in scenario", e);
            throw new Error(getClass().getSimpleName() + ".execute() failed");
        }
        logger.info("End execution {}", getClass().getSimpleName());
    }

    /**
     * Implements the application interaction.
     * @param ctx scenario context
     * @throws Exception if the interaction fails
     */
    protected abstract void internalExecute(CTX ctx) throws Exception;
}
