package com.github.ymanvieu.test.scenario.example.framework.given;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.then.EnrollDriverVerification;
import com.github.ymanvieu.test.scenario.example.framework.when.EnrollDriver;
import com.github.ymanvieu.test.scenario.given.GivenParam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
public class Driver extends GivenParam<F1ChampionshipScenarioContext> {

    private String name;
    private Team team;

    @Override
    protected void internalCreate(F1ChampionshipScenarioContext ctx) {
        if (name == null) {
            name = nextId("DRIVER");
        }

        if (team == null) {
            team = ctx.defaultGiven(Team.class);
        }

        ctx.getDsl()
            .given(team)
            .when(new EnrollDriver().driverName(name).teamName(team.name()))
            .verify(new EnrollDriverVerification());
    }
}
