package io.github.InSearchOfName.inSearchOfNamesRandomAdditions;

import com.google.inject.AbstractModule;


public class PluginModule extends AbstractModule {
    private final InSearchOfNamesRandomAdditions plugin;

    public PluginModule(InSearchOfNamesRandomAdditions plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(InSearchOfNamesRandomAdditions.class).toInstance(plugin);


    }
}
