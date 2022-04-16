package com.github.ymanvieu.test.scenario;

import java.util.HashMap;
import java.util.Map;

import com.github.ymanvieu.test.scenario.given.Given;
import com.github.ymanvieu.test.scenario.given.GivenParam;
import com.github.ymanvieu.test.scenario.then.ThenVerification;
import com.github.ymanvieu.test.scenario.tool.Tool;
import com.github.ymanvieu.test.scenario.tool.ToolAction;
import com.github.ymanvieu.test.scenario.when.When;
import com.github.ymanvieu.test.scenario.when.WhenAction;

public class ScenarioContext {

    private final Map<Class<? extends Given>, Given> givenParamRefs = new HashMap<>();
    private final Map<Class<? extends When>, When> whenActionRefs = new HashMap<>();
    private final Map<Class<? extends Tool>, Tool> toolActionRefs = new HashMap<>();

    public void register(Given givenParam) {
        givenParamRefs.put(givenParam.getClass(), givenParam);
    }

    public void register(When whenAction) {
        whenActionRefs.put(whenAction.getClass(), whenAction);
    }

    public void register(Tool toolAction) {
        toolActionRefs.put(toolAction.getClass(), toolAction);
    }

    public <T extends Given> T defaultGiven(Class<T> givenParamClass) {
        Object givenParam = givenParamRefs.get(givenParamClass);
        if (givenParam == null) {
            try {
                return givenParamClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new Error("Instantiation failed: " + givenParamClass, e);
            }
        } else {
            //noinspection unchecked
            return (T) givenParam;
        }
    }

    public <T extends When> T lastAction(Class<T> whenActionClass) {
        //noinspection unchecked
        return (T) whenActionRefs.get(whenActionClass);
    }

    public <T extends Tool> T lastToolAction(Class<T> toolActionClass) {
        //noinspection unchecked
        return (T) toolActionRefs.get(toolActionClass);
    }


    public static class DSL<CTX extends ScenarioContext> implements ScenarioDSL<CTX> {

        private final CTX scenarioContext;

        public DSL(CTX scenarioContext) {
            this.scenarioContext = scenarioContext;
        }

        @SafeVarargs
        @Override
        public final ScenarioDSL<CTX> given(GivenParam<CTX>... givenParam) {
            for (GivenParam<CTX> param : givenParam) {
                param.create(scenarioContext);
            }
            return this;
        }

        @Override
        public ScenarioDSL<CTX> when(WhenAction<CTX> whenAction) {
            whenAction.execute(scenarioContext);
            return this;
        }

        @Override
        public ScenarioDSL<CTX> when(GivenParam<CTX> givenParam) {
            givenParam.create(scenarioContext);
            return this;
        }

        @Override
        public ScenarioDSL<CTX> verify(ThenVerification<CTX> thenVerification) {
            thenVerification.verify(scenarioContext);
            return this;
        }

        @Override
        public ScenarioDSL<CTX> util(ToolAction<CTX> toolAction) {
            toolAction.execute(scenarioContext);
            return this;
        }
    }
}
