package com.github.ymanvieu.test.scenario.example.framework;

import com.github.ymanvieu.test.scenario.ScenarioContext;
import com.github.ymanvieu.test.scenario.example.application.F1ChampionshipApplication;

import lombok.Getter;

@Getter
public final class F1ChampionshipScenarioContext extends ScenarioContext {

    private final DSL<F1ChampionshipScenarioContext> dsl = new DSL<>(this);
    private final F1ChampionshipApplication application = new F1ChampionshipApplication();
}
