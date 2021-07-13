package com.gamingmesh.jobs.stuff;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem {

    public static void giveItemForPlayer(Player player, ItemStack item) {
	player.getInventory().addItem(item);
	player.updateInventory();
    }
}
