package com.github.ymanvieu.test.scenario.example.framework.when;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.when.WhenAction;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class GetDriverChampionshipLeader extends WhenAction<F1ChampionshipScenarioContext> {

    private String result;

    @Override
    protected void internalExecute(F1ChampionshipScenarioContext ctx) throws Exception {
        result = ctx.getApplication().getDriverChampionshipLeader();
    }
}
