package com.github.blueboy458.smartautoswap;

import com.github.blueboy458.smartautoswap.config.ModConfig;
import com.github.blueboy458.smartautoswap.utilities.InputHandlerUtils;
import com.github.blueboy458.smartautoswap.utilities.WeaponUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class SmartAutoSwap implements ModInitializer {
	public static final String MOD_ID = "smart-auto-swap";
	public static ModConfig CONFIG;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ArrayList<TagKey<Item>> nonProjectileWeaponTags = new ArrayList<>(List.of(
			ItemTags.SWORDS,
			ItemTags.AXES,
			ItemTags.MACE_ENCHANTABLE,
			ItemTags.TRIDENT_ENCHANTABLE
	));
	public static final ArrayList<TagKey<Item>> projectileWeaponTags = new ArrayList<>(List.of(
			ItemTags.BOW_ENCHANTABLE,
			ItemTags.CROSSBOW_ENCHANTABLE,
			ItemTags.TRIDENT_ENCHANTABLE
	));

	@Override
	public void onInitialize() {

		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

			AttackEntityCallback.EVENT.register(((player, level, hand, entity, hitResult) -> {
				if (CONFIG.modEnabled && !player.isSpectator() && level instanceof ServerLevel) {
					ItemStack offHandItem = player.getOffhandItem();
					ItemStack mainHandItem = player.getMainHandItem();
					if (!WeaponUtils.isWeapon(mainHandItem, false) && WeaponUtils.isWeapon(offHandItem, false)){
						InputHandlerUtils.swapHands(player);
					}
				}

				return InteractionResult.PASS;
			}));
			AttackBlockCallback.EVENT.register(((player, level, hand, pos, direction) -> {
				BlockState state = level.getBlockState(pos);
				ItemStack main_hand = player.getItemInHand(InteractionHand.MAIN_HAND);
				ItemStack off_hand = player.getItemInHand(InteractionHand.OFF_HAND);
				if (CONFIG.modEnabled && !off_hand.isEmpty() && !main_hand.isCorrectToolForDrops(state) && off_hand.isCorrectToolForDrops(state)) {
					if (!(player.isCreative() || player.isSpectator()) || CONFIG.swapMiningInCreative) {
						InputHandlerUtils.swapHands(player);
					}
				}

				return InteractionResult.PASS;
			}));
		}



	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
