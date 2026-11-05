package io.github.ymanvieu.test.scenario;

import java.util.HashMap;
import java.util.Map;

import io.github.ymanvieu.test.scenario.given.Given;
import io.github.ymanvieu.test.scenario.given.GivenParam;
import io.github.ymanvieu.test.scenario.then.ThenVerification;
import io.github.ymanvieu.test.scenario.tool.Tool;
import io.github.ymanvieu.test.scenario.tool.ToolAction;
import io.github.ymanvieu.test.scenario.when.When;
import io.github.ymanvieu.test.scenario.when.WhenAction;

/** Stores the latest registered setup, action, and utility step by concrete class. */
public class ScenarioContext {

    /** Creates an empty step registry. */
    public ScenarioContext() {
    }

    private final Map<Class<? extends Given>, Given> givenParamRefs = new HashMap<>();
    private final Map<Class<? extends When>, When> whenActionRefs = new HashMap<>();
    private final Map<Class<? extends Tool>, Tool> toolActionRefs = new HashMap<>();

    /**
     * Stores a setup step, replacing any instance of the same concrete class.
     * @param givenParam setup step
     */
    public void register(Given givenParam) {
        givenParamRefs.put(givenParam.getClass(), givenParam);
    }

    /**
     * Stores an action, replacing any instance of the same concrete class.
     * @param whenAction action step
     */
    public void register(When whenAction) {
        whenActionRefs.put(whenAction.getClass(), whenAction);
    }

    /**
     * Stores a utility step, replacing any instance of the same concrete class.
     * @param toolAction utility step
     */
    public void register(Tool toolAction) {
        toolActionRefs.put(toolAction.getClass(), toolAction);
    }

    /**
     * Finds registered setup or constructs an unregistered instance without executing it.
     * @param <T> setup step type
     * @param givenParamClass concrete class with a public no-argument constructor
     * @return registered step, or a new instance if none is registered
     * @throws Error if a new instance cannot be constructed
     */
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

    /**
     * Finds the last action registered for the exact class.
     * @param <T> action type
     * @param whenActionClass concrete action class
     * @return registered action, or {@code null} if absent
     */
    public <T extends When> T lastAction(Class<T> whenActionClass) {
        //noinspection unchecked
        return (T) whenActionRefs.get(whenActionClass);
    }

    /**
     * Finds the last utility step registered for the exact class.
     * @param <T> utility step type
     * @param toolActionClass concrete utility class
     * @return registered utility step, or {@code null} if absent
     */
    public <T extends Tool> T lastToolAction(Class<T> toolActionClass) {
        //noinspection unchecked
        return (T) toolActionRefs.get(toolActionClass);
    }
}
