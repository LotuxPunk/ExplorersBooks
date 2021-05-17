package com.vandendaelen.explorersbooks.models;

import net.minecraft.util.math.BlockPos;

public class BiomeEntry {
    private String biomeName;
    private BlockPos position;

    public BiomeEntry(String biomeName, BlockPos position) {
        this.biomeName = biomeName;
        this.position = position;
    }

    public String getBiomeName() {
        return biomeName;
    }

    public BlockPos getPosition() {
        return position;
    }
}
