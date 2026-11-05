package com.github.ymanvieu.test.scenario.then;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import com.github.ymanvieu.test.scenario.ScenarioContext;

class ThenVerificationTest {

    private final ScenarioContext ctx = new ScenarioContext();

    @Test
    void failedAssertionIsCheckedAgain() {
        AtomicInteger attempts = new AtomicInteger();
        ThenVerification<ScenarioContext> verification = new ThenVerification<>() {
            @Override
            protected void internalVerify(ScenarioContext ctx) {
                attempts.incrementAndGet();
                throw new AssertionError("Still invalid");
            }
        };

        assertThatThrownBy(() -> verification.verify(ctx)).isInstanceOf(AssertionError.class);
        assertThatThrownBy(() -> verification.verify(ctx)).isInstanceOf(AssertionError.class);
        assertThat(attempts.get()).isEqualTo(2);
    }

    @Test
    void failedVerificationCanSucceedOnRetry() {
        AtomicInteger attempts = new AtomicInteger();
        ThenVerification<ScenarioContext> verification = new ThenVerification<>() {
            @Override
            protected void internalVerify(ScenarioContext ctx) throws Exception {
                if (attempts.incrementAndGet() == 1) {
                    throw new Exception("Not ready");
                }
            }
        };

        assertThatThrownBy(() -> verification.verify(ctx))
            .isInstanceOf(Error.class)
            .hasCauseInstanceOf(Exception.class);
        verification.verify(ctx);
        verification.verify(ctx);
        assertThat(attempts.get()).isEqualTo(2);
    }
}
