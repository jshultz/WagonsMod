package jshultz.wagoncraft;

import jshultz.wagoncraft.proxy.Proxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

@Mod(name = Reference.NAME, modid = Reference.MOD_ID, version = Reference.VERSION)
public class WagonCraft {

    @Mod.Instance(Reference.MOD_ID)
    public static WagonCraft INSTANCE = new WagonCraft();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_PATH, serverSide = Reference.SERVER_PROXY_PATH)
    private static Proxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
