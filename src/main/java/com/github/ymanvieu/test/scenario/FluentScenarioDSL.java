package com.github.ymanvieu.test.scenario;

import com.github.ymanvieu.test.scenario.given.GivenParam;
import com.github.ymanvieu.test.scenario.then.ThenVerification;
import com.github.ymanvieu.test.scenario.tool.ToolAction;
import com.github.ymanvieu.test.scenario.when.WhenAction;

public abstract class FluentScenarioDSL<CTX extends ScenarioContext> {

    protected abstract CTX getScenarioContext();

    @SafeVarargs
    public final FluentScenarioDSL<CTX> given(GivenParam<CTX>... givenParam) {
        for (GivenParam<CTX> param : givenParam) {
            param.create(getScenarioContext());
        }
        return this;
    }

    public <T extends WhenAction<CTX>> T when(T whenAction) {
        whenAction.execute(getScenarioContext());
        return whenAction;
    }

    public <T extends GivenParam<CTX>> T when(T givenParam) {
        givenParam.create(getScenarioContext());
        return givenParam;
    }

    public <T extends ThenVerification<CTX>> T verify(T thenVerification) {
        thenVerification.verify(getScenarioContext());
        return thenVerification;
    }

    public <T extends ToolAction<CTX>> T util(T toolAction) {
        toolAction.execute(getScenarioContext());
        return toolAction;
    }
}
