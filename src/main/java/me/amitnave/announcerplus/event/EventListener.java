
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

package me.amitnave.announcerplus.event;

import me.amitnave.announcerplus.handlers.TitleHandler;
import me.amitnave.announcerplus.state.State;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    private State state = State.getInstance();
    private Configuration configuration = state.getConfig();
    private  TitleHandler titleHandler = new TitleHandler();
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(configuration.getBoolean("use-custom")) {
            titleHandler.sendCustomTitleFromConfig(player);
        } else {
            titleHandler.sendTitleFromConfig(player);
        }
    }
}
