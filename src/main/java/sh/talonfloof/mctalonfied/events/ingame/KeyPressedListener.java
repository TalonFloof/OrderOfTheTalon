package sh.talonfloof.mctalonfied.events.ingame;

import net.fabricmc.loader.api.FabricLoader;
import sh.talonfloof.mctalonfied.IExtendedPlayer;
import sh.talonfloof.mctalonfied.events.init.AchievementListener;
import sh.talonfloof.mctalonfied.events.init.KeyBindingListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import org.lwjgl.input.Keyboard;

public class KeyPressedListener {

    @EventListener
    public void keyPressed(KeyStateChangedEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.isKeyDown(KeyBindingListener.noClipBind.key)) {
            if(((Minecraft) FabricLoader.getInstance().getGameInstance()).player != null) {
                IExtendedPlayer player = ((IExtendedPlayer) ((Minecraft) FabricLoader.getInstance().getGameInstance()).player);
                player.talon$setNoClip(!player.talon$getNoClip());
            }
        }
    }
}
