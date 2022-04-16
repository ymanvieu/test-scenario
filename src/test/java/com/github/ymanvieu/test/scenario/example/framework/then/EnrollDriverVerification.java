package com.github.ymanvieu.test.scenario.example.framework.then;


import static org.assertj.core.api.Assertions.assertThat;

import com.github.ymanvieu.test.scenario.example.framework.F1ChampionshipScenarioContext;
import com.github.ymanvieu.test.scenario.example.framework.when.EnrollDriver;

public class EnrollDriverVerification extends AbstractResponseVerification<EnrollDriverVerification> {

    @Override
    protected void internalVerify(F1ChampionshipScenarioContext ctx) {
        EnrollDriver lastEnrollDriver = ctx.lastAction(EnrollDriver.class);
        assertThat(lastEnrollDriver).withFailMessage("No driver has been enrolled yet.").isNotNull();
        assertThat(lastEnrollDriver.result())
            .as("Enrollment of driver <" + lastEnrollDriver.driverName() + "> for the team <" + lastEnrollDriver.teamName() + "> unexpected response.")
            .isEqualTo(response);
    }
}
