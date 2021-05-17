package com.vandendaelen.explorersbooks.items;

import com.vandendaelen.explorersbooks.ExplorersBooks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExplorersBooksItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExplorersBooks.MODID);

    public static final RegistryObject<Item> BIOMESBOOK = ITEMS.register("biomesbook", BiomesBookItem::new);

}
