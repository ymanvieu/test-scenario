package com.github.ymanvieu.test.scenario;

import com.github.ymanvieu.test.scenario.given.GivenParam;
import com.github.ymanvieu.test.scenario.then.ThenVerification;
import com.github.ymanvieu.test.scenario.tool.ToolAction;
import com.github.ymanvieu.test.scenario.when.WhenAction;

public abstract class ScenarioDSL<CTX extends ScenarioContext> {

    protected abstract CTX getScenarioContext();

    @SafeVarargs
    public final ScenarioDSL<CTX> given(GivenParam<CTX>... givenParam) {
        for (GivenParam<CTX> param : givenParam) {
            param.create(getScenarioContext());
        }
        return this;
    }

    public ScenarioDSL<CTX> when(WhenAction<CTX> whenAction) {
        whenAction.execute(getScenarioContext());
        return this;
    }

    public ScenarioDSL<CTX> when(GivenParam<CTX> givenParam) {
        givenParam.create(getScenarioContext());
        return this;
    }

    public ScenarioDSL<CTX> verify(ThenVerification<CTX> thenVerification) {
        thenVerification.verify(getScenarioContext());
        return this;
    }

    public ScenarioDSL<CTX> util(ToolAction<CTX> toolAction) {
        toolAction.execute(getScenarioContext());
        return this;
    }
}
