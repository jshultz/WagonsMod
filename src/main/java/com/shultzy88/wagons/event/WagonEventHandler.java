package com.shultzy88.wagons.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import com.shultzy88.wagons.entity.EntityCoveredWagon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WagonEventHandler
{
	private static World world;
	private static EntityPlayer player;

	private static EntityCoveredWagon coveredWagon;

	// position of entity lead
	private static int posX;
	private static int posY;
	private static int posZ;

	/**
	 * When player interacts with wagon entity while horse, mule or oxen is
	 * teathered to player by lead, attach animal to wagon.
	 * 
	 * @param e
	 */
	@SubscribeEvent
	public void onWagonUse(AttackEntityEvent event)
	{
		// method stub for handling animal anchoring
		coveredWagon.anchorAnimalToWagon(player, world, posX, posY, posZ);
		
	}
}
