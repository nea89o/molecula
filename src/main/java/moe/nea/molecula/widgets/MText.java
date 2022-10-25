package moe.nea.molecula.widgets;

import java.util.function.Supplier;
import lombok.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import moe.nea.molecula.Molecule;

@Value
public class MText extends Molecule {
    Supplier<String> label;

    public static MText withStaticText(String text) {
        return new MText(() -> text);
    }

    public static MText withTranslatableText(String key, Object... params) {
        return new MText(() -> I18n.format(key, params));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        String       text = label.get();
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        font.drawSplitString(text, 0, 0, getWidth(), 0xFFFFFFFF);
    }
}
