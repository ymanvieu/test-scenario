package io.github.ymanvieu.test.scenario.example;

import org.junit.jupiter.api.Test;
import io.github.ymanvieu.test.scenario.example.application.enums.Response;
import io.github.ymanvieu.test.scenario.example.framework.AbstractScenario;
import io.github.ymanvieu.test.scenario.example.framework.given.Team;
import io.github.ymanvieu.test.scenario.example.framework.then.EnrollTeamVerification;
import io.github.ymanvieu.test.scenario.example.framework.when.EnrollTeam;

public class TeamScenario extends AbstractScenario {

    @Test
    void enrollTeam_ok() {
        String teamName = "Ferrari";

        when(new EnrollTeam()
            .teamName(teamName));
        verify(new EnrollTeamVerification());
    }

    @Test
    void enrollTeam_alreadyEnrolled() {
        String teamName = "Red Bull Racing";

        given(new Team().name(teamName));

        when(new EnrollTeam()
            .teamName(teamName));
        verify(new EnrollTeamVerification().response(Response.TEAM_ALREADY_ENROLLED));
    }
}
