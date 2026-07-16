package com.github.blueboy458.smartautoswap.client;

import com.github.blueboy458.smartautoswap.Config.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

import java.util.concurrent.atomic.AtomicInteger;

public class SmartAutoSwapClient implements ClientModInitializer {
	public static final String MOD_ID = "smart-auto-swap";
	public static ModConfig CONFIG;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int tickDuration = 5;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		AtomicInteger tickCounter = new AtomicInteger();

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			int count = tickCounter.get();
			Options playerOptions = net.minecraft.client.Minecraft.getInstance().options;
			KeyMapping useKey = playerOptions.keyUse;
			boolean tickCounterChanged = (count > 0);

			if (useKey.isDown() && count < tickDuration) {
				tickCounter.getAndIncrement();
			}

			else if (useKey.isDown() && count == tickDuration){
				LOGGER.info("Player held the \"use\" key, duration: {}", count);
			}
			else {
				if (count < tickDuration && tickCounterChanged) {
					LOGGER.info("Player tapped the \"use\" key, duration: {}", count);
				}
				tickCounter.set(0);
			}
			//LOGGER.info("tick {}", tickCounter.get());
		});
	}
}