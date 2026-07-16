package com.github.blueboy458.smartautoswap.utilities;

import com.github.blueboy458.smartautoswap.config.ModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class InputHandlerUtils {
    public static final String MOD_ID = "smart-auto-swap";
    public static ModConfig CONFIG;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void swapHands(Player player) {
        var offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        player.setItemInHand(InteractionHand.OFF_HAND, player.getItemInHand(InteractionHand.MAIN_HAND));
        player.setItemInHand(InteractionHand.MAIN_HAND, offHandStack);
    }

    public static void startTicking(AtomicInteger tickCounter) {

    }
}
