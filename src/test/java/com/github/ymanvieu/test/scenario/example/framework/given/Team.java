package com.github.ymanvieu.test.scenario.example.framework.given;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.then.EnrollTeamVerification;
import com.github.ymanvieu.test.scenario.example.framework.when.EnrollTeam;
import com.github.ymanvieu.test.scenario.given.GivenParam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@ToString
public class Team extends GivenParam<F1ChampionshipScenarioContext> {

    private String name;

    @Override
    protected void internalCreate(F1ChampionshipScenarioContext ctx) {
        if (name == null) {
            name = nextId("TEAM");
        }

        ctx.getDsl()
            .when(new EnrollTeam().teamName(name))
            .verify(new EnrollTeamVerification());
    }
}
