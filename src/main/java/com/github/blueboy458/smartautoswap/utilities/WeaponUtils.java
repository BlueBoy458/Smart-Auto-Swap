package com.github.blueboy458.smartautoswap.utilities;

import com.github.blueboy458.smartautoswap.SmartAutoSwap;
import com.github.blueboy458.smartautoswap.config.ModConfig;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ThrowablePotionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WeaponUtils {
    //public static ModConfig CONFIG;
    public static ArrayList<Class<?>> throwableItem = new ArrayList<>(List.of(
            ThrowableItemProjectile.class,
            ThrowablePotionItem.class
    ));
    //public static ModConfig.HiddenSettings settings = CONFIG.settings;
    /**
     * Checks whether the current item in the specified item stack is a weapon, by iterating over the weapon tags.
     * @param currentStack The stack of the current hand item.
     * @param isProjectile whether to look for projectile weapons (e.g. Bow) instead of short
     *                     range weapons (e.g. Swords, axes, etc.)
     * @return true if the item is a weapon/projectile weapon, false otherwise.
     */

    public static boolean isWeapon(ItemStack currentStack, boolean isProjectile) {
        final List<TagKey<Item>> weapons = isProjectile ? SmartAutoSwap.CONFIG.settings.projectileWeaponTags
                                                          : SmartAutoSwap.CONFIG.settings.nonProjectileWeaponTags;
        for (TagKey<Item> weaponType: weapons) {
            if (currentStack.is(weaponType)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isThrowable(ItemStack currentStack) {
        Item ItemObject = currentStack.getItem();
        for (Class<?> CurrentThrowableProjectileClass : throwableItem) {
            if (CurrentThrowableProjectileClass.isInstance(ItemObject)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isUsable(ItemStack currentStack) {
        ItemUseAnimation usedAnimation = currentStack.getUseAnimation();

        return isWeapon(currentStack, true)
                || SmartAutoSwap.CONFIG.settings.usableItemAnimation.contains(usedAnimation)
                || isThrowable(currentStack);
    }

}
