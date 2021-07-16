package com.gamingmesh.jobs.config.impl;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.reference.ConfigurationReference;
import org.spongepowered.configurate.reference.ValueReference;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractConfiguration {

    private WatchServiceListener watchServiceListener;
    private ConfigurationReference<CommentedConfigurationNode> base;
    private ValueReference<?, CommentedConfigurationNode> config;

    private Class<?> clazz;
    private Path configFile;

    protected void setup() {
        try {
            this.watchServiceListener = WatchServiceListener.create();
            this.base = this.watchServiceListener.listenToConfiguration(file ->
                    YamlConfigurationLoader.builder()
                            .nodeStyle(NodeStyle.BLOCK)
                            .defaultOptions(options -> options.shouldCopyDefaults(true))
                            .path(file).build(), configFile);

            this.config = this.base.referenceTo(clazz);
            this.base.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
