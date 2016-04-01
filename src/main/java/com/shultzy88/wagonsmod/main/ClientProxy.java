package com.shultzy88.wagonsmod.main;

import com.shultzy88.wagonsmod.client.model.ModelCoveredWagon;
import com.shultzy88.wagonsmod.client.render.RenderCoveredWagon;
import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy {
	public static KeyBinding[] keyBindings;

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		// register the renderer
		RenderingRegistry.registerEntityRenderingHandler(EntityCoveredWagon.class,
				new RenderCoveredWagon(new ModelCoveredWagon(), 1.0F));
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		
		super.init(e);	
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}
