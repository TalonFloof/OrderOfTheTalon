package sh.talonfloof.orderofthetalon.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BlockListener {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block limestone;
    public static Block copperOre;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        limestone = new TemplateBlock(NAMESPACE.id("limestone"), Material.STONE).setHardness(1.5F).setTranslationKey(NAMESPACE, "limestone");
        copperOre = new TemplateBlock(NAMESPACE.id("copper_ore"), Material.STONE).setHardness(1.5F).setTranslationKey(NAMESPACE, "copperOre");
    }
}
