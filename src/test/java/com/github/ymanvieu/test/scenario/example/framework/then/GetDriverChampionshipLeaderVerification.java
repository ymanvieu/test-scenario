package com.github.ymanvieu.test.scenario.example.framework.then;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.when.GetDriverChampionshipLeader;
import com.github.ymanvieu.test.scenario.then.ThenVerification;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
public class GetDriverChampionshipLeaderVerification extends ThenVerification<F1ChampionshipScenarioContext> {

    private String driverName;

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        GetDriverChampionshipLeader lastAction = ctx.lastAction(GetDriverChampionshipLeader.class);
        assertThat(lastAction).withFailMessage("No GetDriverChampionshipLeader invoked yet.").isNotNull();
        assertThat(lastAction.result()).isEqualTo(driverName);
    }
}
