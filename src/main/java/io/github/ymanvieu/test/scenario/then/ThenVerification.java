package io.github.ymanvieu.test.scenario.then;

import io.github.ymanvieu.test.scenario.ScenarioContext;

/**
 * Verifies scenario results once per successful step instance.
 * @param <CTX> scenario context type
 */
public abstract class ThenVerification<CTX extends ScenarioContext> implements Then {

    /** Creates a verification that has not yet succeeded. */
    public ThenVerification() {
    }

    private boolean verified;

    /**
     * Runs this verification unless it has already succeeded.
     * @param ctx scenario context
     * @throws Error if verification throws an exception
     */
    public void verify(CTX ctx) {
        if (verified) {
            return;
        }
        try {
            internalVerify(ctx);
            verified = true;
        } catch (Exception exception) {
            throw new Error(getClass().getSimpleName() + ".verify() failed", exception);
        }
    }

    /**
     * Implements application-specific assertions.
     * @param ctx scenario context
     * @throws Exception if verification fails
     */
    protected abstract void internalVerify(CTX ctx) throws Exception;
}
