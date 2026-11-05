package io.github.ymanvieu.test.scenario;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.github.ymanvieu.test.scenario.given.GivenParam;
import io.github.ymanvieu.test.scenario.when.WhenAction;

class ScenarioLoggingTest {

    private static final String SECRET = "dummy-secret-token";

    @Test
    void givenLogsOnlyItsClassName() {
        assertStepLogs(SecretGiven.class, () -> new SecretGiven().create(new ScenarioContext()),
            "Start creating SecretGiven", "Done creating SecretGiven");
    }

    @Test
    void whenLogsOnlyItsClassName() {
        assertStepLogs(SecretAction.class, () -> new SecretAction().execute(new ScenarioContext()),
            "Start execution SecretAction", "End execution SecretAction");
    }

    private void assertStepLogs(Class<?> stepClass, Runnable execute, String... expectedMessages) {
        Logger logger = (Logger) LoggerFactory.getLogger(stepClass);
        Level originalLevel = logger.getLevel();
        boolean originalAdditive = logger.isAdditive();
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);
        logger.setLevel(Level.INFO);
        logger.setAdditive(false);
        try {
            execute.run();

            assertThat(appender.list).extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(expectedMessages)
                .allSatisfy(message -> assertThat(message).doesNotContain(SECRET));
            assertThat(appender.list).allSatisfy(event ->
                assertThat(event.getArgumentArray()).containsExactly(stepClass.getSimpleName()));
        } finally {
            logger.detachAppender(appender);
            logger.setLevel(originalLevel);
            logger.setAdditive(originalAdditive);
            appender.stop();
        }
    }

    private static class SecretGiven extends GivenParam<ScenarioContext> {
        @Override
        protected void internalCreate(ScenarioContext ctx) {
        }

        @Override
        public String toString() {
            return "SecretGiven(token=" + SECRET + ")";
        }
    }

    private static class SecretAction extends WhenAction<ScenarioContext> {
        @Override
        protected void internalExecute(ScenarioContext ctx) {
        }

        @Override
        public String toString() {
            return "SecretAction(token=" + SECRET + ")";
        }
    }
}
