package com.github.ymanvieu.test.scenario.example.framework;

import org.junit.jupiter.api.BeforeEach;
import com.github.ymanvieu.test.scenario.ScenarioDSL;

public class AbstractScenario extends ScenarioDSL<F1ChampionshipScenarioContext> {

    private F1ChampionshipScenarioContext ctx;

    /**
     * Context is recreated before each test
     */
    @BeforeEach
    public void setup() {
        this.ctx = new F1ChampionshipScenarioContext(this);
    }

    @Override
    protected F1ChampionshipScenarioContext getScenarioContext() {
        return ctx;
    }

}
