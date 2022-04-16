package com.github.ymanvieu.test.scenario.example.framework.when;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.assertj.core.util.Sets;
import com.github.ymanvieu.test.scenario.example.application.enums.Race;
import com.github.ymanvieu.test.scenario.example.application.enums.Response;
import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.when.WhenAction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@ToString(exclude = "result")
public class AddRaceStandings extends WhenAction<F1ChampionshipScenarioContext> {

    @Getter
    @Setter
    private Race race;
    private Set<String> standings;

    @Getter
    private Response result;

    public Set<String> standings() {
        return standings;
    }
    public AddRaceStandings standings(String... standings) {
        this.standings = Sets.newLinkedHashSet(standings);
        return this;
    }

    @Override
    protected void internalExecute(F1ChampionshipScenarioContext ctx) {
        result = ctx.getApplication().addResults(requireNonNull(race), requireNonNull(standings));
    }
}
