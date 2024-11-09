package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


@Config(name ="ariadna")
public class AriadnaConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 256)
    public int RADIUS = 64;
    @ConfigEntry.BoundedDiscrete(min = -64, max = 320)
    public int Y_LEVEL = -58;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 180)
    public int Y_RADIUS = 1;


    @ConfigEntry.Gui.Tooltip()
    public String configBlock = "deepslate_diamond_ore";

    public Block getter() {
        Block b = Registries.BLOCK.get(Identifier.tryParse(configBlock));
        if (b == null) {
            System.err.println("[Ariadna] Block not found: " + configBlock);
        b = Registries.BLOCK.get(Identifier.ofVanilla("deepslate_diamond_ore"));}
        return b;
    }

}
