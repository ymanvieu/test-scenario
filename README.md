# test-scenario

[![Build](https://github.com/ymanvieu/test-scenario/actions/workflows/maven.yml/badge.svg)](https://github.com/ymanvieu/test-scenario/actions/workflows/maven.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)

A small Java library for writing readable black-box tests with reusable Given/When/Then steps.

Move setup, application interactions, and assertions into named steps so tests describe
business behavior. The library provides a Java DSL; your test code supplies the
application-specific context, actions, and verifications.

## Requirements

- Java 17 or later.
- Maven to build this repository.
- A test runner such as JUnit Jupiter to execute your scenarios. The examples use JUnit Jupiter.

## Installation

```xml
<dependency>
  <groupId>io.github.ymanvieu</groupId>
  <artifactId>test-scenario</artifactId>
  <version>RELEASE_VERSION</version>
  <scope>test</scope>
</dependency>
```

## Writing scenarios

A scenario typically follows three steps:

1. `given(...)` creates the initial state.
2. `when(...)` performs an action against the application.
3. `verify(...)` checks the result, representing the Then step.

For example, this test checks that an existing team cannot enroll twice:

```java
import org.junit.jupiter.api.Test;

import io.github.ymanvieu.test.scenario.example.application.enums.Response;
import io.github.ymanvieu.test.scenario.example.framework.AbstractScenario;
import io.github.ymanvieu.test.scenario.example.framework.given.Team;
import io.github.ymanvieu.test.scenario.example.framework.then.EnrollTeamVerification;
import io.github.ymanvieu.test.scenario.example.framework.when.EnrollTeam;

class TeamScenario extends AbstractScenario {

    @Test
    void rejectsAnAlreadyEnrolledTeam() {
        String teamName = "Red Bull Racing";

        given(new Team().name(teamName));

        when(new EnrollTeam().teamName(teamName));

        verify(new EnrollTeamVerification()
            .response(Response.TEAM_ALREADY_ENROLLED));
    }
}
```

The `example` classes above belong to this repository's test suite and are not
included in the published library. Use them as a reference for your own steps:

- Extend `ScenarioContext` to hold the state and application access needed by a scenario.
- Extend `ScenarioDSL<YourContext>` and implement `getScenarioContext()` in your test base class.
- Extend `GivenParam<YourContext>`, `WhenAction<YourContext>`, and `ThenVerification<YourContext>` to implement reusable steps.
- Create a fresh context before each test to keep scenarios independent.

`ScenarioDSL` returns the DSL from each step for chaining.
`FluentScenarioDSL` returns the executed step from its single-step methods so
callers can access that step's values.

### Examples

Browse the complete, executable examples:

- [TeamScenario](src/test/java/io/github/ymanvieu/test/scenario/example/TeamScenario.java): team enrollment and duplicate enrollment.
- [DriverScenario](src/test/java/io/github/ymanvieu/test/scenario/example/DriverScenario.java): driver scenarios.
- [ChampionshipScenario](src/test/java/io/github/ymanvieu/test/scenario/example/ChampionshipScenario.java): championship scenarios combining multiple steps.
- [Example test base](src/test/java/io/github/ymanvieu/test/scenario/example/framework/AbstractScenario.java): context initialization with JUnit.
- [Example steps and context](src/test/java/io/github/ymanvieu/test/scenario/example/framework): application-specific implementations.

## Local development

Clone the repository and run the build:

```sh
git clone https://github.com/ymanvieu/test-scenario.git
cd test-scenario
mvn -B clean verify -Dgpg.skip=true
```

This compiles the library, runs the tests, and generates the library, sources,
and Javadoc JARs under `target/`. Signing is skipped for local verification;
publishing and release builds run through GitHub Actions.

## Contributing

Bug reports, documentation improvements, and pull requests are welcome.

Before opening an [issue](https://github.com/ymanvieu/test-scenario/issues), check
for an existing report. Include the Java version, library version, steps to
reproduce, and the expected and actual behavior. A small reproducer helps.

For a pull request:

1. Fork the repository and create a branch for your change.
2. Keep the change focused and follow the existing Java style.
3. Add or update tests when changing behavior, and update documentation when needed.
4. Run `mvn -B clean verify -Dgpg.skip=true`.
5. Explain the problem, the change, and how you verified it in the pull request.

Do not include credentials, private signing keys, or sensitive application data
in issues, logs, or pull requests.

## License

Licensed under the [Apache License, Version 2.0](LICENSE).
