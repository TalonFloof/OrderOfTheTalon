package sh.talonfloof.orderofthetalon.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.BirchTreeStructure;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeProviderRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.BiomeAPI;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;
import net.modificationstation.stationapi.api.worldgen.biome.VoronoiBiomeProvider;
import net.modificationstation.stationapi.api.worldgen.feature.*;
import sh.talonfloof.orderofthetalon.features.LargeTree;

public class BiomeListener {
    private Biome testBiome1;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        HeightScatterFeature feature1 = new HeightScatterFeature(new LargeTree(), 4);
        HeightScatterFeature feature2 = new HeightScatterFeature(new BirchTreeStructure(), 1);
        testBiome1 = BiomeBuilder.start("Woods").grassAndLeavesColor(0xFF4dd97f)
                .feature(feature1)
                .feature(feature2)
                .overworldLakes()
                .build();
        assert(!testBiome1.getFeatures().isEmpty());
    }

    @EventListener
    public void registerRegions(BiomeProviderRegisterEvent event) {
        VoronoiBiomeProvider voronoi = new VoronoiBiomeProvider();
        voronoi.addBiome(testBiome1);

        BiomeAPI.addOverworldBiomeProvider(StationAPI.NAMESPACE.id("voronoi_provider"), voronoi);
    }
}
