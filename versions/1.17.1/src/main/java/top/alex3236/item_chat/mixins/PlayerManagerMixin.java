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

package top.alex3236.item_chat.mixins;

import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.alex3236.item_chat.utils.StackText;

import java.util.UUID;
import java.util.function.Function;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow
    @Nullable
    public abstract ServerPlayerEntity getPlayer(UUID uuid);

    @Shadow
    public abstract void broadcastChatMessage(Text par1, MessageType par2, UUID par3);

    @Inject(method = "broadcast", at = @At("HEAD"), cancellable = true)
    private void onBroadcast(Text serverMessage, Function<ServerPlayerEntity, Text> playerMessageFactory, MessageType playerMessageType, UUID sender, CallbackInfo ci) {
        if (serverMessage.getString().startsWith("/") || sender == null) return;

        StringBuilder sb = new StringBuilder(serverMessage.getString());
        String adjustedMessage = sb.delete(0, sb.indexOf(">") + 2).toString();
        ServerPlayerEntity player = this.getPlayer(sender);
        if (player == null) return;

        Text result = StackText.handlePatternText(player, adjustedMessage, ci);
        if (result == null) return;

        this.broadcastChatMessage(result, MessageType.CHAT, sender);
    }
}