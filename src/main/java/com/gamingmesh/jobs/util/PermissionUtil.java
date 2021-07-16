package com.gamingmesh.jobs.util;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.cmi.lib.RawMessage;
import org.bukkit.entity.Player;

public class PermissionUtil {

    public static boolean hasPermission(Object sender, String perm, boolean rawEnable) {
        if (!(sender instanceof Player) || ((Player) sender).hasPermission(perm))
            return true;

        if (!rawEnable) {
            ((Player) sender).sendMessage(Jobs.getLanguage().getMessage("general.error.permission"));
            return false;
        }
        new RawMessage().addText(Jobs.getLanguage().getMessage("general.error.permission")).addHover("&2" + perm).show((Player) sender);
        return false;

    }

}
