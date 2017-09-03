package jshultz.obsidian.client.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ModelRegistryAdapter {

    public static void registerModel(Item item, int meta, String resourcePath) {
        registerModel(item, meta, new ModelResourceLocation(resourcePath, "inventory"));
    }

    public static void registerModel(Item item, int meta, ModelResourceLocation fullResourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item, 0, fullResourceLocation);
        ModelLoader.setCustomMeshDefinition(item, stack -> fullResourceLocation);
    }
}
