

/*
 * Copyright (c) 2018. Amit Nave
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package me.amitnave.announcerplus.handlers;

import com.google.common.collect.Iterables;
import lombok.*;
import me.amitnave.announcerplus.FontSettings;
import me.amitnave.announcerplus.state.State;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TitleHandler {

    private State state = State.getInstance();
    private Configuration configuration = state.getConfig();

    /**
     * generates an animated title list
     * @param colors list of colors that are going to be animated
     * @param partLength the length of one color
     * @param message the title that is going to be animated
     * @return a list of titles that are animated.
     */
    private List<String> generateTitle(List<String> colors, int partLength, String message, FontSettings fontSettings) {

        List<String> list = new ArrayList<>();
        int colorAmount = colors.size();
        List<String> firstColors = new ArrayList<>(colors);
        Collections.reverse(firstColors);
        Iterator<String> firstColorIterator = Iterables.cycle(firstColors).iterator();
        String lastColor = "";
        ArrayList<String> newColors = new ArrayList<>(colors);
        int offset = 0;
        // loop over all list elements there should be
        for (int i = 0; i < colorAmount * partLength; i++) {
            // create an element
            Iterator<String> colorIterator = Iterables.cycle(newColors).iterator();
            if (i % partLength == 0) {
                lastColor = firstColorIterator.next();
            }
            StringBuilder newMessage = new StringBuilder("§" + lastColor);
            if(fontSettings.isBold()) newMessage.append("§l");
            if(fontSettings.isItalic()) newMessage.append("§o");
            if(fontSettings.isUnderline()) newMessage.append("§n");
            for (int j = 0; j < message.length(); j++) {
                newMessage.append(message.toCharArray()[j]);
                if (j % partLength == offset) {
                    newMessage.append("§").append(colorIterator.next());
                    if(fontSettings.isBold()) newMessage.append("§l");
                    if(fontSettings.isItalic()) newMessage.append("§o");
                    if(fontSettings.isUnderline()) newMessage.append("§n");
                }
            }
            offset++;
            if (offset == partLength) {
                offset = 0;
                newColors.add(0, newColors.remove(newColors.size() - 1));
            }
            list.add(newMessage.toString());
        }
        return list;
    }

    /**
     *
     * @param player the player to send the title to.
     */
    public void sendCustomTitleFromConfig(Player player) {
        List<String> mainTitle = configuration.getStringList("custom-messages")
                .stream()
                .map(s -> s.replace('&', '§'))
                .collect(Collectors.toList());
        List<String> secondaryTitle = configuration.getStringList("secondary-custom-messages")
                .stream()
                .map(s -> s.replace('&', '§'))
                .collect(Collectors.toList());

        Iterator<String> mainIterator = Iterables.cycle(mainTitle).iterator();
        Iterator<String> secondaryIterator = Iterables.cycle(secondaryTitle).iterator();

        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                state.getPlugin(),
                () -> player.sendTitle(mainIterator.next(), secondaryIterator.next(), 0, configuration.getInt("frame-length"), 10),
                0,
                configuration.getLong("frame-length"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                state.getPlugin(),
                () -> Bukkit.getScheduler().cancelTask(task),
                configuration.getLong("timespan") * 30);
    }
    /**
     *
     * @param player the player to send the title to.
     */
    public void sendTitleFromConfig(Player player) {
        FontSettings main = new FontSettings(configuration.getBoolean("bold"),
                configuration.getBoolean("italic"),
                configuration.getBoolean("underlined"));

        FontSettings secondary = new FontSettings(configuration.getBoolean("secondary-bold"),
                configuration.getBoolean("secondary-italic"),
                configuration.getBoolean("secondary-underlined"));

        List<String> mainTitle = this.generateTitle(
                configuration.getStringList("changing-colors"),
                configuration.getInt("part-length"),
                configuration.getString("message"),
                main
        );

        List<String> secondaryTitle = this.generateTitle(
                configuration.getStringList("secondary-changing-colors"),
                configuration.getInt("secondary-part-length"),
                configuration.getString("secondary-message"),
                secondary
        );
        Iterator<String> mainIterator = Iterables.cycle(mainTitle).iterator();
        Iterator<String> secondaryIterator = Iterables.cycle(secondaryTitle).iterator();

        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                state.getPlugin(),
                () -> player.sendTitle(mainIterator.next(), secondaryIterator.next(), 0, configuration.getInt("frame-length"), 10),
                0,
                configuration.getLong("frame-length"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                state.getPlugin(),
                () -> Bukkit.getScheduler().cancelTask(task),
                configuration.getLong("timespan") * 30);
    }
}