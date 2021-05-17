package com.vandendaelen.explorersbooks.capabilities;

import com.vandendaelen.explorersbooks.helpers.BiomesHelper;
import com.vandendaelen.explorersbooks.models.BiomeEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.Constants;

import java.util.*;

public class BiomeCapability implements IBiome {
    private Set<BiomeEntry> biomeEntries = new HashSet<BiomeEntry>() {
    };

    @Override
    public void tick(PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity){

            MinecraftServer server = (playerEntity).getServer();
            Biome biomePlayerIn = server.func_241755_D_().getBiome(playerEntity.getPosition());
            Optional<RegistryKey<Biome>> biomeRegistryKey = server.getDynamicRegistries().getRegistry(Registry.BIOME_KEY).getOptionalKey(biomePlayerIn);

            biomeRegistryKey.ifPresent(
                    biomeKey -> {
                        ResourceLocation biomeName = biomeKey.getLocation();
                        boolean isBiomeAlreadyRegistered = biomeEntries
                                .stream()
                                .filter(biomeEntry -> biomeEntry.getBiomeName().equals(biomeName.toString()))
                                .anyMatch(entry -> entry.getPosition().withinDistance(playerEntity.getPosition(), 20*16));

                        if (!isBiomeAlreadyRegistered){
                            biomeEntries.add(
                                    new BiomeEntry(
                                            biomeName.toString(),
                                            playerEntity.getPosition())
                            );

                            playerEntity.sendStatusMessage(new TranslationTextComponent("explorersbooks.new_biome", BiomesHelper.prettyBiomesName(biomeName)), true);
                            //TODO : Set a book sound when a new biome is registered
                            //((ServerPlayerEntity) playerEntity).getServerWorld().playSound(playerEntity, playerEntity.getPosition(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.AMBIENT, 1F, 1F);
                        }

                    }
            );
        }
    }

    @Override
    public Set<BiomeEntry> getBiomesExplored() {
        return this.biomeEntries;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT bookTag = new CompoundNBT();
        ListNBT biomesList = new ListNBT();

        for(BiomeEntry entry : new ArrayList<>(biomeEntries)){
            CompoundNBT tag = new CompoundNBT();

            tag.putString("name", entry.getBiomeName());
            tag.putLong("position", entry.getPosition().toLong());

            biomesList.add(tag);
        }

        bookTag.put("biomes", biomesList);
        return bookTag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("biomes")){
            ListNBT biomesList = nbt.getList("biomes", Constants.NBT.TAG_COMPOUND);

            for (INBT nbtBiome : biomesList){
                CompoundNBT tag = (CompoundNBT)nbtBiome;

                biomeEntries
                        .add(
                                new BiomeEntry(
                                        tag.getString("name"),
                                        BlockPos.fromLong(tag.getLong("position"))
                                )
                        );
            }
        }
    }
}
