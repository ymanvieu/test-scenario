package io.github.ymanvieu.test.scenario;

import io.github.ymanvieu.test.scenario.given.GivenParam;
import io.github.ymanvieu.test.scenario.then.ThenVerification;
import io.github.ymanvieu.test.scenario.tool.ToolAction;
import io.github.ymanvieu.test.scenario.when.WhenAction;

/**
 * Executes scenario steps and returns this DSL for chaining.
 * @param <CTX> scenario context type
 */
public abstract class ScenarioDSL<CTX extends ScenarioContext> {

    /** Creates a scenario DSL. */
    public ScenarioDSL() {
    }

    /**
     * Supplies the context used by every step.
     * @return current scenario context
     */
    protected abstract CTX getScenarioContext();

    /**
     * Creates setup steps in order.
     * @param givenParam setup steps
     * @return this DSL
     */
    @SafeVarargs
    public final ScenarioDSL<CTX> given(GivenParam<CTX>... givenParam) {
        for (GivenParam<CTX> param : givenParam) {
            param.create(getScenarioContext());
        }
        return this;
    }

    /**
     * Executes an application action.
     * @param whenAction step to execute
     * @return this DSL
     */
    public ScenarioDSL<CTX> when(WhenAction<CTX> whenAction) {
        whenAction.execute(getScenarioContext());
        return this;
    }

    /**
     * Creates setup as an action step.
     * @param givenParam step to execute
     * @return this DSL
     */
    public ScenarioDSL<CTX> when(GivenParam<CTX> givenParam) {
        givenParam.create(getScenarioContext());
        return this;
    }

    /**
     * Checks the scenario result.
     * @param thenVerification step to execute
     * @return this DSL
     */
    public ScenarioDSL<CTX> verify(ThenVerification<CTX> thenVerification) {
        thenVerification.verify(getScenarioContext());
        return this;
    }

    /**
     * Executes a utility step.
     * @param toolAction step to execute
     * @return this DSL
     */
    public ScenarioDSL<CTX> util(ToolAction<CTX> toolAction) {
        toolAction.execute(getScenarioContext());
        return this;
    }
}
