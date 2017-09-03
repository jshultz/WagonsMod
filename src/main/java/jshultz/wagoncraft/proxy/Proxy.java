package jshultz.wagoncraft.proxy;

import net.minecraftforge.fml.common.event.*;

public interface Proxy {
    void preInit(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void postInit(FMLPostInitializationEvent event);
}
