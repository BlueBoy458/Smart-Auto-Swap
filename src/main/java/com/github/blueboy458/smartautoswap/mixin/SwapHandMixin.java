package com.github.blueboy458.smartautoswap.mixin;

import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action.*;

@Mixin(ServerGamePacketListenerImpl.class)
public class SwapHandMixin {
    @Unique
    private static final Logger LOGGER = LogUtils.getLogger();

    @Shadow public ServerPlayer player; // Gives you access to the specific player instance

    @Inject(method = "handlePlayerAction", at = @At("HEAD"))
    private void onPlayerAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {

    }
}



