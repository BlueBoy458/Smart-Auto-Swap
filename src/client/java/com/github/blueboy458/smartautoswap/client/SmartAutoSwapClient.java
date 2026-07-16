package com.github.blueboy458.smartautoswap.client;

import com.github.blueboy458.smartautoswap.SmartAutoSwap;
import com.github.blueboy458.smartautoswap.config.ModConfig;
import com.github.blueboy458.smartautoswap.utilities.InputHandlerUtils;
import com.github.blueboy458.smartautoswap.utilities.WeaponUtils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;

import net.fabricmc.api.ClientModInitializer;

import java.util.concurrent.atomic.AtomicInteger;

public class SmartAutoSwapClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		AtomicInteger tickCounter = new AtomicInteger();


		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			int count = tickCounter.get();
			Options playerOptions = net.minecraft.client.Minecraft.getInstance().options;
			KeyMapping useKey = playerOptions.keyUse;
			boolean tickCounterChanged = (count > 0);

			if (useKey.isDown() && count < SmartAutoSwap.CONFIG.tickDuration) {
				tickCounter.getAndIncrement();
			}

			else if (useKey.isDown() && count == SmartAutoSwap.CONFIG.tickDuration){
				SmartAutoSwap.LOGGER.info("Player held the \"use\" key, duration: {}", count);
			}
			else {
				if (count < SmartAutoSwap.CONFIG.tickDuration && tickCounterChanged) {
					SmartAutoSwap.LOGGER.info("Player tapped the \"use\" key, duration: {}", count);
				}
				tickCounter.set(0);
			}
		});
	}
}