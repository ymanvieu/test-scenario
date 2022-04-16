package com.github.ymanvieu.test.scenario.example.framework;

import org.junit.jupiter.api.BeforeEach;
import com.github.ymanvieu.test.scenario.ScenarioDSL;
import com.github.ymanvieu.test.scenario.given.GivenParam;
import com.github.ymanvieu.test.scenario.then.ThenVerification;
import com.github.ymanvieu.test.scenario.tool.ToolAction;
import com.github.ymanvieu.test.scenario.when.WhenAction;

public class AbstractScenario implements ScenarioDSL<F1ChampionshipScenarioContext> {

    private F1ChampionshipScenarioContext ctx;

    /**
     * Context is recreated before each test
     */
    @BeforeEach
    public void setup() {
        this.ctx = new F1ChampionshipScenarioContext();
    }

    @SafeVarargs
    @Override
    public final ScenarioDSL<F1ChampionshipScenarioContext> given(GivenParam<F1ChampionshipScenarioContext>... givenParam) {
        return ctx.getDsl().given(givenParam);
    }

    @Override
    public ScenarioDSL<F1ChampionshipScenarioContext> when(WhenAction<F1ChampionshipScenarioContext> whenAction) {
        return ctx.getDsl().when(whenAction);
    }

    @Override
    public ScenarioDSL<F1ChampionshipScenarioContext> verify(ThenVerification<F1ChampionshipScenarioContext> thenVerification) {
        return ctx.getDsl().verify(thenVerification);
    }

    @Override
    public ScenarioDSL<F1ChampionshipScenarioContext> util(ToolAction<F1ChampionshipScenarioContext> toolAction) {
        return ctx.getDsl().util(toolAction);
    }
}
