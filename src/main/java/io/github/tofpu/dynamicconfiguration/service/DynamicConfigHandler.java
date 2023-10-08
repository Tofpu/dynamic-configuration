package io.github.tofpu.dynamicconfiguration.service;


import io.github.tofpu.dynamicconfiguration.Configuration;
import io.github.tofpu.dynamicconfiguration.LoadableConfiguration;
import io.github.tofpu.dynamicconfiguration.impl.AdvancedConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.tofpu.dynamicconfiguration.ProgramCorrectness.requireArgument;
import static io.github.tofpu.dynamicconfiguration.ProgramCorrectness.requireState;

public class DynamicConfigHandler {

    private final File pluginDirectory;
    private final Map<ConfigType, LoadableConfiguration> configurationMap = new HashMap<>();
    private boolean loaded = false;

    public DynamicConfigHandler(File directory) {
        this.pluginDirectory = directory;
    }

    public void load(final Set<ConfigType> configTypes) {
        requireState(!loaded, "DynamicConfigHandler can only be loaded once.");
        requireArgument(configTypes != null && configTypes.size() > 0, "At-least a singular configuration type must be provided.");

        this.loaded = true;

        for (ConfigType value : configTypes) {
            this.configurationMap.put(value, new AdvancedConfiguration());
        }
        this.configurationMap.forEach((type, configuration) -> {
            configuration.load(new File(pluginDirectory, type.identifier() + ".yml"));
        });
    }

    public void load(final ConfigType[] configTypes) {
        load(Arrays.stream(configTypes).collect(Collectors.toSet()));
    }

    public void unload() {
        if (!loaded) return;
        this.configurationMap.keySet().forEach(this::save);
        this.loaded = false;
    }

    public Configuration on(final ConfigType type) {
        requireState(this.configurationMap.containsKey(type), "Unknown %s configuration type",
            type.identifier());
        return this.configurationMap.get(type);
    }

    public void save(final ConfigType type) {
        requireState(this.configurationMap.containsKey(type), "Unknown %s configuration type",
                type.identifier());
        LoadableConfiguration configuration = this.configurationMap.get(type);
        configuration.save(new File(pluginDirectory, type.identifier() + ".yml"));
    }
}