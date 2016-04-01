package com.shultzy88.wagonsmod.main;

import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;
import com.shultzy88.wagonsmod.event.EntityEventHandler;
import com.shultzy88.wagonsmod.item.ItemWagon;
import com.shultzy88.wagonsmod.network.PacketDispatcher;
import com.shultzy88.wagonsmod.network.WagonGuiHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	// items and blocks
	private static ItemWagon itemWagon;

	public void preInit(FMLPreInitializationEvent e) {
		int globalID = 0;
		itemWagon = (ItemWagon) new ItemWagon().setUnlocalizedName("wagon");
		GameRegistry.registerItem(itemWagon, "wagon");
		EntityRegistry.registerModEntity(EntityCoveredWagon.class, "EntityCoveredWagon",
				globalID++, WagonsMod.INSTANCE, 80, 3, false);

		// initialize the network and register packets
		PacketDispatcher.register();
	}

	public void init(FMLInitializationEvent e) {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(WagonsMod.INSTANCE, new WagonGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
	}

	/**
	 * Gets the player instance based on which side gets executed.
	 */
	public EntityPlayer getPlayerFromContext(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity;
	}
}
