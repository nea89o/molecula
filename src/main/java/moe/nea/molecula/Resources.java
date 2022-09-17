package moe.nea.molecula;

import net.minecraft.util.ResourceLocation;

public class Resources {

    public static ResourceLocation r(String name) {
        return new ResourceLocation("molecula", name);
    }

    public static final ResourceLocation BUTTONS = r("textures/gui/buttons.png");
    public static final ResourceLocation BACKGROUND = r("textures/gui/background.png");
}
