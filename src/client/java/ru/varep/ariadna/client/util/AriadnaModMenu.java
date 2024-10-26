package ru.varep.ariadna.client.util;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.varep.ariadna.client.AriadnaConfig;

@Environment(EnvType.CLIENT)
public class AriadnaModMenu implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(AriadnaConfig.class, parent).get();
    }
}
