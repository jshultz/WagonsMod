package jshultz.wagoncraft;

import jshultz.wagoncraft.items.ItemWagon;
import net.minecraft.item.Item;

public class Reference {

    public static final String NAME = "WagonCraft";
    public static final String MOD_ID = "wcm";
    public static final String VERSION = "1.10.2-1.0.0.0";
    public static final String CLIENT_PROXY_PATH = "jshultz.wagoncraft.proxy.ClientProxy";
    public static final String SERVER_PROXY_PATH = "jshultz.wagoncraft.proxy.CommonProxy";

    public static class Items {

        public static final Item ITEM_HORSE_CART = new ItemWagon("horse_cart");
    }
}
