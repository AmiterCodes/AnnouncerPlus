

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

package me.amitnave.announcerplus;

import me.amitnave.announcerplus.event.EventListener;
import me.amitnave.announcerplus.handlers.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public final class AnnouncerPlus extends JavaPlugin {

    public static AnnouncerPlus getInstance() {
        return instance;
    }
    private static AnnouncerPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        getCommand("announce").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
