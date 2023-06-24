package wsh.eval.module;

import com.google.inject.AbstractModule;
import wsh.eval.variable.MapVariableStore;
import wsh.eval.variable.VariableStore;

public class VariableModule extends AbstractModule {
    @Override
    public void configure() {
        bind(VariableStore.class).to(MapVariableStore.class);
    }

}
