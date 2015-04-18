package com.shultzy88.wagons.main;

import com.shultzy88.wagons.entity.EntityCoveredWagon;
import com.shultzy88.wagons.render.RenderCoveredWagon;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CombinedClientProxy extends CommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		// register the renderer
		RenderingRegistry.registerEntityRenderingHandler(EntityCoveredWagon.class, new RenderCoveredWagon());
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
	
}