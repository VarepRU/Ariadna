package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name ="ariadna")
public class AriadnaConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 256)
    public int RADIUS = 64;
    @ConfigEntry.BoundedDiscrete(min = -64, max = 320)
    public int Y_LEVEL = -58;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 180)
    public int Y_RADIUS = 1;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 50)
    @ConfigEntry.Gui.Tooltip()
    public int VEIN_RADIUS = 3;
    @ConfigEntry.BoundedDiscrete(min = 0, max = 256)
    @ConfigEntry.Gui.Tooltip()
    public int LAVA = 20;
}
