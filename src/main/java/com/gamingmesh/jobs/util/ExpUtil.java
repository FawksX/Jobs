package com.gamingmesh.jobs.util;

import com.gamingmesh.jobs.cmi.lib.Version;
import org.bukkit.entity.Player;

public class ExpUtil {

    public static int getPlayerExperience(Player player) {
        return (expToLevel(player.getLevel()) + Math.round(deltaLevelToExp(player.getLevel()) * player.getExp()));
    }

    // total xp calculation based by lvl
    public static int expToLevel(int level) {
        if (Version.isCurrentEqualOrLower(Version.v1_7_R4)) {
            if (level <= 16)
                return 17 * level;
            else if (level <= 31)
                return ((3 * level * level) / 2) - ((59 * level) / 2) + 360;
            else
                return ((7 * level * level) / 2) - ((303 * level) / 2) + 2220;
        }
        if (level <= 16)
            return (level * level) + (6 * level);
        else if (level <= 31)
            return (int) ((2.5 * level * level) - (40.5 * level) + 360);
        else
            return (int) ((4.5 * level * level) - (162.5 * level) + 2220);
    }

    // xp calculation for one current lvl
    public static int deltaLevelToExp(int level) {
        if (level <= 16) {
            return 2 * level + 7;
        }
        else if (level <= 31) {
            return 5 * level - 38;
        }
        else {
            return 9 * level - 158;
        }
    }

}
