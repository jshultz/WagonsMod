package com.shultzy88.wagonsmod.network;

import java.util.Iterator;
import java.util.List;

import com.shultzy88.wagonsmod.client.gui.GuiCoveredWagon;
import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;
import com.shultzy88.wagonsmod.entity.EntityWagon;
import com.shultzy88.wagonsmod.inventory.ContainerWagon;
import com.shultzy88.wagonsmod.main.WagonsMod;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class WagonGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == WagonsMod.WAGON_GUI_ID) {
			MovingObjectPosition mopTarget = Minecraft.getMinecraft().objectMouseOver;

			if (mopTarget.entityHit instanceof EntityCoveredWagon) {
				System.out.println("opening wagon inventory...");
				return new ContainerWagon(player.inventory, (EntityCoveredWagon) mopTarget.entityHit);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == WagonsMod.WAGON_GUI_ID) {
			MovingObjectPosition mopTarget = Minecraft.getMinecraft().objectMouseOver;

			if (mopTarget.entityHit instanceof EntityCoveredWagon) {
				System.out.println("opening wagon inventory...");
				return new GuiCoveredWagon(player.inventory, (EntityCoveredWagon) mopTarget.entityHit);
			}

		}
		return null;
	}

}
