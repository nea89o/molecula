package moe.nea.molecula.test;

import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import moe.nea.molecula.MoleculaGui;
import moe.nea.molecula.widgets.MBackground;
import moe.nea.molecula.widgets.MButton;
import moe.nea.molecula.widgets.MGridPanel;

@Mod(modid = MoleculaTest.MODID)
public class MoleculaTest {
    public static final String MODID = "molecula";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onKeyDown(TickEvent.ClientTickEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_K) && Minecraft.getMinecraft().currentScreen == null) {
            var panel = MGridPanel.noGaps(18);
            panel.addMolecule(MButton.withStaticLabel("Hehe"), 0, 0, 8, 1)
                 .setOnClick(() -> Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Clicked")));
            Minecraft.getMinecraft().displayGuiScreen(new MoleculaGui(new MBackground(6, panel)));
        }
    }
}
