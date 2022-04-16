package com.github.ymanvieu.test.scenario.example.framework.when;

import static java.util.Objects.requireNonNull;

import com.github.ymanvieu.test.scenario.example.application.enums.Response;
import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.when.WhenAction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@ToString(exclude = "result")
public class EnrollDriver extends WhenAction<F1ChampionshipScenarioContext> {

    @Setter
    private String driverName;
    @Setter
    private String teamName;

    private Response result;

    @Override
    protected void internalExecute(F1ChampionshipScenarioContext ctx) {
        result = ctx.getApplication()
            .enrollDriver(requireNonNull(driverName), requireNonNull(teamName));
    }
}
