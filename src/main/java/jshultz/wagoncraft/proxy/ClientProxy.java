package jshultz.wagoncraft.proxy;

import jshultz.wagoncraft.Reference;
import jshultz.wagoncraft.client.renderer.entity.RenderHorseCart;
import jshultz.wagoncraft.entity.EntityHorseCart;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientProxy implements Proxy {

    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID.toUpperCase() + "/CommonProxy");

    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Entity rendering registration initiated");
        RenderingRegistry.registerEntityRenderingHandler(EntityHorseCart.class, RenderHorseCart::new);
        LOGGER.info("Entity rendering registration complete");
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
