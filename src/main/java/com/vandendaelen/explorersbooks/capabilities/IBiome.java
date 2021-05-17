package com.vandendaelen.explorersbooks.capabilities;

import com.vandendaelen.explorersbooks.models.BiomeEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.Set;

public interface IBiome extends INBTSerializable<CompoundNBT> {
    void tick(PlayerEntity playerEntity);
    Set<BiomeEntry> getBiomesExplored();

    public static class Storage implements Capability.IStorage<IBiome> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IBiome> capability, IBiome instance, Direction side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IBiome> capability, IBiome instance, Direction side, INBT nbt) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        private IBiome biome;

        public Provider(IBiome biome) {
            this.biome = biome;
        }

        public Provider() {
            this.biome = new BiomeCapability();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == ExplorersBooksCapabilities.BIOME ? (LazyOptional<T>) LazyOptional.of(() -> (T) biome) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return biome.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            biome.deserializeNBT(nbt);
        }

    }
}
