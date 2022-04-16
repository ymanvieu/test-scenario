package com.github.ymanvieu.test.scenario.example.framework.when;

import java.util.Set;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.when.WhenAction;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ListDrivers extends WhenAction<F1ChampionshipScenarioContext> {

    private Set<String> result;

    @Override
    protected void internalExecute(F1ChampionshipScenarioContext ctx) {
        result = ctx.getApplication().listDrivers();
    }
}
