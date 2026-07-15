package com.github.blueboy458.smartautoswap;

import com.github.blueboy458.smartautoswap.Config.ModConfig;
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
import net.minecraft.world.entity.player.Player;
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

	/**
	* Swaps player's items in both hand by using the third `temp` variable.
	* Equivalent to the "Swap hands" key (The default keybind is "f").
	 */
	public void swapHands(Player player) {
		var offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
		player.setItemInHand(InteractionHand.OFF_HAND, player.getItemInHand(InteractionHand.MAIN_HAND));
		player.setItemInHand(InteractionHand.MAIN_HAND, offHandStack);
	}

	/**
	 * Checks whether the current item in the specified item stack is a weapon, by iterating over the weapon tags.
	 * @param handStack The stack of the current hand item.
	 * @param isProjectile whether to look for projectile weapons (e.g. Bow) instead of short
	 *                     range weapons (e.g. Swords, axes, etc.)
	 * @return true if the item is a weapon/projectile weapon, false otherwise.
	 */
	public boolean isWeapon(ItemStack currentStack, boolean isProjectile) {
		final List<TagKey<Item>> weapons = isProjectile ? projectileWeaponTags : nonProjectileWeaponTags;
		for (TagKey<Item> weaponType: weapons) {
			if (currentStack.is(weaponType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onInitialize() {

		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

			AttackEntityCallback.EVENT.register(((player, level, hand, entity, hitResult) -> {
				if (CONFIG.modEnabled && !player.isSpectator() && level instanceof ServerLevel) {
					ItemStack offHandItem = player.getOffhandItem();
					ItemStack mainHandItem = player.getMainHandItem();
					if (!isWeapon(mainHandItem, false) && isWeapon(offHandItem, false)){
						swapHands(player);
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
						swapHands(player);
					}
				}

				return InteractionResult.PASS;
			}));
		}



	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
