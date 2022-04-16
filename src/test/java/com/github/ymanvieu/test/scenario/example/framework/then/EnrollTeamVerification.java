package com.github.ymanvieu.test.scenario.example.framework.then;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.when.EnrollTeam;

public class EnrollTeamVerification extends AbstractResponseVerification<EnrollTeamVerification> {

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        EnrollTeam lastEnrollTeam = ctx.lastAction(EnrollTeam.class);
        assertThat(lastEnrollTeam).withFailMessage("No Team has been enrolled yet.").isNotNull();
        assertThat(lastEnrollTeam.result())
            .as("Enrollment of team <" + lastEnrollTeam.teamName() + "> unexpected response.")
            .isEqualTo(response);
    }
}
