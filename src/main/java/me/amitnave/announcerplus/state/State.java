

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

package me.amitnave.announcerplus.state;

import lombok.Data;
import lombok.experimental.Accessors;
import me.amitnave.announcerplus.AnnouncerPlus;
import org.bukkit.configuration.Configuration;

@Data
@Accessors(chain = true)
public class State {
    private AnnouncerPlus plugin = AnnouncerPlus.getInstance();

    Configuration config = plugin.getConfig();

    private final String PREFIX = "§8§l[§c§lAnnouncer§f+§8§l]§f";

    private static State ourInstance = new State();

    public static State getInstance() {
        return ourInstance;
    }

    private State() {

    }
}
