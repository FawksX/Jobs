package com.gamingmesh.jobs.service.impl;

import com.gamingmesh.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class AbstractService<S> {

    private final S service;

    public AbstractService(Jobs jobs, Class<S> clazz) {

        RegisteredServiceProvider<S> provider = Bukkit.getServicesManager().getRegistration(clazz);

        if(provider == null) {
            throw new RuntimeException("Provider for " + clazz.getSimpleName() + " could not be found!");
        }

        this.service = provider.getProvider();

    }

    public S getService() {
        return this.service;
    }
}
