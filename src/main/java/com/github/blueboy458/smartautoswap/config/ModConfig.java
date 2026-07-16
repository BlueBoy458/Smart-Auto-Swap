package com.github.blueboy458.smartautoswap.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;

import java.util.ArrayList;
import java.util.List;

@Config(name="smart-auto-swap")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean modEnabled = true;
    @ConfigEntry.Gui.Tooltip
    public boolean swapMiningInCreative = false;
    @ConfigEntry.Gui.Tooltip
    public boolean preserveDurability = false;
    @ConfigEntry.Gui.Tooltip
    public boolean prioritizeHigherTiers = true;

    @ConfigEntry.Gui.Excluded
    public transient HiddenSettings settings = new HiddenSettings();
    @ConfigEntry.Gui.Excluded
    public int tickDuration = 5;
    public static class HiddenSettings {
        public ArrayList<TagKey<Item>> nonProjectileWeaponTags = new ArrayList<>(List.of(
                ItemTags.SWORDS,
                ItemTags.AXES,
                ItemTags.MACE_ENCHANTABLE,
                ItemTags.TRIDENT_ENCHANTABLE
        ));
        public ArrayList<TagKey<Item>> projectileWeaponTags = new ArrayList<>(List.of(
                ItemTags.BOW_ENCHANTABLE,
                ItemTags.CROSSBOW_ENCHANTABLE,
                ItemTags.TRIDENT_ENCHANTABLE
        ));
        public ArrayList<ItemUseAnimation> usableItemAnimation = new ArrayList<>(List.of(
                ItemUseAnimation.BOW,
                ItemUseAnimation.CROSSBOW,
                ItemUseAnimation.EAT,
                ItemUseAnimation.DRINK,
                ItemUseAnimation.BLOCK,
                ItemUseAnimation.SPYGLASS,
                ItemUseAnimation.BRUSH,
                ItemUseAnimation.SPEAR
        ));
//        public transient ArrayList<Class<?>> throwableItem = new ArrayList<>(List.of(
//           ThrowableItemProjectile.class,
//                ThrowablePotionItem.class
//        ));
    }
}
