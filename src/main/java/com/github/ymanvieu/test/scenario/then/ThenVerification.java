package com.github.ymanvieu.test.scenario.then;

import com.github.ymanvieu.test.scenario.ScenarioContext;

public abstract class ThenVerification<CTX extends ScenarioContext> implements Then {

    private boolean verified;

    public void verify(CTX ctx) {
        if (verified) {
            return;
        }
        verified = true;
        try {
            internalVerify(ctx);
        } catch (Exception exception) {
            throw new Error(getClass().getSimpleName() + ".verify() failed", exception);
        }
    }

    protected abstract void internalVerify(CTX ctx) throws Exception;
}
