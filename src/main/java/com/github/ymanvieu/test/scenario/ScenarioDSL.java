package com.github.ymanvieu.test.scenario;

import com.github.ymanvieu.test.scenario.given.GivenParam;
import com.github.ymanvieu.test.scenario.then.ThenVerification;
import com.github.ymanvieu.test.scenario.tool.ToolAction;
import com.github.ymanvieu.test.scenario.when.WhenAction;

public interface ScenarioDSL<CTX extends ScenarioContext> {

    ScenarioDSL<CTX> given(GivenParam<CTX>... givenParam);
    ScenarioDSL<CTX> when(WhenAction<CTX> whenAction);
    default ScenarioDSL<CTX> when(GivenParam<CTX> givenParam) {
        return given(givenParam);
    }
    ScenarioDSL<CTX> verify(ThenVerification<CTX> thenVerification);
    ScenarioDSL<CTX> util(ToolAction<CTX> toolAction);
}
