package com.github.ymanvieu.test.scenario.example.framework.then;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.when.AddRaceStandings;

public class AddRaceStandingsVerification extends AbstractResponseVerification<AddRaceStandingsVerification> {

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        AddRaceStandings lastAction = ctx.lastAction(AddRaceStandings.class);
        assertThat(lastAction).withFailMessage("No race standings added yet.").isNotNull();
        assertThat(lastAction.result())
            .as("Adding race <%s> standings <%s> unexpected response.", lastAction.race(), lastAction.standings())
            .isEqualTo(response);
    }
}
