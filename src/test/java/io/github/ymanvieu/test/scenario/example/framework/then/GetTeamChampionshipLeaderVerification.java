package io.github.ymanvieu.test.scenario.example.framework.then;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import io.github.ymanvieu.test.scenario.example.framework.when.GetTeamChampionshipLeader;
import io.github.ymanvieu.test.scenario.then.ThenVerification;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
public class GetTeamChampionshipLeaderVerification extends ThenVerification<F1ChampionshipScenarioContext> {

    private String teamName;

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        GetTeamChampionshipLeader lastAction = ctx.lastAction(GetTeamChampionshipLeader.class);
        assertThat(lastAction).withFailMessage("No GetTeamChampionshipLeader invoked yet.").isNotNull();
        assertThat(lastAction.result()).isEqualTo(teamName);
    }
}
