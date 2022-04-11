package com.github.ymanvieu.test.scenario.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.ymanvieu.test.scenario.ScenarioContext;

public abstract class WhenAction<CTX extends ScenarioContext> implements When {

    private boolean executed = false;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void execute(CTX ctx) {
        if (executed) {
            return;
        }
        ctx.register(this);
        executed = true;
        logger.info("Start execution {}", this);

        try {
            internalExecute(ctx);
        } catch (Exception e) {
            logger.error("Exception in scenario", e);
            throw new Error(getClass().getSimpleName() + ".execute() failed");
        }
        logger.info("End execution {}", this);
    }

    protected abstract void internalExecute(CTX ctx) throws Exception;
}
