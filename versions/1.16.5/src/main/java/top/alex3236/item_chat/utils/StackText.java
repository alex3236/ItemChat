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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.regex.Pattern;

public class StackText {
    public static Text handlePatternText(PlayerEntity player, String message, CallbackInfo ci) {
        String pattern = Config.getPattern();
        if (!message.contains(pattern) || player == null) return null;

        ItemStack stack = player.getMainHandStack();

        if (Objects.equals(Registry.ITEM.getId(stack.getItem()).toString(), "minecraft:air")) {
            Text msg = new TranslatableText("commands.enchant.failed.itemless", player.getDisplayName()).formatted(Formatting.RED);
            player.sendMessage(msg, false);
            return null;
        }

        ci.cancel();
        String[] parts = message.split(Pattern.quote(pattern), 2);
        LiteralText beforeText = new LiteralText(parts[0]);
        LiteralText afterText = new LiteralText(parts[1]);
        Text hoverableText = getStackText(stack);
        Text completeText = beforeText.append(hoverableText).append(afterText);
        return new TranslatableText("chat.type.text", player.getDisplayName(), completeText);
    }

    public static Text getStackText(ItemStack stack) {
        MutableText mutableText = new TranslatableText("entity.minecraft.item").append(":").append(stack.getName());
        if (stack.hasCustomName()) {
            mutableText.formatted(Formatting.ITALIC);
        }
        MutableText mutableText2 = Texts.bracketed(mutableText);
        if (!stack.isEmpty()) {
            mutableText2.formatted(stack.getRarity().formatting).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack))));
        }
        return mutableText2;

    }
}