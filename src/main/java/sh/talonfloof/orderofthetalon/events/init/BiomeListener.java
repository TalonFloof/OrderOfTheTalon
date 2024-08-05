package sh.talonfloof.orderofthetalon.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.entity.living.animal.ChickenEntity;
import net.minecraft.entity.living.animal.CowEntity;
import net.minecraft.entity.living.animal.PigEntity;
import net.minecraft.entity.living.animal.SheepEntity;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.BirchTreeStructure;
import net.minecraft.level.structure.OreStructure;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeProviderRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.BiomeAPI;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;
import net.modificationstation.stationapi.api.worldgen.biome.VoronoiBiomeProvider;
import net.modificationstation.stationapi.api.worldgen.feature.*;
import net.modificationstation.stationapi.api.worldgen.surface.SurfaceBuilder;
import sh.talonfloof.orderofthetalon.features.LargeTree;
import sh.talonfloof.orderofthetalon.features.NewOreStructure;
import sh.talonfloof.orderofthetalon.features.OreScatterFeature;

public class BiomeListener {
    private Biome biomeWoods;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        HeightScatterFeature feature1 = new HeightScatterFeature(new LargeTree(), 4);
        HeightScatterFeature feature2 = new HeightScatterFeature(new BirchTreeStructure(), 1);

        NewOreStructure DIRT_STRUCTURE = new NewOreStructure(() -> Block.DIRT, 32);
        NewOreStructure GRAVEL_STRUCTURE = new NewOreStructure(() -> Block.GRAVEL, 32);
        NewOreStructure LIMESTONE_STRUCTURE = new NewOreStructure(() -> BlockListener.limestone, 32);

        biomeWoods = BiomeBuilder.start("Woods").grassAndLeavesColor(0xFF4dd97f)
                .feature(feature1)
                .feature(feature2)
                .feature(new OreScatterFeature(LIMESTONE_STRUCTURE,4,-64,128))
                .feature(new OreScatterFeature(DIRT_STRUCTURE,4,-64,128))
                .feature(new OreScatterFeature(GRAVEL_STRUCTURE,4,-64,128))
                .passiveEntity(SheepEntity.class,12)
                .passiveEntity(PigEntity.class,10)
                .passiveEntity(ChickenEntity.class,10)
                .passiveEntity(CowEntity.class,8)
                .build();
        //throw new RuntimeException("Limestone id: "+BlockListener.limestone.id);
    }

    @EventListener
    public void registerRegions(BiomeProviderRegisterEvent event) {
        VoronoiBiomeProvider voronoi = new VoronoiBiomeProvider();
        voronoi.addBiome(biomeWoods);

        BiomeAPI.addOverworldBiomeProvider(StationAPI.NAMESPACE.id("voronoi_provider"), voronoi);
    }
}
