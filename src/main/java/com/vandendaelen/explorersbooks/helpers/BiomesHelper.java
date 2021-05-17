package com.vandendaelen.explorersbooks.helpers;

import net.minecraft.util.ResourceLocation;

public class BiomesHelper {
    public static String prettyBiomesName(ResourceLocation biomeKey){
        String prettyName = biomeKey
                .getPath()
                .replace("_", " ")
                .toLowerCase();
        return prettyName.substring(0, 1).toUpperCase() + prettyName.substring(1);
    }
}
