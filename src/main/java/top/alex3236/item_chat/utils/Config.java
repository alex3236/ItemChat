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

package top.alex3236.item_chat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import top.alex3236.item_chat.ItemChat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static String pattern = "[i]";

    public static String getPattern() {
        return pattern;
    }

    public static void loadConfig() {
        File file = ItemChat.configFilePath.toFile();
        if (!file.exists()) {
            saveConfig();
            return;
        }
        try {
            String json = new String(Files.readAllBytes(ItemChat.configFilePath)).replace("\n", "");
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            pattern = jsonObject.get("pattern").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("pattern", pattern);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(jsonObject);
            Files.write(ItemChat.configFilePath, json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}