package jshultz.wagoncraft.proxy;

import jshultz.wagoncraft.Reference;
import jshultz.wagoncraft.WagonCraft;
import jshultz.wagoncraft.entity.EntityHorseCart;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonProxy implements Proxy {

    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID.toUpperCase() + "/CommonProxy");

    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Entity registration initiated");
        EntityRegistry.registerModEntity(EntityHorseCart.class, "HorseCart", 0, WagonCraft.INSTANCE, 64, 20, false);
        LOGGER.info("Entity registration completed");
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
