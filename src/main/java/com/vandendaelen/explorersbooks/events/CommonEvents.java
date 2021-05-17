package com.vandendaelen.explorersbooks.events;

import com.vandendaelen.explorersbooks.ExplorersBooks;
import com.vandendaelen.explorersbooks.capabilities.BiomeCapability;
import com.vandendaelen.explorersbooks.capabilities.IBiome;
import com.vandendaelen.explorersbooks.items.ExplorersBooksItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExplorersBooks.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    public static final ResourceLocation BIOME_CAP = new ResourceLocation(ExplorersBooks.MODID, "biome");

    @SubscribeEvent
    public static void attachItemStackCap(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == ExplorersBooksItems.BIOMESBOOK.get())
            event.addCapability(BIOME_CAP, new IBiome.Provider(new BiomeCapability()));
    }
}
