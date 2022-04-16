package com.github.ymanvieu.test.scenario.example.framework.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.when.ListDrivers;
import com.github.ymanvieu.test.scenario.then.ThenVerification;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ListDriversVerification extends ThenVerification<F1ChampionshipScenarioContext> {

    private List<String> drivers;

    public ListDriversVerification drivers(String... drivers) {
        this.drivers = Arrays.asList(drivers);
        return this;
    }

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        ListDrivers lastListDrivers = ctx.lastAction(ListDrivers.class);
        assertThat(lastListDrivers).withFailMessage("No ListDrivers invoked yet.").isNotNull();
        assertThat(lastListDrivers.result())
            .containsExactlyInAnyOrderElementsOf(drivers);
    }
}
