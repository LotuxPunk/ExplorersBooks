package com.vandendaelen.explorersbooks.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ExplorersBooksCapabilities {
    @CapabilityInject(IBiome.class)
    public static final Capability<IBiome> BIOME = null;
}
