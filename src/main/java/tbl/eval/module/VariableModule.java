package tbl.eval.module;

import com.google.inject.AbstractModule;
import tbl.eval.variable.MapVariableStore;
import tbl.eval.variable.VariableStore;

public class VariableModule extends AbstractModule {
    @Override
    public void configure() {
        bind(VariableStore.class).to(MapVariableStore.class);
    }

}
