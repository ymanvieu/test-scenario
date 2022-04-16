package com.github.ymanvieu.test.scenario.example.application;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static org.assertj.core.data.MapEntry.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.ymanvieu.test.scenario.example.application.enums.Race;
import com.github.ymanvieu.test.scenario.example.application.enums.Response;
import com.github.ymanvieu.test.scenario.example.application.exception.ChampionshipNotStartedException;

/**
 * Simple Application handling F1 teams and drivers.
 */
public class F1ChampionshipApplication {

    private static final List<Integer> POINTS = Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1);

    private final Set<String> teams = new HashSet<>();
    // <driver, team>
    private final Map<String, String> drivers = new HashMap<>();

    private final Map<Race, LinkedHashSet<String>> standings = new LinkedHashMap<>();

    /**
     * Enroll a team with the given name.
     * @param teamName the name of the team
     * @return OK if the team has been enrolled
     */
    public Response enrollTeam(String teamName) {
        return teams.add(teamName) ? Response.OK : Response.TEAM_ALREADY_ENROLLED;
    }

    /**
     * Enroll a driver with the given name for the given team.
     * @param driverName the name of the driver
     * @param teamName the name of the team
     * @return OK if the driver has been enrolled
     */
    public Response enrollDriver(String driverName, String teamName) {
        if (!teams.contains(teamName)) {
            return Response.TEAM_NOT_ENROLLED;
        }
        return drivers.putIfAbsent(driverName, teamName) == null ? Response.OK : Response.DRIVER_ALREADY_ENROLLED;
    }

    public Set<String> listDrivers() {
        return Collections.unmodifiableSet(drivers.keySet());
    }

    public Response addResults(Race race, Set<String> driversStandings) {
        return standings.putIfAbsent(race, new LinkedHashSet<>(driversStandings)) == null ? Response.OK : Response.RACE_STANDINGS_ALREADY_ADDED;
    }

    public String getDriverChampionshipLeader() throws ChampionshipNotStartedException {
        return computeDriversStandings()
            .entrySet().stream()
            .max(Entry.comparingByValue()).orElseThrow(ChampionshipNotStartedException::new)
            .getKey();
    }

    public String getTeamChampionshipLeader() throws ChampionshipNotStartedException {
        return computeDriversStandings()
            .entrySet().stream().collect(groupingBy(e -> drivers.get(e.getKey()), summingInt(Entry::getValue)))
            .entrySet().stream()
            .max(Entry.comparingByValue()).orElseThrow(ChampionshipNotStartedException::new)
            .getKey();
    }

    private Map<String, Integer> computeDriversStandings() {
         return standings.values().stream()
            .flatMap(raceStanding -> raceStanding.stream().map(driver -> entry(driver, getPoints(new ArrayList<>(raceStanding).indexOf(driver)+1))))
            .collect(groupingBy(Entry::getKey, summingInt(Entry::getValue)));
    }

    private static Integer getPoints(int position) {
        return position > 0 && position <= 10 ? POINTS.get(position) : 0;
    }
}
