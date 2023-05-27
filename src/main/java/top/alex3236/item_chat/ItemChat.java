/*
 * This file is part of the ItemChat project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Alex3236 and contributors
 *
 * ItemChat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ItemChat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ItemChat.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.alex3236.item_chat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import top.alex3236.item_chat.utils.Config;

import java.nio.file.Path;

public class ItemChat implements ModInitializer {
//    public static final Logger LOGGER = LogManager.getLogger();

    public static final Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve("itemchat.json");

    @Override
    public void onInitialize() {
        Config.loadConfig();
    }
}
