package jshultz.wagoncraft.client.init;

import jshultz.wagoncraft.Reference;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static jshultz.obsidian.client.model.ModelRegistryAdapter.registerModel;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemModelRegistry {

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        registerModel(Reference.Items.ITEM_HORSE_CART, 0,Reference.MOD_ID + ":horse_cart");
    }
}
