package com.github.blueboy458.smartautoswap.utilities;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class InputHandlerUtils {
    public static void swapHands(Player player) {
        var offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        player.setItemInHand(InteractionHand.OFF_HAND, player.getItemInHand(InteractionHand.MAIN_HAND));
        player.setItemInHand(InteractionHand.MAIN_HAND, offHandStack);
    }

}
