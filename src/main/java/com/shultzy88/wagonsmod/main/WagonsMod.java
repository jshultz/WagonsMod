package com.shultzy88.wagonsmod.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;

/**
 * Name: WagonCraft Description This is based on a mod called Simply Horses that
 * introduces horse or oxen drawn wagons to Minecraft.</br>
 * Currently a work in progress, source code available at
 * <a href="http://www.github.com/shultzy88/wagons-mod">http://www.github.com/
 * shultzy88/wagons-mod</a>
 * 
 * @author Shultzy
 *
 */
@Mod(modid = WagonsMod.MOD_ID, name = WagonsMod.NAME, version = WagonsMod.VERSION)
public class WagonsMod {
	// The instance of this mod that Forge uses
	@Mod.Instance(WagonsMod.MOD_ID)
	public static WagonsMod INSTANCE;

	@SidedProxy(clientSide = "com.shultzy88.wagonsmod.main.ClientProxy", serverSide = "com.shultzy88.wagonsmod.main.CommonProxy")
	public static CommonProxy proxy;

	// Mod characterization data
	public static final String MOD_ID = "wagonsmod";
	public static final String NAME = "WagonCraft";
	public static final String VERSION = "1.0.0";
	
	// Other mod properties
	public static final int LASSO_GUI_ID = 0;
	public static final int WAGON_GUI_ID = 1;

	/**
	 * Called before Minecraft Forge loads mod
	 * 
	 * @param preEvent
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	/**
	 * Called when Minecraft Forge begins loading the mod
	 * 
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	/**
	 * Called after Minecraft Forge finishes loading the mod
	 * 
	 * @param postEvent
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
}
