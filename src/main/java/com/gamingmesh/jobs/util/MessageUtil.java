package com.gamingmesh.jobs.util;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MessageUtil {

    // Todo make configurable
    public static final String PREFIX = "<dark_gray>[<aqua>Jobs<dark_gray>] ";

    public static Component deserialize(String message) {
        return MiniMessage.get().parse(message).decoration(TextDecoration.ITALIC, false);
    }

    public static void message(Player player, String message) {
        player.sendMessage(parse(message));
    }

    public static Component parse(String text, @Nullable Object... placeholders) {
        int i = 2 + (placeholders != null ? placeholders.length : 0);
        String[] strPlaceholders = new String[i];
        strPlaceholders[0] = "prefix"; strPlaceholders[1] = PREFIX;

        for(int x = 2; x < i; i++) {
            strPlaceholders[x] = String.valueOf(placeholders[x-2]);
        }
        return MiniMessage.get().parse(text, strPlaceholders).decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> parse(List<String> lore) {
        List<Component> list = Lists.newArrayList();

        for(String s : lore) {
            list.add(deserialize(s));
        }

        return list;

    }

}
