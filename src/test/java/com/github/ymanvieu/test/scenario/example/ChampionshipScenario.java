package com.github.ymanvieu.test.scenario.example;

import org.junit.jupiter.api.Test;
import com.github.ymanvieu.test.scenario.example.application.enums.Race;
import com.github.ymanvieu.test.scenario.example.application.enums.Response;
import com.github.ymanvieu.test.scenario.example.framework.AbstractScenario;
import com.github.ymanvieu.test.scenario.example.framework.given.Driver;
import com.github.ymanvieu.test.scenario.example.framework.given.RaceStandings;
import com.github.ymanvieu.test.scenario.example.framework.given.Team;
import com.github.ymanvieu.test.scenario.example.framework.then.AddRaceStandingsVerification;
import com.github.ymanvieu.test.scenario.example.framework.then.GetDriverChampionshipLeaderVerification;
import com.github.ymanvieu.test.scenario.example.framework.then.GetTeamChampionshipLeaderVerification;
import com.github.ymanvieu.test.scenario.example.framework.when.AddRaceStandings;
import com.github.ymanvieu.test.scenario.example.framework.when.GetDriverChampionshipLeader;
import com.github.ymanvieu.test.scenario.example.framework.when.GetTeamChampionshipLeader;

public class ChampionshipScenario extends AbstractScenario {

    private final Team ferrari = new Team().name("Ferrari");
    private final Team redBull = new Team().name("Red bull");
    private final Team mercedes = new Team().name("Mercedes");

    private final Driver charlesLeclerc = new Driver().name("Charles Leclerc").team(ferrari);
    private final Driver maxVerstappen = new Driver().name("Max Verstappen").team(redBull);
    private final Driver georgeRussell = new Driver().name("George Russell").team(mercedes);

    @Test
    void addRaceStanding_ok() {
        given(georgeRussell, charlesLeclerc, maxVerstappen);

        when(new AddRaceStandings()
            .race(Race.AUSTRALIA)
            .standings(
                charlesLeclerc.name(),
                maxVerstappen.name(),
                georgeRussell.name()));
        verify(new AddRaceStandingsVerification());
    }

    @Test
    void addRaceStanding_alreadyAdded() {
        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(georgeRussell, charlesLeclerc)); // max didn't finish (╯°□°）╯︵ ┻━┻

        when(new AddRaceStandings()
            .race(Race.AUSTRALIA)
            .standings(
                maxVerstappen.name(),
                charlesLeclerc.name(),
                georgeRussell.name()));
        verify(new AddRaceStandingsVerification()
            .response(Response.RACE_STANDINGS_ALREADY_ADDED));
    }

    @Test
    void getDriverChampionshipLeader_ok() {
        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(maxVerstappen, charlesLeclerc, georgeRussell));

        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(maxVerstappen.name()));

        given(new RaceStandings()
            .race(Race.BELGIUM)
            .standings(charlesLeclerc, georgeRussell));

        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(charlesLeclerc.name()));

        given(new RaceStandings()
            .race(Race.ABU_DHABI)
            .standings(georgeRussell, charlesLeclerc, maxVerstappen));

        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(charlesLeclerc.name()));
    }

    @Test
    void getTeamChampionshipLeader_ok() {
        Driver carlosSainz = new Driver().name("Carlos Sainz").team(ferrari);
        Driver sergioPerez = new Driver().name("Sergio Perez").team(redBull);

        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(maxVerstappen, charlesLeclerc, georgeRussell, sergioPerez, carlosSainz));

        // RB: 37, F: 28
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(redBull.name()));

        given(new RaceStandings()
            .race(Race.BELGIUM)
            .standings(charlesLeclerc, georgeRussell, sergioPerez, carlosSainz, maxVerstappen));

        // RB: 62, F: 65
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(ferrari.name()));

        given(new RaceStandings()
            .race(Race.ABU_DHABI)
            .standings(georgeRussell, maxVerstappen, carlosSainz, sergioPerez, charlesLeclerc));

        // RB: 92, F: 90
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(redBull.name()));
    }
}
