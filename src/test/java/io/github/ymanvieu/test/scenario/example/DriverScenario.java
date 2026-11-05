package io.github.ymanvieu.test.scenario.example;

import org.junit.jupiter.api.Test;
import io.github.ymanvieu.test.scenario.example.application.enums.Response;
import io.github.ymanvieu.test.scenario.example.framework.AbstractScenario;
import io.github.ymanvieu.test.scenario.example.framework.given.Driver;
import io.github.ymanvieu.test.scenario.example.framework.given.Team;
import io.github.ymanvieu.test.scenario.example.framework.then.EnrollDriverVerification;
import io.github.ymanvieu.test.scenario.example.framework.then.ListDriversVerification;
import io.github.ymanvieu.test.scenario.example.framework.when.EnrollDriver;
import io.github.ymanvieu.test.scenario.example.framework.when.ListDrivers;

public class DriverScenario extends AbstractScenario {

    @Test
    void enrollDriver_ok() {
        String driverName = "Charles Leclerc";
        String teamName = "Ferrari";

        given(new Team().name(teamName));

        when(new EnrollDriver()
            .driverName(driverName)
            .teamName(teamName));
        verify(new EnrollDriverVerification());
    }

    @Test
    void enrollDriver_alreadyEnrolled() {
        String driverName = "George Russell";
        Team williams = new Team().name("Williams");
        Team mercedes = new Team().name("Mercedes");

        given(williams, mercedes);

        // Streamlined enrollment using a given param.
        when(new Driver().team(mercedes).name(driverName));

        when(new EnrollDriver()
            .driverName(driverName)
            .teamName(williams.name()));

        verify(new EnrollDriverVerification()
            .response(Response.DRIVER_ALREADY_ENROLLED));
    }

    @Test
    void listDrivers_ok() {
        Driver georgeRussell = new Driver().name("George Russell").team(new Team().name("Mercedes"));
        Driver charlesLeclerc = new Driver().name("Charles Leclerc").team(new Team().name("Ferrari"));
        Driver maxVerstappen = new Driver().name("Max Verstappen").team(new Team().name("Red Bull Racing"));

        given(georgeRussell, charlesLeclerc, maxVerstappen);

        when(new ListDrivers());
        verify(new ListDriversVerification()
            .drivers(maxVerstappen.name(), georgeRussell.name(), charlesLeclerc.name()));
    }
}
