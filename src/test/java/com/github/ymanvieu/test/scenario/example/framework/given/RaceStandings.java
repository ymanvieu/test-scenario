package com.github.ymanvieu.test.scenario.example.framework.given;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import com.github.ymanvieu.test.scenario.example.application.enums.Race;
import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.then.AddRaceStandingsVerification;
import com.github.ymanvieu.test.scenario.example.framework.when.AddRaceStandings;
import com.github.ymanvieu.test.scenario.given.GivenParam;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@ToString
@Accessors(fluent = true)
public class RaceStandings extends GivenParam<F1ChampionshipScenarioContext> {

    @Setter
    private Race race;
    private Driver[] standings;

    public RaceStandings standings(Driver... standings) {
        this.standings = standings;
        return this;
    }

    @Override
    protected void internalCreate(F1ChampionshipScenarioContext ctx) {
        ctx.getDsl().given(requireNonNull(standings));

        String[] names = Arrays.stream(standings).map(Driver::name).toArray(String[]::new);

        ctx.getDsl()
            .when(new AddRaceStandings().race(race).standings(names))
            .verify(new AddRaceStandingsVerification());
    }
}
