package jshultz.wagoncraft.init;

import jshultz.wagoncraft.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ItemRegistry {

    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":horse_cart")
    public static final Item ITEM_HORSE_CART = null;

    @Mod.EventBusSubscriber
    public static class RegistryHandler {

        @SubscribeEvent
        public static void onRegisterItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(Reference.Items.ITEM_HORSE_CART);
        }
    }
}
