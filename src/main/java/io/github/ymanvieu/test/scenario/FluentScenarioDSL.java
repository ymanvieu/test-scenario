package io.github.ymanvieu.test.scenario;

import io.github.ymanvieu.test.scenario.given.GivenParam;
import io.github.ymanvieu.test.scenario.then.ThenVerification;
import io.github.ymanvieu.test.scenario.tool.ToolAction;
import io.github.ymanvieu.test.scenario.when.WhenAction;

/**
 * Executes scenario steps and returns each step for access to its values.
 * @param <CTX> scenario context type
 */
public abstract class FluentScenarioDSL<CTX extends ScenarioContext> {

    /** Creates a scenario DSL. */
    public FluentScenarioDSL() {
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
    public final FluentScenarioDSL<CTX> given(GivenParam<CTX>... givenParam) {
        for (GivenParam<CTX> param : givenParam) {
            param.create(getScenarioContext());
        }
        return this;
    }

    /**
     * Creates a setup step.
     * @param <T> concrete step type
     * @param givenParam step to execute
     * @return the executed step
     */
    public <T extends GivenParam<CTX>> T given(T givenParam) {
        givenParam.create(getScenarioContext());
        return givenParam;
    }

    /**
     * Executes an application action.
     * @param <T> concrete step type
     * @param whenAction step to execute
     * @return the executed step
     */
    public <T extends WhenAction<CTX>> T when(T whenAction) {
        whenAction.execute(getScenarioContext());
        return whenAction;
    }

    /**
     * Creates setup as an action step.
     * @param <T> concrete step type
     * @param givenParam step to execute
     * @return the executed step
     */
    public <T extends GivenParam<CTX>> T when(T givenParam) {
        givenParam.create(getScenarioContext());
        return givenParam;
    }

    /**
     * Checks the scenario result.
     * @param <T> concrete step type
     * @param thenVerification step to execute
     * @return the executed step
     */
    public <T extends ThenVerification<CTX>> T verify(T thenVerification) {
        thenVerification.verify(getScenarioContext());
        return thenVerification;
    }

    /**
     * Executes a utility step.
     * @param <T> concrete step type
     * @param toolAction step to execute
     * @return the executed step
     */
    public <T extends ToolAction<CTX>> T util(T toolAction) {
        toolAction.execute(getScenarioContext());
        return toolAction;
    }
}
