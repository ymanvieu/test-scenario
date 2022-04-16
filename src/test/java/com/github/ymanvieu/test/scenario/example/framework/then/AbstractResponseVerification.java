package com.github.ymanvieu.test.scenario.example.framework.then;

import com.github.ymanvieu.test.scenario.example.application.enums.Response;
import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.then.ThenVerification;

abstract class AbstractResponseVerification<T extends AbstractResponseVerification<T>> extends ThenVerification<F1ChampionshipScenarioContext> {

    protected Response response = Response.OK;

    public T response(Response response) {
        this.response = response;
        //noinspection unchecked
        return (T) this;
    }
}
