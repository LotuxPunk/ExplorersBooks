package com.vandendaelen.explorersbooks;

import com.vandendaelen.explorersbooks.capabilities.BiomeCapability;
import com.vandendaelen.explorersbooks.capabilities.IBiome;
import com.vandendaelen.explorersbooks.items.ExplorersBooksItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.vandendaelen.explorersbooks.ExplorersBooks.MODID;

@Mod(MODID)
public class ExplorersBooks {
    public static final String MODID = "explorersbooks";

    public ExplorersBooks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        ExplorersBooksItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IBiome.class, new IBiome.Storage(), BiomeCapability::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }
}
