

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

import me.amitnave.announcerplus.AnnouncerPlus;
import me.amitnave.announcerplus.state.State;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class CommandHandler implements CommandExecutor {
    private State state = State.getInstance();
    private AnnouncerPlus plugin = state.getPlugin();
    private Configuration configuration = plugin.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equals("announce")) {
            if(args.length == 0) {
                sendErrorMessage("No arguments given, correct usage: §6/announce <main|secondary|announce|custom|reload>", sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sendSuccessMessage("Reloaded config successfully", sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("announce")) {
                plugin.reloadConfig();
                if(args.length != 1)
                    sendErrorMessage("Wrong usage! correct usage: §6/announce announce,§ctoo many arguments", sender);
                TitleHandler titleHandler = new TitleHandler();
                if(configuration.getBoolean("use-custom"))
                Bukkit.getOnlinePlayers().forEach(titleHandler::sendCustomTitleFromConfig);
                else Bukkit.getOnlinePlayers().forEach(titleHandler::sendTitleFromConfig);
                sendSuccessMessage("Announced successfully", sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("main")) {
                if(args.length < 2)
                    sendErrorMessage("wrong usage! correct usage: §6/announce main <message>", sender);
                args = (String[]) ArrayUtils.remove(args, 0);
                configuration.set("message", StringUtils.join(args, " "));
                plugin.saveConfig();
                sendSuccessMessage("Changed Main Message Successfully", sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("custom")) {
                if(args.length != 2)
                     sendErrorMessage("wrong usage! correct usage: §6/announce custom <true|false>", sender);
                if(args[1].equalsIgnoreCase("true")) configuration.set("use-custom", true);
                else if(args[1].equalsIgnoreCase("false")) configuration.set("use-custom", false);
                else sendErrorMessage("wrong usage! correct usage: §6/announce custom <true|false>", sender);
                plugin.saveConfig();
                sendSuccessMessage("Changed Use Custom Successfully", sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("secondary")) {
                if(args.length < 2)
                    sendErrorMessage("wrong usage! correct usage: §6/announce secondary <message>", sender);
                args = (String[]) ArrayUtils.remove(args, 0);
                configuration.set("secondary-message", StringUtils.join(args, " "));
                plugin.saveConfig();
                sendSuccessMessage("Changed Secondary Message Successfully", sender);
                return true;
            }
            sendErrorMessage("wrong usage! correct usage: §6/announce <main|secondary|announce|custom|reload>", sender);

        }
        return true;
    }
    private void sendErrorMessage(String message, CommandSender sender) {
        sender.sendMessage(state.getPREFIX() + "§c" +  message);
    }
    private void sendSuccessMessage(String message, CommandSender sender) {
        sender.sendMessage(state.getPREFIX() + "§a" +  message);
    }
}
