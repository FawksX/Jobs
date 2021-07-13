package com.gamingmesh.jobs.service;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.economy.CurrencyHandler;
import com.gamingmesh.jobs.service.impl.AbstractService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

public class EconomyService extends AbstractService<Economy> implements CurrencyHandler {

    private static EconomyService instance;

    public EconomyService(Jobs jobs) {
        super(jobs, Economy.class);
        instance = this;
    }

    public static EconomyService getInstance() {
        return instance;
    }

    @Override
    public boolean depositPlayer(OfflinePlayer offlinePlayer, double money) {
        return this.getService().depositPlayer(offlinePlayer, money).transactionSuccess();
    }

    @Override
    public boolean withdrawPlayer(OfflinePlayer offlinePlayer, double money) {
        return this.getService().withdrawPlayer(offlinePlayer, money).transactionSuccess();
    }

    @Override
    public String format(double money) {
        return this.getService().format(money);
    }

    @Override
    public boolean hasMoney(OfflinePlayer offlinePlayer, double money) {
        return this.getService().has(offlinePlayer, money);
    }

    @Override
    public boolean hasMoney(String player, double money) {
        return this.getService().has(player, money);
    }

    @Override
    public boolean withdrawPlayer(String player, double money) {
        return this.getService().withdrawPlayer(player, money).transactionSuccess();
    }

    @Override
    public boolean depositPlayer(String player, double money) {
        return this.getService().depositPlayer(player, money).transactionSuccess();
    }
}
