# test-scenario [![](https://jitpack.io/v/ymanvieu/test-scenario.svg)](https://jitpack.io/#ymanvieu/test-scenario) [![Build](https://github.com/ymanvieu/test-scenario/workflows/Build/badge.svg)](https://github.com/ymanvieu/test-scenario/actions/workflows/maven.yml) [![Release](https://github.com/ymanvieu/test-scenario/workflows/Release/badge.svg)](https://github.com/ymanvieu/test-scenario/actions/workflows/maven-release.yml)

## Test-scenario is a Java framework used for blackbox testing

The main goal of the framework is to abstract any business/technical implementation, improving tests maintenability and readability.

In other words, it is a test (Scenario) with Given/When/Then steps from the [BDD](https://en.wikipedia.org/wiki/Behavior-driven_development) paradigm.

```java
class SomeBusinessPerimeterScenario extends AbstractScenario {

    @Test
    void doingSomethingInThatPerimeter() {
        // prerequisites
        given(new SomeOtherLinkedPerimeterAction());

        // action to test
        when(new ActionInThePerimeter().someParameter(value));

        // assertions
        // some direct assertions like a returned value/status of the previous action
        then(new ActionInThePerimeterVerification().returnedValue(expectedValue));

        // optional: indirect assertions like reading values from somewhere else in the application
        when(new GetSomeBusinessInfo().someParameter(value));
        then(new GetSomeBusinessInfoVerification().expectedValue(someValue));
    }
}
```

## Installation

pom.xml

```xml
<project>
  [...]
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
  [...]
  <dependencies>
    <dependency>
      <groupId>com.github.ymanvieu</groupId>
      <artifactId>test-scenario</artifactId>
      <version>LATEST_VERSION</version>
    </dependency>
  </dependencies>
</project>
```
## Examples

[ChampionshipScenario](src/test/java/com/github/ymanvieu/test/scenario/example/ChampionshipScenario.java)
