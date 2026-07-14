package com.github.blueboy458.smartautoswap.Config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

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

}
