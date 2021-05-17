package com.vandendaelen.explorersbooks.items;

import com.vandendaelen.explorersbooks.capabilities.ExplorersBooksCapabilities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BiomesBookItem extends Item {
    private static final String BIOMES = "biomes";

    public BiomesBookItem() {
        super(new Properties().maxStackSize(1).group(ItemGroup.TOOLS));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote()){
            stack.getCapability(ExplorersBooksCapabilities.BIOME)
                    .ifPresent(cap -> cap.tick((PlayerEntity) entityIn));
        }
        if (worldIn.getGameTime() % 20 == 0) {
            syncCapability(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static void syncCapability(ItemStack stack) {
        if (stack.getShareTag() != null) {
            stack.getOrCreateTag().merge(stack.getShareTag());
        }
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        stack.getCapability(ExplorersBooksCapabilities.BIOME).ifPresent(handler -> tag.put("cap_sync", handler.serializeNBT()));
        return tag;

    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            if (nbt.contains("cap_sync")) {
                stack.getCapability(ExplorersBooksCapabilities.BIOME).ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("cap_sync")));
            }
        }
    }
}
