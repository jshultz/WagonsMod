package com.shultzy88.wagonsmod.event;

import com.shultzy88.wagonsmod.entity.ExtendedPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class EntityEventHandler {
	
	@SubscribeEvent(receiveCanceled = true)
	public void onEntityConstruct(EntityConstructing event) {
		
		if (event.entity instanceof EntityPlayer)
			ExtendedPlayer.register((EntityPlayer) event.entity);
	}
}