package sh.talonfloof.orderofthetalon.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.options.KeyBinding;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class KeyBindingListener {
    public static KeyBinding sprintBind;
    public static KeyBinding noClipBind;

    @EventListener
    public void registerKeyBindings(KeyBindingRegisterEvent event) {
        List<KeyBinding> list = event.keyBindings;
        list.add(sprintBind = new KeyBinding("key.mctalonfied.sprint", Keyboard.KEY_LCONTROL));
        list.add(noClipBind = new KeyBinding("key.mctalonfied.noclip", Keyboard.KEY_G));
    }
}
