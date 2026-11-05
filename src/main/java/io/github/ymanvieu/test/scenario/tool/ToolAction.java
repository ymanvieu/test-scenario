package io.github.ymanvieu.test.scenario.tool;

import io.github.ymanvieu.test.scenario.ScenarioContext;

/**
 * Utility step with application-defined execution behavior.
 * @param <CTX> scenario context type
 */
public abstract class ToolAction<CTX extends ScenarioContext> implements Tool {

    /** Creates a utility step. */
    public ToolAction() {
    }

    /**
     * Performs this utility step.
     * @param ctx scenario context
     */
    public abstract void execute(CTX ctx);
}
