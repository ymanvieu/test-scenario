package io.github.ymanvieu.test.scenario.example;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import io.github.ymanvieu.test.scenario.example.application.enums.Race;
import io.github.ymanvieu.test.scenario.example.application.enums.Response;
import io.github.ymanvieu.test.scenario.example.framework.AbstractScenario;
import io.github.ymanvieu.test.scenario.example.framework.given.Driver;
import io.github.ymanvieu.test.scenario.example.framework.given.RaceStandings;
import io.github.ymanvieu.test.scenario.example.framework.given.Team;
import io.github.ymanvieu.test.scenario.example.framework.then.AddRaceStandingsVerification;
import io.github.ymanvieu.test.scenario.example.framework.then.GetDriverChampionshipLeaderVerification;
import io.github.ymanvieu.test.scenario.example.framework.then.GetTeamChampionshipLeaderVerification;
import io.github.ymanvieu.test.scenario.example.framework.when.AddRaceStandings;
import io.github.ymanvieu.test.scenario.example.framework.when.GetDriverChampionshipLeader;
import io.github.ymanvieu.test.scenario.example.framework.when.GetTeamChampionshipLeader;

public class ChampionshipScenario extends AbstractScenario {

    private final Team ferrari = new Team().name("Ferrari");
    private final Team redBull = new Team().name("Red Bull Racing");
    private final Team mercedes = new Team().name("Mercedes");
    private final Team mclaren = new Team().name("McLaren");
    private final Team racingBulls = new Team().name("Racing Bulls");
    private final Team audi = new Team().name("Audi");
    private final Team alpine = new Team().name("Alpine");
    private final Team haas = new Team().name("Haas F1 Team");

    private final Driver charlesLeclerc = new Driver().name("Charles Leclerc").team(ferrari);
    private final Driver lewisHamilton = new Driver().name("Lewis Hamilton").team(ferrari);
    private final Driver maxVerstappen = new Driver().name("Max Verstappen").team(redBull);
    private final Driver isackHadjar = new Driver().name("Isack Hadjar").team(redBull);
    private final Driver georgeRussell = new Driver().name("George Russell").team(mercedes);
    private final Driver kimiAntonelli = new Driver().name("Kimi Antonelli").team(mercedes);
    private final Driver landoNorris = new Driver().name("Lando Norris").team(mclaren);
    private final Driver oscarPiastri = new Driver().name("Oscar Piastri").team(mclaren);
    private final Driver liamLawson = new Driver().name("Liam Lawson").team(racingBulls);
    private final Driver arvidLindblad = new Driver().name("Arvid Lindblad").team(racingBulls);
    private final Driver nicoHulkenberg = new Driver().name("Nico Hulkenberg").team(audi);
    private final Driver gabrielBortoleto = new Driver().name("Gabriel Bortoleto").team(audi);
    private final Driver pierreGasly = new Driver().name("Pierre Gasly").team(alpine);
    private final Driver francoColapinto = new Driver().name("Franco Colapinto").team(alpine);
    private final Driver oliverBearman = new Driver().name("Oliver Bearman").team(haas);

    // Official 2026 Grand Prix top tens; drivers outside the points are omitted.
    // https://www.formula1.com/en/results/2026/races/1279/australia/race-result
    private final Driver[] australiaStandings = {
        georgeRussell, kimiAntonelli, charlesLeclerc, lewisHamilton, landoNorris,
        maxVerstappen, oliverBearman, arvidLindblad, gabrielBortoleto, pierreGasly
    };

    // https://www.formula1.com/en/results/2026/races/1290/belgium/race-result
    private final Driver[] belgiumStandings = {
        kimiAntonelli, charlesLeclerc, maxVerstappen, lewisHamilton, oscarPiastri,
        isackHadjar, landoNorris, gabrielBortoleto, arvidLindblad, francoColapinto
    };

    // https://www.formula1.com/en/results/2026/races/1296/singapore/race-result
    private final Driver[] singaporeStandings = {
        maxVerstappen, charlesLeclerc, kimiAntonelli, landoNorris, oscarPiastri,
        lewisHamilton, liamLawson, nicoHulkenberg, georgeRussell, isackHadjar
    };

    @Test
    void addRaceStanding_ok() {
        given(australiaStandings);

        when(new AddRaceStandings()
            .race(Race.AUSTRALIA)
            .standings(driverNames(australiaStandings)));
        verify(new AddRaceStandingsVerification());
    }

    @Test
    void addRaceStanding_alreadyAdded() {
        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(australiaStandings));

        when(new AddRaceStandings()
            .race(Race.AUSTRALIA)
            .standings(driverNames(australiaStandings)));
        verify(new AddRaceStandingsVerification()
            .response(Response.RACE_STANDINGS_ALREADY_ADDED));
    }

    @Test
    void getDriverChampionshipLeader_ok() {
        // Totals cover only the selected Grands Prix, excluding sprints.
        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(australiaStandings));

        // Russell: 25; Antonelli: 18; Leclerc: 15.
        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(georgeRussell.name()));

        given(new RaceStandings()
            .race(Race.BELGIUM)
            .standings(belgiumStandings));

        // Antonelli: 43; Leclerc: 33; Russell: 25.
        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(kimiAntonelli.name()));

        given(new RaceStandings()
            .race(Race.SINGAPORE)
            .standings(singaporeStandings));

        // Antonelli: 58; Leclerc: 51; Verstappen: 48.
        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(kimiAntonelli.name()));
    }

    @Test
    void getTeamChampionshipLeader_usesCorrectPositionPoints() {
        given(
            new RaceStandings().race(Race.BELGIUM).standings(belgiumStandings),
            new RaceStandings().race(Race.SINGAPORE).standings(singaporeStandings));

        // Ferrari: (18 + 12) + (18 + 8) = 56; Red Bull: (15 + 8) + (25 + 1) = 49.
        // Mercedes: 25 + (15 + 2) = 42; McLaren: (10 + 6) + (12 + 10) = 38.
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(ferrari.name()));
    }

    @Test
    void getDriverChampionshipLeader_withTenFinishers() {
        given(new RaceStandings().race(Race.AUSTRALIA).standings(australiaStandings));

        when(new GetDriverChampionshipLeader());
        verify(new GetDriverChampionshipLeaderVerification().driverName(georgeRussell.name()));
    }

    @Test
    void getTeamChampionshipLeader_ok() {
        // Totals cover only the selected Grands Prix, excluding sprints.
        given(new RaceStandings()
            .race(Race.AUSTRALIA)
            .standings(australiaStandings));

        // Mercedes: 43; Ferrari: 27; McLaren: 10; Red Bull: 8.
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(mercedes.name()));

        given(new RaceStandings()
            .race(Race.BELGIUM)
            .standings(belgiumStandings));

        // Mercedes: 68; Ferrari: 57; Red Bull: 31; McLaren: 26.
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(mercedes.name()));

        given(new RaceStandings()
            .race(Race.SINGAPORE)
            .standings(singaporeStandings));

        // Mercedes: 85; Ferrari: 83; Red Bull: 57; McLaren: 48.
        when(new GetTeamChampionshipLeader());
        verify(new GetTeamChampionshipLeaderVerification().teamName(mercedes.name()));
    }

    private static String[] driverNames(Driver[] standings) {
        return Arrays.stream(standings).map(Driver::name).toArray(String[]::new);
    }
}
