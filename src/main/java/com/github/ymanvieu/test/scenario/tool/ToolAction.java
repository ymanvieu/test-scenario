package com.github.ymanvieu.test.scenario.tool;

import com.github.ymanvieu.test.scenario.ScenarioContext;

public abstract class ToolAction<CTX extends ScenarioContext> implements Tool {

    public abstract void execute(CTX ctx);
}
